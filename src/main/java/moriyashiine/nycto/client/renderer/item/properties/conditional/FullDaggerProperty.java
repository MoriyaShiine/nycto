/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.item.properties.conditional;

import com.mojang.serialization.MapCodec;
import moriyashiine.nycto.common.world.item.VampiricDaggerItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record FullDaggerProperty() implements ConditionalItemModelProperty {
	public static final MapCodec<FullDaggerProperty> MAP_CODEC = MapCodec.unit(new FullDaggerProperty());

	@Override
	public boolean get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity owner, int seed, ItemDisplayContext displayContext) {
		return VampiricDaggerItem.isFull(stack);
	}

	@Override
	public MapCodec<FullDaggerProperty> type() {
		return MAP_CODEC;
	}
}
