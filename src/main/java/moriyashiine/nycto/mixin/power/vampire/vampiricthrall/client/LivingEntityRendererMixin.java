/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.nycto.api.NyctoClientAPI;
import moriyashiine.nycto.api.renderer.entity.vampiricthrall.VampiricThrallRenderer;
import moriyashiine.nycto.client.renderer.entity.state.VampiricThrallRenderState;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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
	private static final Map<Tuple<EntityType<?>, Boolean>, Identifier> THRALL_IDENTIFIERS = new HashMap<>();

	protected LivingEntityRendererMixin(EntityRendererProvider.Context context) {
		super(context);
	}

	@ModifyExpressionValue(method = "getRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getTextureLocation(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;)Lnet/minecraft/resources/Identifier;"))
	private Identifier nycto$vampiricThrall(Identifier original, S state) {
		VampiricThrallRenderState vampiricThrallRenderState = state.getData(VampiricThrallRenderState.KEY);
		if (vampiricThrallRenderState != null && vampiricThrallRenderState.thrallTexture != null) {
			return vampiricThrallRenderState.thrallTexture;
		}
		return original;
	}

	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V", at = @At("TAIL"))
	private void nycto$vampiricThrall(T entity, S state, float partialTicks, CallbackInfo ci) {
		VampiricThrallRenderState vampiricThrallRenderState = new VampiricThrallRenderState();
		if (NyctoClientAPI.hasVampiricThrallTexture(entity)) {
			vampiricThrallRenderState.thrallTexture = Objects.requireNonNullElse(VampiricThrallRenderer.getTexture(entity), THRALL_IDENTIFIERS.computeIfAbsent(new Tuple<>(entity.getType(), entity.isBaby()), tuple -> {
				Identifier key = BuiltInRegistries.ENTITY_TYPE.getKey(tuple.getA());
				return Nycto.id("textures/entity/vampiric_thrall/" + key.getNamespace() + "/" + key.getPath() + (tuple.getB() ? "_baby" : "") + ".png");
			}));
		}
		state.setData(VampiricThrallRenderState.KEY, vampiricThrallRenderState);
	}
}
