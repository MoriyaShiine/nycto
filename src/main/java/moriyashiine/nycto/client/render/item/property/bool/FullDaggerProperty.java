/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.item.property.bool;

import com.mojang.serialization.MapCodec;
import moriyashiine.nycto.common.item.VampiricDaggerItem;
import net.minecraft.client.render.item.property.bool.BooleanProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record FullDaggerProperty() implements BooleanProperty {
	public static final MapCodec<FullDaggerProperty> CODEC = MapCodec.unit(new FullDaggerProperty());

	@Override
	public boolean test(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
		return VampiricDaggerItem.isFull(stack);
	}

	@Override
	public MapCodec<FullDaggerProperty> getCodec() {
		return CODEC;
	}
}
