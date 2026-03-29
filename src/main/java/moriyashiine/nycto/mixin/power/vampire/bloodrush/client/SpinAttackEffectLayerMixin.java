/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.bloodrush.client;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.client.renderer.entity.state.BloodrushRenderState;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.renderer.entity.layers.SpinAttackEffectLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SpinAttackEffectLayer.class)
public class SpinAttackEffectLayerMixin {
	@Unique
	private static final Identifier BLOODRUSH_RIPTIDE_TEXTURE = Nycto.id("textures/entity/bloodrush/bloodrush_riptide.png");

	@ModifyArg(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/AvatarRenderState;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/resources/Identifier;IIILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V"))
	private Identifier nycto$bloodrush(Identifier texture, @Local(argsOnly = true) AvatarRenderState state) {
		@Nullable BloodrushRenderState bloodrushRenderState = state.getData(BloodrushRenderState.KEY);
		if (bloodrushRenderState != null && bloodrushRenderState.usingBloodrushLenient) {
			texture = BLOODRUSH_RIPTIDE_TEXTURE;
		}
		return texture;
	}
}
