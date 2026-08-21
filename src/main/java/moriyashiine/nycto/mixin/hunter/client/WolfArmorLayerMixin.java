package moriyashiine.nycto.mixin.hunter.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.api.init.NyctoRegistries;
import net.minecraft.client.renderer.entity.layers.WolfArmorLayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WolfArmorLayer.class)
public class WolfArmorLayerMixin {
	@ModifyExpressionValue(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/WolfRenderState;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;get(Lnet/minecraft/core/component/DataComponentType;)Ljava/lang/Object;"))
	private Object nycto$hunter(Object original, @Local(name = "armorItem") ItemStack armorItem) {
		if (NyctoRegistries.HUNTER_TYPE.stream().anyMatch(type -> armorItem.is(type.armorTagKey))) {
			return null;
		}
		return original;
	}
}
