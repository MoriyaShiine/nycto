package moriyashiine.nycto.mixin.misc;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Witch.class)
public class WitchMixin {
	@WrapOperation(method = "performRangedAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/alchemy/PotionContents;createItemStack(Lnet/minecraft/world/item/Item;Lnet/minecraft/core/Holder;)Lnet/minecraft/world/item/ItemStack;"))
	private ItemStack nycto$fixInvertedHealingAndHarm(Item item, Holder<Potion> potion, Operation<ItemStack> original, LivingEntity target) {
		if (potion == Potions.HARMING || potion == Potions.POISON) {
			if (target.isInvertedHealAndHarm()) {
				potion = Potions.HEALING;
			}
		}
		return original.call(item, potion);
	}
}
