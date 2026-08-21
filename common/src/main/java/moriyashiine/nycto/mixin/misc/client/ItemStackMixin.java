package moriyashiine.nycto.mixin.misc.client;

import moriyashiine.nycto.client.event.ItemDescriptionsClientEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	@Inject(method = "addAttributeTooltips", at = @At("HEAD"))
	private void nycto$itemDescriptions(Consumer<Component> consumer, TooltipDisplay display, @Nullable Player player, CallbackInfo ci) {
		for (ItemDescriptionsClientEvent.TooltipAdder adder : ItemDescriptionsClientEvent.ARMOR_SET_BONUSES) {
			adder.getTooltip((ItemStack) (Object) this).ifPresent(texts -> texts.forEach(consumer));
		}
	}
}
