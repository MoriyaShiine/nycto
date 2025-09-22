/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.vampiricdagger.client;

import com.mojang.serialization.MapCodec;
import moriyashiine.nycto.client.render.item.property.bool.FullDaggerProperty;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.render.item.property.bool.BooleanProperties;
import net.minecraft.client.render.item.property.bool.BooleanProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BooleanProperties.class)
public class BooleanPropertiesMixin {
	@Shadow
	@Final
	public static Codecs.IdMapper<Identifier, MapCodec<? extends BooleanProperty>> ID_MAPPER;

	@Inject(method = "bootstrap", at = @At("TAIL"))
	private static void nycto$vampiricDagger(CallbackInfo ci) {
		ID_MAPPER.put(Nycto.id("full_dagger"), FullDaggerProperty.CODEC);
	}
}
