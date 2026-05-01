/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.hunter;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.nycto.api.init.NyctoRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Wolf.class)
public abstract class WolfMixin extends TamableAnimal {
	protected WolfMixin(EntityType<? extends TamableAnimal> type, Level level) {
		super(type, level);
	}

	@ModifyExpressionValue(method = "canArmorAbsorb", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Ljava/lang/Object;)Z"))
	private boolean nycto$hunter(boolean original) {
		return original || NyctoRegistries.HUNTER_TYPE.stream().anyMatch(type -> getBodyArmorItem().is(type.armorTagKey));
	}
}
