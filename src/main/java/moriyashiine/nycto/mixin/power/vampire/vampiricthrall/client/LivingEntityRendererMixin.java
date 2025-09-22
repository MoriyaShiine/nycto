/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.nycto.api.NyctoClientAPI;
import moriyashiine.nycto.client.render.entity.state.VampiricThrallRenderStateAddition;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState> extends EntityRenderer<T, S> {
	@Unique
	private static final Map<EntityType<?>, Identifier> THRALL_IDENTIFIERS = new HashMap<>();

	@Unique
	@Nullable
	private Identifier thrallTexture = null;

	protected LivingEntityRendererMixin(EntityRendererFactory.Context context) {
		super(context);
	}

	@ModifyExpressionValue(method = "getRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;getTexture(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;)Lnet/minecraft/util/Identifier;"))
	private Identifier nycto$vampiricThrall(Identifier original) {
		if (thrallTexture != null) {
			return thrallTexture;
		}
		return original;
	}

	@Inject(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At("TAIL"))
	private void nycto$vampiricThrall(T entity, S state, float tickProgress, CallbackInfo ci) {
		thrallTexture = null;
		if (NyctoClientAPI.hasThrallTexture(entity)) {
			thrallTexture = Objects.requireNonNullElseGet(NyctoClientAPI.getSpecialThrallTexture(entity), () -> THRALL_IDENTIFIERS.computeIfAbsent(entity.getType(), type -> {
				Identifier entityTypeId = Registries.ENTITY_TYPE.getId(type);
				return Nycto.id("textures/entity/vampiric_thrall/" + entityTypeId.getNamespace() + "/" + entityTypeId.getPath() + ".png");
			}));
		}
		((VampiricThrallRenderStateAddition) state).nycto$setThralled(thrallTexture != null);
	}
}
