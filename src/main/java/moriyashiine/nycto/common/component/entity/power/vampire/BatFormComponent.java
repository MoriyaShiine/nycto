/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.component.entity.power.vampire;

import io.github.ladysnake.pal.AbilitySource;
import io.github.ladysnake.pal.Pal;
import io.github.ladysnake.pal.VanillaAbilities;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.power.util.VampireFormChangeComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;

public class BatFormComponent extends VampireFormChangeComponent {
	private static final EntityAttributeModifier MAX_HEALTH_MODIFIER = new EntityAttributeModifier(Nycto.id("bat_form_max_health"), -0.7, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

	private static final AbilitySource SOURCE = Pal.getAbilitySource(Nycto.id("bat_form"));

	public BatFormComponent(PlayerEntity obj) {
		super(obj);
	}

	public void sync() {
		ModEntityComponents.BAT_FORM.sync(obj);
	}

	@Override
	public void toggle() {
		SLibUtils.addParticles(obj, ParticleTypes.SMOKE, 48, ParticleAnchor.BODY);
		EntityAttributeInstance maxHealthAttribute = obj.getAttributeInstance(EntityAttributes.MAX_HEALTH);
		float percentage = obj.getHealth() / obj.getMaxHealth();
		if (enabled) {
			SOURCE.revokeFrom(obj, VanillaAbilities.ALLOW_FLYING);
			SOURCE.revokeFrom(obj, VanillaAbilities.FLYING);
			maxHealthAttribute.removeModifier(MAX_HEALTH_MODIFIER);
			obj.setHealth(obj.getMaxHealth() * percentage);
			SLibUtils.setModelReplacement(obj, null);
			drainTicks = 0;
		} else {
			SOURCE.grantTo(obj, VanillaAbilities.ALLOW_FLYING);
			SOURCE.grantTo(obj, VanillaAbilities.FLYING);
			maxHealthAttribute.addPersistentModifier(MAX_HEALTH_MODIFIER);
			obj.setHealth(obj.getMaxHealth() * percentage);
			SLibUtils.setModelReplacement(obj, EntityType.BAT);
			ModEntityComponents.BLOOD.get(obj).drain(ModPowers.BAT_FORM.getCost(obj));
			drainTicks = FORM_DRAIN_TICKS;
		}
		enabled = !enabled;
		sync();
	}
}
