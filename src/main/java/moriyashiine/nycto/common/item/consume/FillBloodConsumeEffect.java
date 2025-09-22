/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.item.consume;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModConsumeEffectTypes;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public record FillBloodConsumeEffect(int fillAmount,
									 ApplyEffectsConsumeEffect nonVampireEffects) implements ConsumeEffect {
	public static final MapCodec<FillBloodConsumeEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
					Codecs.POSITIVE_INT.fieldOf("fill_amount").forGetter(FillBloodConsumeEffect::fillAmount),
					ApplyEffectsConsumeEffect.CODEC.fieldOf("non_vampire_effects").forGetter(FillBloodConsumeEffect::nonVampireEffects))
			.apply(instance, FillBloodConsumeEffect::new)
	);
	public static final PacketCodec<RegistryByteBuf, FillBloodConsumeEffect> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.INTEGER, FillBloodConsumeEffect::fillAmount,
			ApplyEffectsConsumeEffect.PACKET_CODEC, FillBloodConsumeEffect::nonVampireEffects,
			FillBloodConsumeEffect::new
	);

	public FillBloodConsumeEffect(int fillAmount) {
		this(fillAmount, new ApplyEffectsConsumeEffect(List.of()));
	}

	@Override
	public Type<? extends ConsumeEffect> getType() {
		return ModConsumeEffectTypes.FILL_BLOOD;
	}

	@Override
	public boolean onConsume(World world, ItemStack stack, LivingEntity user) {
		if (nonVampireEffects().effects().isEmpty() || NyctoAPI.isVampire(user)) {
			int fillAmount = fillAmount();
			if (user instanceof PlayerEntity player && NyctoAPI.hasPower(player, ModPowers.RICH_TASTES)) {
				fillAmount = MathHelper.ceil(fillAmount / 2F);
			}
			ModEntityComponents.BLOOD.get(user).fill(fillAmount);
			return true;
		}
		return nonVampireEffects().onConsume(world, stack, user);
	}
}
