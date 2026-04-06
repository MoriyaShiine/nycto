/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.bloodrush.client;

import moriyashiine.nycto.client.renderer.entity.layers.BloodrushAuraLayer;
import moriyashiine.nycto.client.renderer.entity.state.BloodrushRenderState;
import moriyashiine.nycto.common.component.entity.power.vampire.BloodrushComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public abstract class AvatarRendererMixin<AvatarlikeEntity extends Avatar & ClientAvatarEntity> extends LivingEntityRenderer<AvatarlikeEntity, AvatarRenderState, PlayerModel> {
	public AvatarRendererMixin(EntityRendererProvider.Context context, PlayerModel model, float shadow) {
		super(context, model, shadow);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void nycto$bloodrush(EntityRendererProvider.Context context, boolean slimSteve, CallbackInfo ci) {
		addLayer(new BloodrushAuraLayer(this, context.getModelSet(), slimSteve));
	}

	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("TAIL"))
	private void nycto$bloodrush(AvatarlikeEntity entity, AvatarRenderState state, float partialTicks, CallbackInfo ci) {
		BloodrushComponent bloodrushComponent = ModEntityComponents.BLOODRUSH.getNullable(entity);
		if (bloodrushComponent != null) {
			BloodrushRenderState bloodrushRenderState = new BloodrushRenderState();
			bloodrushRenderState.usingBloodrush = bloodrushComponent.isActive(false);
			bloodrushRenderState.usingBloodrushLenient = bloodrushComponent.isActive(true);
			state.setData(BloodrushRenderState.KEY, bloodrushRenderState);
		}
	}
}
