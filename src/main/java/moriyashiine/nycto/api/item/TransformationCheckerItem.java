/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.api.item;

import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class TransformationCheckerItem extends Item {
	private final Predicate<LivingEntity> predicate;

	public TransformationCheckerItem(Settings settings, Predicate<LivingEntity> predicate) {
		super(settings);
		this.predicate = predicate;
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		return useOnEntity(stack, user, entity, predicate, () -> super.useOnEntity(stack, user, entity, hand));
	}

	public static ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Predicate<LivingEntity> predicate, Supplier<ActionResult> supplier) {
		if (predicate.test(entity)) {
			entity.playSoundIfNotSilent(ModSoundEvents.ENTITY_GENERIC_SIZZLE);
			if (entity.getWorld().isClient) {
				SLibClientUtils.addParticles(entity, ParticleTypes.SMOKE, 8, ParticleAnchor.BODY);
			} else {
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 60));
				user.getItemCooldownManager().set(stack, 60);
				stack.decrementUnlessCreative(1, user);
			}
			return ActionResult.SUCCESS;
		}
		return supplier.get();
	}
}
