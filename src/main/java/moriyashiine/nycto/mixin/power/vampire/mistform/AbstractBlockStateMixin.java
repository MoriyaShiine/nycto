/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.mistform;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.tag.ModBlockTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {
	@Shadow
	public abstract boolean isIn(TagKey<Block> tag);

	@ModifyReturnValue(method = "getCollisionShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;", at = @At("RETURN"))
	private VoxelShape nycto$mistForm(VoxelShape original, @Local(argsOnly = true) ShapeContext context) {
		if (original != VoxelShapes.empty() && original != VoxelShapes.fullCube() && context instanceof EntityShapeContext entityContext && entityContext.getEntity() instanceof PlayerEntity player && ModEntityComponents.MIST_FORM.get(player).isEnabled() && !isIn(ModBlockTags.MIST_FORM_UNPASSABLE)) {
			return VoxelShapes.empty();
		}
		return original;
	}
}