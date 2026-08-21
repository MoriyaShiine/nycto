package moriyashiine.nycto.fabric.mixin.api.power.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.nycto.client.event.PowerClientEvent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Shadow
	@Nullable
	public LocalPlayer player;

	@ModifyExpressionValue(method = "startUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z", ordinal = 1))
	private boolean nycto$power(boolean original) {
		return original && !PowerClientEvent.isActive(player, NyctoEntityComponents.TRANSFORMATION.get(player));
	}
}
