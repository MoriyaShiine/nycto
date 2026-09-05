package moriyashiine.nycto.common.component.entity.power.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.NyctoService;
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
	private static final AttributeModifier MAX_HEALTH_MODIFIER = new AttributeModifier(Nycto.id("bat_form"), -0.7, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

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
			maxHealth.removeModifier(MAX_HEALTH_MODIFIER);
			obj.setHealth(obj.getMaxHealth() * percentage);
			NyctoService.INSTANCE.applyBatFormAbilities(obj, false);
			SLibUtils.removeModelReplacementType(obj, EntityTypes.BAT);
			drainTicks = 0;
		} else {
			maxHealth.addPermanentModifier(MAX_HEALTH_MODIFIER);
			obj.setHealth(obj.getMaxHealth() * percentage);
			NyctoService.INSTANCE.applyBatFormAbilities(obj, true);
			SLibUtils.addModelReplacementType(obj, EntityTypes.BAT, 500);
			NyctoAPI.drainBlood(obj, NyctoPowers.BAT_FORM.getCost(obj));
			drainTicks = POWER_DRAIN_TICKS;
		}
		enabled = !enabled;
		sync();
	}
}
