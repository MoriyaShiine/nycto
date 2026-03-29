/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.item.consumeeffects;

import com.mojang.serialization.MapCodec;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModConsumeEffectTypes;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;

public record ClearNegativeEffectsConsumeEffect() implements ConsumeEffect {
	public static final ClearNegativeEffectsConsumeEffect INSTANCE = new ClearNegativeEffectsConsumeEffect();
	public static final MapCodec<ClearNegativeEffectsConsumeEffect> CODEC = MapCodec.unit(INSTANCE);
	public static final StreamCodec<RegistryFriendlyByteBuf, ClearNegativeEffectsConsumeEffect> PACKET_CODEC = StreamCodec.unit(INSTANCE);

	@Override
	public Type<ClearNegativeEffectsConsumeEffect> getType() {
		return ModConsumeEffectTypes.CLEAR_NEGATIVE_EFFECTS;
	}

	@Override
	public boolean apply(Level level, ItemStack stack, LivingEntity user) {
		if (level.isClientSide()) {
			return false;
		}
		boolean removed = false;
		for (MobEffect effect : BuiltInRegistries.MOB_EFFECT) {
			if (effect.getCategory() == MobEffectCategory.HARMFUL) {
				Holder<MobEffect> holder = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect);
				if (user.hasEffect(holder)) {
					removed |= user.removeEffect(holder);
				}
			}
		}
		if (ModEntityComponents.HEAL_BLOCK.get(user).isHealingBlocked()) {
			NyctoAPI.applyHealBlock(user, 0);
			removed = true;
		}
		return removed;
	}
}
