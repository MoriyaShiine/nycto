package moriyashiine.nycto.mixin.power.vampire.vampiricthrall;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public class EntityMixin {
	@ModifyReturnValue(method = "considersEntityAsAlly", at = @At("RETURN"))
	private boolean nycto$vampiricThrall(boolean original, Entity other) {
		if (!original) {
			VampiricThrallComponent vampiricThrall = NyctoEntityComponents.VAMPIRIC_THRALL.getNullable(this);
			if (vampiricThrall != null && vampiricThrall.getOwnerUuid() != null) {
				VampiricThrallComponent otherVampiricThrallComponent = NyctoEntityComponents.VAMPIRIC_THRALL.getNullable(other);
				if (otherVampiricThrallComponent != null && vampiricThrall.getOwnerUuid().equals(otherVampiricThrallComponent.getOwnerUuid())) {
					return true;
				}
			}
		}
		return original;
	}
}
