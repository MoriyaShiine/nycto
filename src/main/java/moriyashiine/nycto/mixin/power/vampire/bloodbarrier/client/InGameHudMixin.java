/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.bloodbarrier.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InGameHud.class)
public class InGameHudMixin {
	@Unique
	private static final Identifier BLOOD_BARRIER = Nycto.id("hud/blood_barrier");

	@Unique
	private static int bloodBarriers = 0;

	@ModifyExpressionValue(method = "renderArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getArmor()I"))
	private static int nycto$bloodBarrier(int original, @Local(argsOnly = true) PlayerEntity player) {
		bloodBarriers = ModEntityComponents.BLOOD_BARRIER.get(player).getBarriers();
		return Math.max(bloodBarriers, original);
	}

	@WrapOperation(method = "renderArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIII)V"))
	private static void nycto$bloodBarrier(DrawContext instance, RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height, Operation<Void> original, @Local(argsOnly = true) PlayerEntity player, @Local(ordinal = 6) int n) {
		original.call(instance, pipeline, sprite, x, y, width, height);
		if (bloodBarriers > n) {
			original.call(instance, pipeline, BLOOD_BARRIER, x, y, width, height);
		}
	}
}
