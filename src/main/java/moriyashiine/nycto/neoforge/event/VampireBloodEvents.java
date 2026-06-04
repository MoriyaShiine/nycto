/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.event;

import moriyashiine.nycto.neoforge.NyctoData;
import moriyashiine.nycto.neoforge.hunter.NyctoHunterUtil;
import moriyashiine.nycto.neoforge.registry.NyctoSoundEvents;
import moriyashiine.nycto.neoforge.util.NyctoBloodUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public final class VampireBloodEvents {
	private static final int BLOOD_VEIL_COST = 10;
	private static final String BLOOD_VEIL_COOLDOWN = "nycto_blood_veil_cooldown";
	private static final int BLOOD_VEIL_COOLDOWN_TICKS = 60;

	private VampireBloodEvents() {
	}

	public static void onLivingDeath(LivingDeathEvent event) {
		if (!(event.getEntity() instanceof ServerPlayer player) || !NyctoData.isVampire(player)) {
			return;
		}
		long gameTime = player.level().getGameTime();
		if (player.getPersistentData().getLong(BLOOD_VEIL_COOLDOWN) > gameTime || NyctoData.getBlood(player) < BLOOD_VEIL_COST) {
			return;
		}
		if (NyctoHunterUtil.isVampireWeaknessDamage(event.getSource())) {
			return;
		}
		event.setCanceled(true);
		NyctoData.addBlood(player, -BLOOD_VEIL_COST);
		player.getPersistentData().putLong(BLOOD_VEIL_COOLDOWN, gameTime + BLOOD_VEIL_COOLDOWN_TICKS);
		player.setHealth(1);
		player.level().playSound(null, player.getX(), player.getY(), player.getZ(), NyctoSoundEvents.GENERIC_BLOOD_DRAIN_BLOCKED.get(), SoundSource.PLAYERS, 1, 0.8F);
		player.displayClientMessage(Component.translatable("message.nycto.blood_veil", BLOOD_VEIL_COST), true);
	}

	public static void onEntityTick(EntityTickEvent.Post event) {
		if (event.getEntity() instanceof LivingEntity living && !living.level().isClientSide()) {
			NyctoBloodUtil.tickEntityBlood(living);
		}
	}
}
