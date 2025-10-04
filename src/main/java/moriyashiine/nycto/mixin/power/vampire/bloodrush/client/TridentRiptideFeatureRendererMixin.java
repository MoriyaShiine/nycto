/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.bloodrush.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.client.render.entity.state.BloodrushRenderStateAddition;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.TridentRiptideFeatureRenderer;
import net.minecraft.client.render.entity.model.TridentRiptideEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TridentRiptideFeatureRenderer.class)
public class TridentRiptideFeatureRendererMixin {
	@Unique
	private static final Identifier BLOODRUSH_TEXTURE = Nycto.id("textures/entity/bloodrush_riptide.png");

	@WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/client/render/entity/state/PlayerEntityRenderState;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/TridentRiptideEntityModel;getLayer(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
	private RenderLayer nycto$bloodrush(TridentRiptideEntityModel instance, Identifier identifier, Operation<RenderLayer> original, @Local(argsOnly = true) PlayerEntityRenderState state) {
		if (((BloodrushRenderStateAddition) state).nycto$usingBloodrushLenient()) {
			identifier = BLOODRUSH_TEXTURE;
		}
		return original.call(instance, identifier);
	}
}
