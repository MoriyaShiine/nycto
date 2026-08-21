package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.mobspecific;

import moriyashiine.nycto.common.init.NyctoEntityComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.npc.villager.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Zombie.class)
public class ZombieMixin {
	@Inject(method = "convertVillagerToZombieVillager", at = @At("HEAD"), cancellable = true)
	private void nycto$vampiricThrall(ServerLevel level, Villager villager, CallbackInfoReturnable<Boolean> cir) {
		if (NyctoEntityComponents.VAMPIRIC_THRALL.get(villager).hasOwner()) {
			cir.setReturnValue(false);
		}
	}
}
