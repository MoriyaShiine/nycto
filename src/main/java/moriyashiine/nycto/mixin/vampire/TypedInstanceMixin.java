/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.vampire;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.world.transformation.VampireTransformation;
import net.minecraft.core.TypedInstance;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TypedInstance.class)
public interface TypedInstanceMixin {
	@ModifyReturnValue(method = "is(Lnet/minecraft/tags/TagKey;)Z", at = @At("RETURN"))
	private <T> boolean nycto$vampire(boolean original, TagKey<T> tag) {
		if (!original && !VampireTransformation.ignoreIsCalls) {
			if (tag == EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES || tag == EntityTypeTags.UNDEAD || tag == EntityTypeTags.CAN_BREATHE_UNDER_WATER || tag == EntityTypeTags.IGNORES_POISON_AND_REGEN || tag == EntityTypeTags.INVERTED_HEALING_AND_HARM) {
				VampireTransformation.ignoreIsCalls = true;
				boolean vampire = (Object) this instanceof Entity entity && NyctoAPI.isVampire(entity);
				VampireTransformation.ignoreIsCalls = false;
				return vampire;
			}
		}
		return original;
	}
}
