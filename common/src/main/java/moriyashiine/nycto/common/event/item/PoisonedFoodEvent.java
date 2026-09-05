package moriyashiine.nycto.common.event.item;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.NyctoDamageTypes;
import moriyashiine.nycto.common.init.NyctoDataComponents;
import moriyashiine.strawberrylib.api.event.FoodEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PoisonedFoodEvent implements FoodEvents.Eat {
	public static void init() {
		FoodEvents.EAT.register(new PoisonedFoodEvent());
	}

	@Override
	public void eat(Level level, LivingEntity user, ItemStack stack) {
		if (level instanceof ServerLevel serverLevel && stack.getOrDefault(NyctoDataComponents.POISONED, false)) {
			user.addEffect(new MobEffectInstance(MobEffects.POISON, 400, 1));
			if (NyctoAPI.isWerewolf(user)) {
				user.hurtServer(serverLevel, level.damageSources().source(NyctoDamageTypes.TOXIC_TOUCH), Float.MAX_VALUE);
			}
		}
	}
}
