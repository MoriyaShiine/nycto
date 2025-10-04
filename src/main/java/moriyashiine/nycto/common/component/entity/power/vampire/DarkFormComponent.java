/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.component.entity.power.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.power.util.VampireFormChangeComponent;
import moriyashiine.nycto.common.entity.mob.DarkFormEntity;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModEntityTypes;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.payload.DarkFormJumpPayload;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.strawberrylib.api.event.PreventEquipmentUsageEvent;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import java.util.stream.Collectors;

public class DarkFormComponent extends VampireFormChangeComponent implements CommonTickingComponent {
	private static final EntityAttributeModifier ARMOR_MODIFIER = new EntityAttributeModifier(Nycto.id("dark_form_armor"), 20, EntityAttributeModifier.Operation.ADD_VALUE);
	private static final EntityAttributeModifier ARMOR_TOUGHNESS_MODIFIER = new EntityAttributeModifier(Nycto.id("dark_form_armor_toughness"), 12, EntityAttributeModifier.Operation.ADD_VALUE);
	private static final EntityAttributeModifier ATTACK_DAMAGE_MODIFIER = new EntityAttributeModifier(Nycto.id("dark_form_attack_damage"), 12, EntityAttributeModifier.Operation.ADD_VALUE);
	private static final EntityAttributeModifier ATTACK_SPEED_MODIFIER = new EntityAttributeModifier(Nycto.id("dark_form_attack_speed"), -0.5, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	private static final EntityAttributeModifier BLOCK_INTERACTION_RANGE_MODIFIER = new EntityAttributeModifier(Nycto.id("dark_form_block_interaction_range"), 1, EntityAttributeModifier.Operation.ADD_VALUE);
	private static final EntityAttributeModifier ENTITY_INTERACTION_RANGE_MODIFIER = new EntityAttributeModifier(Nycto.id("dark_form_entity_interaction_range"), 1, EntityAttributeModifier.Operation.ADD_VALUE);
	private static final EntityAttributeModifier KNOCKBACK_RESISTANCE_MODIFIER = new EntityAttributeModifier(Nycto.id("dark_form_knockback_resistance"), 0.7, EntityAttributeModifier.Operation.ADD_VALUE);

	private int jumpCooldown = 0;

	public DarkFormComponent(PlayerEntity obj) {
		super(obj);
	}

	@Override
	public void readData(ReadView readView) {
		super.readData(readView);
		jumpCooldown = readView.getInt("JumpCooldown", 0);
	}

	@Override
	public void writeData(WriteView writeView) {
		super.writeData(writeView);
		writeView.putInt("JumpCooldown", jumpCooldown);
	}

	@Override
	public void tick() {
		if (enabled && obj.isPartOfGame()) {
			if (obj.isOnGround()) {
				jumpCooldown = 0;
			} else {
				if (jumpCooldown > 0) {
					jumpCooldown--;
				}
				if (obj.jumping && canJump()) {
					jump();
					DarkFormJumpPayload.send();
				}
				if (obj.getVelocity().getY() < 0 && !obj.isSneaking()) {
					obj.setVelocity(obj.getVelocity().multiply(1, 0.8, 1));
					obj.onLanding();
					obj.fallDistance = 1;
				}
			}
		} else {
			jumpCooldown = 0;
		}
	}

	@Override
	public void serverTick() {
		super.serverTick();
		tick();
		if (enabled && NyctoUtil.isSurvival(obj) && obj.age % 20 == 0) {
			obj.getEntityWorld().getEntitiesByClass(VillagerEntity.class, obj.getBoundingBox().expand(16), LivingEntity::isAlive).forEach(villager -> villager.getBrain().doExclusively(Activity.PANIC));
		}
	}

	public void sync() {
		ModEntityComponents.DARK_FORM.sync(obj);
	}

	@Override
	public void toggle() {
		SLibUtils.addParticles(obj, ParticleTypes.SMOKE, 48, ParticleAnchor.BODY);
		if (enabled) {
			SLibUtils.setModelReplacement(obj, null);
			drainTicks = 0;
		} else {
			ModEntityComponents.BLOOD.get(obj).drain(ModPowers.DARK_FORM.getCost(obj));
			SLibUtils.setModelReplacement(obj, ModEntityTypes.DARK_FORM);
			drainTicks = FORM_DRAIN_TICKS;
		}
		enabled = !enabled;
		SLibUtils.conditionallyApplyAttributeModifier(obj, EntityAttributes.ARMOR, getAdjustedModifier(ARMOR_MODIFIER), enabled);
		SLibUtils.conditionallyApplyAttributeModifier(obj, EntityAttributes.ARMOR_TOUGHNESS, getAdjustedModifier(ARMOR_TOUGHNESS_MODIFIER), enabled);
		SLibUtils.conditionallyApplyAttributeModifier(obj, EntityAttributes.ATTACK_DAMAGE, getAdjustedModifier(ATTACK_DAMAGE_MODIFIER), enabled);
		SLibUtils.conditionallyApplyAttributeModifier(obj, EntityAttributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER, enabled);
		SLibUtils.conditionallyApplyAttributeModifier(obj, EntityAttributes.BLOCK_INTERACTION_RANGE, BLOCK_INTERACTION_RANGE_MODIFIER, enabled);
		SLibUtils.conditionallyApplyAttributeModifier(obj, EntityAttributes.ENTITY_INTERACTION_RANGE, ENTITY_INTERACTION_RANGE_MODIFIER, enabled);
		SLibUtils.conditionallyApplyAttributeModifier(obj, EntityAttributes.KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE_MODIFIER, enabled);
		PreventEquipmentUsageEvent.triggerEquipmentCheck(obj);
		if (!enabled) {
			for (ItemStack stack : obj.getInventory().getMainStacks()) {
				EquipmentSlot slot = obj.getPreferredEquipmentSlot(stack);
				if (slot.isArmorSlot() && obj.getEquippedStack(slot).isEmpty() && obj.canEquip(stack, slot)) {
					obj.equipStack(slot, stack.copyAndEmpty());
				}
			}
		}
		sync();
	}

	public void jump() {
		jumpCooldown = DarkFormEntity.JUMP_COOLDOWN;
		obj.jump();
		obj.setVelocity(obj.getVelocity().multiply(1.3, 1.1, 1.3));
		SLibUtils.playSound(obj, ModSoundEvents.ENTITY_DARK_FORM_FLAP);
	}

	public boolean canJump() {
		if (obj.getAbilities().flying || obj.isTouchingWater() || obj.isSwimming()) {
			return false;
		}
		return jumpCooldown == 0 && !obj.isGliding() && obj.getVehicle() == null && !obj.isClimbing();
	}

	private EntityAttributeModifier getAdjustedModifier(EntityAttributeModifier modifier) {
		int negativePowers = NyctoAPI.getPowers(obj).stream().filter(instance -> instance.getPower().isNegative()).collect(Collectors.toSet()).size();
		float multiplier = switch (negativePowers) {
			case 0 -> 0.5F;
			case 1 -> 0.7F;
			case 2 -> 0.85F;
			default -> 1;
		};
		return new EntityAttributeModifier(modifier.id(), modifier.value() * multiplier, modifier.operation());
	}
}
