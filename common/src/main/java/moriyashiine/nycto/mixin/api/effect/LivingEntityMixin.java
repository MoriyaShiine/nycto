package moriyashiine.nycto.mixin.api.effect;

import moriyashiine.nycto.api.world.effect.UpdatableMobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(method = "onEffectUpdated", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;refreshDirtyAttributes()V"))
	private void nycto$effect(MobEffectInstance effect, boolean doRefreshAttributes, Entity source, CallbackInfo ci) {
		if (effect.getEffect().value() instanceof UpdatableMobEffect updatableMobEffect) {
			updatableMobEffect.onEffectAdded(effect, (LivingEntity) (Object) this);
		}
	}
}
