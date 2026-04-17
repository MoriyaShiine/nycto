/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.item.consumeeffects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModConsumeEffectTypes;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;

import java.util.List;

public record FillBloodConsumeEffect(int fillAmount, ApplyStatusEffectsConsumeEffect nonVampireEffects) implements ConsumeEffect {
	public static final MapCodec<FillBloodConsumeEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
					ExtraCodecs.POSITIVE_INT.fieldOf("fill_amount").forGetter(FillBloodConsumeEffect::fillAmount),
					ApplyStatusEffectsConsumeEffect.CODEC.fieldOf("non_vampire_effects").forGetter(FillBloodConsumeEffect::nonVampireEffects))
			.apply(instance, FillBloodConsumeEffect::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, FillBloodConsumeEffect> PACKET_CODEC = StreamCodec.composite(
			ByteBufCodecs.INT, FillBloodConsumeEffect::fillAmount,
			ApplyStatusEffectsConsumeEffect.STREAM_CODEC, FillBloodConsumeEffect::nonVampireEffects,
			FillBloodConsumeEffect::new
	);

	public FillBloodConsumeEffect(int fillAmount) {
		this(fillAmount, new ApplyStatusEffectsConsumeEffect(List.of()));
	}

	@Override
	public Type<FillBloodConsumeEffect> getType() {
		return ModConsumeEffectTypes.FILL_BLOOD;
	}

	@Override
	public boolean apply(Level level, ItemStack stack, LivingEntity user) {
		if (nonVampireEffects().effects().isEmpty() || NyctoAPI.isVampire(user)) {
			int fillAmount = fillAmount();
			if (user instanceof Player player && NyctoAPI.hasPower(player, ModPowers.RICH_TASTES)) {
				fillAmount = Mth.ceil(fillAmount / 2F);
			}
			ModEntityComponents.BLOOD.get(user).fill(fillAmount);
			return true;
		}
		return nonVampireEffects().apply(level, stack, user);
	}
}
