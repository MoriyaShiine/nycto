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
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Gui.class)
public class GuiMixin {
	@Unique
	private static final Identifier BLOOD_BARRIER = Nycto.id("hud/blood_barrier");

	@Unique
	private static int bloodBarriers = 0;

	@ModifyExpressionValue(method = "extractArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getArmorValue()I"))
	private static int nycto$bloodBarrier(int original, @Local(argsOnly = true) Player player) {
		bloodBarriers = ModEntityComponents.BLOOD_BARRIER.get(player).getBarriers();
		return Math.max(bloodBarriers, original);
	}

	@WrapOperation(method = "extractArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"))
	private static void nycto$bloodBarrier(GuiGraphicsExtractor instance, RenderPipeline renderPipeline, Identifier location, int x, int y, int width, int height, Operation<Void> original, @Local(name = "i") int i) {
		original.call(instance, renderPipeline, location, x, y, width, height);
		if (bloodBarriers > i) {
			original.call(instance, renderPipeline, BLOOD_BARRIER, x, y, width, height);
		}
	}
}
