/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity.power.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.power.util.VampireFormChangeComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModEntityTypes;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.payload.DarkFormJumpPayload;
import moriyashiine.nycto.common.world.entity.monster.DarkForm;
import moriyashiine.strawberrylib.api.event.PreventEquipmentUsageEvent;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import java.util.stream.Collectors;

public class DarkFormComponent extends VampireFormChangeComponent implements CommonTickingComponent {
	private static final AttributeModifier ARMOR_MODIFIER = new AttributeModifier(Nycto.id("dark_form_armor"), 20, AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier ARMOR_TOUGHNESS_MODIFIER = new AttributeModifier(Nycto.id("dark_form_armor_toughness"), 12, AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier ATTACK_DAMAGE_MODIFIER = new AttributeModifier(Nycto.id("dark_form_attack_damage"), 12, AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier ATTACK_SPEED_MODIFIER = new AttributeModifier(Nycto.id("dark_form_attack_speed"), -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	private static final AttributeModifier BLOCK_INTERACTION_RANGE_MODIFIER = new AttributeModifier(Nycto.id("dark_form_block_interaction_range"), 1, AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier ENTITY_INTERACTION_RANGE_MODIFIER = new AttributeModifier(Nycto.id("dark_form_entity_interaction_range"), 1, AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier KNOCKBACK_RESISTANCE_MODIFIER = new AttributeModifier(Nycto.id("dark_form_knockback_resistance"), 0.7, AttributeModifier.Operation.ADD_VALUE);

	private int jumpCooldown = 0;

	public DarkFormComponent(Player obj) {
		super(obj);
	}

	@Override
	public void readData(ValueInput input) {
		super.readData(input);
		jumpCooldown = input.getIntOr("JumpCooldown", 0);
	}

	@Override
	public void writeData(ValueOutput output) {
		super.writeData(output);
		output.putInt("JumpCooldown", jumpCooldown);
	}

	@Override
	public void tick() {
		if (enabled && obj.slib$exists()) {
			if (obj.onGround()) {
				jumpCooldown = 0;
			} else {
				if (jumpCooldown > 0) {
					jumpCooldown--;
				}
				if (obj.jumping && canJump()) {
					jump();
					DarkFormJumpPayload.send();
				}
				if (obj.getDeltaMovement().y() < 0 && !obj.isShiftKeyDown()) {
					obj.setDeltaMovement(obj.getDeltaMovement().multiply(1, 0.8, 1));
					obj.resetFallDistance();
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
		if (enabled && obj.slib$isSurvival() && obj.tickCount % 20 == 0) {
			obj.level().getEntitiesOfClass(Villager.class, obj.getBoundingBox().inflate(16), LivingEntity::isAlive).forEach(villager -> villager.getBrain().setActiveActivityIfPossible(Activity.PANIC));
		}
	}

	public void sync() {
		ModEntityComponents.DARK_FORM.sync(obj);
	}

	@Override
	public void toggle() {
		SLibUtils.addParticles(obj, ParticleTypes.SMOKE, 48, ParticleAnchor.BODY);
		if (enabled) {
			SLibUtils.removeModelReplacementType(obj, ModEntityTypes.DARK_FORM);
			drainTicks = 0;
		} else {
			ModEntityComponents.BLOOD.get(obj).drain(ModPowers.DARK_FORM.getCost(obj));
			SLibUtils.addModelReplacementType(obj, ModEntityTypes.DARK_FORM, 500);
			drainTicks = FORM_DRAIN_TICKS;
		}
		enabled = !enabled;
		SLibUtils.conditionallyApplyAttributeModifier(obj, Attributes.ARMOR, getAdjustedModifier(ARMOR_MODIFIER), enabled);
		SLibUtils.conditionallyApplyAttributeModifier(obj, Attributes.ARMOR_TOUGHNESS, getAdjustedModifier(ARMOR_TOUGHNESS_MODIFIER), enabled);
		SLibUtils.conditionallyApplyAttributeModifier(obj, Attributes.ATTACK_DAMAGE, getAdjustedModifier(ATTACK_DAMAGE_MODIFIER), enabled);
		SLibUtils.conditionallyApplyAttributeModifier(obj, Attributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER, enabled);
		SLibUtils.conditionallyApplyAttributeModifier(obj, Attributes.BLOCK_INTERACTION_RANGE, BLOCK_INTERACTION_RANGE_MODIFIER, enabled);
		SLibUtils.conditionallyApplyAttributeModifier(obj, Attributes.ENTITY_INTERACTION_RANGE, ENTITY_INTERACTION_RANGE_MODIFIER, enabled);
		SLibUtils.conditionallyApplyAttributeModifier(obj, Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE_MODIFIER, enabled);
		PreventEquipmentUsageEvent.triggerEquipmentCheck(obj);
		if (!enabled) {
			for (ItemStack stack : obj.getInventory().getNonEquipmentItems()) {
				EquipmentSlot slot = obj.getEquipmentSlotForItem(stack);
				if (slot.isArmor() && obj.getItemBySlot(slot).isEmpty() && obj.isEquippableInSlot(stack, slot)) {
					obj.setItemSlot(slot, stack.copyAndClear());
				}
			}
		}
		sync();
	}

	public void jump() {
		jumpCooldown = DarkForm.JUMP_COOLDOWN;
		obj.jumpFromGround();
		obj.setDeltaMovement(obj.getDeltaMovement().multiply(1.3, 1.1, 1.3));
		SLibUtils.playSound(obj, ModSoundEvents.ENTITY_DARK_FORM_FLAP);
	}

	public boolean canJump() {
		if (obj.getAbilities().flying || obj.isInWater() || obj.isSwimming()) {
			return false;
		}
		return jumpCooldown == 0 && !obj.isFallFlying() && obj.getVehicle() == null && !obj.onClimbable();
	}

	private AttributeModifier getAdjustedModifier(AttributeModifier modifier) {
		int weaknesses = NyctoAPI.getPowers(obj).stream().filter(instance -> instance.getPower().isWeakness()).collect(Collectors.toSet()).size();
		float multiplier = switch (weaknesses) {
			case 0 -> 0.5F;
			case 1 -> 0.7F;
			case 2 -> 0.85F;
			default -> 1;
		};
		return new AttributeModifier(modifier.id(), modifier.amount() * multiplier, modifier.operation());
	}
}
