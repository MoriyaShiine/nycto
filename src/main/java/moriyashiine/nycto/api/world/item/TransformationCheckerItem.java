/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.api.world.item;

import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class TransformationCheckerItem extends Item {
	private final Predicate<LivingEntity> predicate;

	public TransformationCheckerItem(Properties properties, Predicate<LivingEntity> predicate) {
		super(properties);
		this.predicate = predicate;
	}

	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
		return useOnEntity(stack, player, target, predicate, () -> super.interactLivingEntity(stack, player, target, hand));
	}

	public static InteractionResult useOnEntity(ItemStack stack, Player player, LivingEntity target, Predicate<LivingEntity> predicate, Supplier<InteractionResult> resultSupplier) {
		if (predicate.test(target)) {
			target.playSound(ModSoundEvents.ENTITY_GENERIC_SIZZLE);
			if (target.level().isClientSide()) {
				SLibClientUtils.addParticles(target, ParticleTypes.SMOKE, 8, ParticleAnchor.BODY);
			} else {
				target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60));
				player.getCooldowns().addCooldown(stack, 60);
				stack.consume(1, player);
			}
			return InteractionResult.SUCCESS;
		}
		return resultSupplier.get();
	}
}
