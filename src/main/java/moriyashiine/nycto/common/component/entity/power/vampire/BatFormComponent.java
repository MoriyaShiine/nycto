package moriyashiine.nycto.common.component.entity.power.vampire;

import io.github.ladysnake.pal.AbilitySource;
import io.github.ladysnake.pal.Pal;
import io.github.ladysnake.pal.VanillaAbilities;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.power.util.VampireFormChangeComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.init.NyctoPowers;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class BatFormComponent extends VampireFormChangeComponent {
	private static final AttributeModifier MAX_HEALTH_MODIFIER = new AttributeModifier(Nycto.id("bat_form_max_health"), -0.7, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

	private static final AbilitySource SOURCE = Pal.getAbilitySource(Nycto.id("bat_form"));

	public BatFormComponent(Player obj) {
		super(obj);
	}

	public void sync() {
		NyctoEntityComponents.BAT_FORM.sync(obj);
	}

	@Override
	public void toggle() {
		SLibUtils.addParticles(obj, ParticleTypes.SMOKE, 48, ParticleAnchor.BODY);
		AttributeInstance maxHealth = obj.getAttribute(Attributes.MAX_HEALTH);
		float percentage = obj.getHealth() / obj.getMaxHealth();
		if (enabled) {
			SOURCE.revokeFrom(obj, VanillaAbilities.ALLOW_FLYING);
			SOURCE.revokeFrom(obj, VanillaAbilities.FLYING);
			maxHealth.removeModifier(MAX_HEALTH_MODIFIER);
			obj.setHealth(obj.getMaxHealth() * percentage);
			SLibUtils.removeModelReplacementType(obj, EntityTypes.BAT);
			drainTicks = 0;
		} else {
			SOURCE.grantTo(obj, VanillaAbilities.ALLOW_FLYING);
			SOURCE.grantTo(obj, VanillaAbilities.FLYING);
			maxHealth.addPermanentModifier(MAX_HEALTH_MODIFIER);
			obj.setHealth(obj.getMaxHealth() * percentage);
			SLibUtils.addModelReplacementType(obj, EntityTypes.BAT, 500);
			NyctoEntityComponents.BLOOD.get(obj).drain(NyctoPowers.BAT_FORM.getCost(obj));
			drainTicks = POWER_DRAIN_TICKS;
		}
		enabled = !enabled;
		sync();
	}
}
