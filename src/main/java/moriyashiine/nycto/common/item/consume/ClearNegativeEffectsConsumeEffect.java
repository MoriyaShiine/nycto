/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.item.consume;

import com.mojang.serialization.MapCodec;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModConsumeEffectTypes;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

public record ClearNegativeEffectsConsumeEffect() implements ConsumeEffect {
	public static final ClearNegativeEffectsConsumeEffect INSTANCE = new ClearNegativeEffectsConsumeEffect();
	public static final MapCodec<ClearNegativeEffectsConsumeEffect> CODEC = MapCodec.unit(INSTANCE);
	public static final PacketCodec<RegistryByteBuf, ClearNegativeEffectsConsumeEffect> PACKET_CODEC = PacketCodec.unit(INSTANCE);

	@Override
	public Type<? extends ConsumeEffect> getType() {
		return ModConsumeEffectTypes.FILL_BLOOD;
	}

	@Override
	public boolean onConsume(World world, ItemStack stack, LivingEntity user) {
		if (world.isClient) {
			return false;
		}
		boolean removed = false;
		for (StatusEffect statusEffect : Registries.STATUS_EFFECT) {
			if (statusEffect.getCategory() == StatusEffectCategory.HARMFUL) {
				RegistryEntry<StatusEffect> entry = Registries.STATUS_EFFECT.getEntry(statusEffect);
				if (user.hasStatusEffect(entry)) {
					removed |= user.removeStatusEffect(entry);
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
