/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.event;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.NyctoData;
import moriyashiine.nycto.neoforge.network.ThrallClientState;
import moriyashiine.nycto.neoforge.power.NyctoPowers;
import moriyashiine.nycto.neoforge.registry.NyctoParticleTypes;
import moriyashiine.nycto.neoforge.registry.NyctoTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@EventBusSubscriber(modid = NyctoNeoForge.MOD_ID, value = Dist.CLIENT)
public final class VampiricThrallClientEvents {
	private static final int OUTLINE_COLOR = 0x7F00FF;
	private static final Set<Integer> LOCAL_GLOWING = new HashSet<>();
	private static final Map<Integer, Boolean> PREVIOUS_GLOWING = new HashMap<>();

	private VampiricThrallClientEvents() {
	}

	@SubscribeEvent
	public static void onClientTick(ClientTickEvent.Post event) {
		Minecraft minecraft = Minecraft.getInstance();
		Player player = minecraft.player;
		ClientLevel level = minecraft.level;
		if (level == null || player == null || !isSelectingThrallPower(player)) {
			if (level != null) {
				ThrallClientState.pruneMissing(level);
			}
			clearLocalGlow(minecraft);
			return;
		}
		ThrallClientState.pruneMissing(level);
		Set<Integer> current = new HashSet<>();
		for (Entity entity : level.entitiesForRendering()) {
			if (entity instanceof Mob mob && mob.distanceTo(player) < 16 && canBeThralledBy(player, mob)) {
				addThrallHintParticles(level, mob);
				if (minecraft.crosshairPickEntity == mob) {
					PREVIOUS_GLOWING.putIfAbsent(mob.getId(), mob.isCurrentlyGlowing());
					mob.setGlowingTag(true);
					current.add(mob.getId());
				}
			}
		}
		restoreNoLongerHighlighted(minecraft, current);
		LOCAL_GLOWING.clear();
		LOCAL_GLOWING.addAll(current);
	}

	@SubscribeEvent
	public static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
		ThrallClientState.clear();
		clearLocalGlow(Minecraft.getInstance());
	}

	public static int outlineColor(Entity entity) {
		return LOCAL_GLOWING.contains(entity.getId()) ? OUTLINE_COLOR : -1;
	}

	private static boolean isSelectingThrallPower(Player player) {
		return NyctoData.isVampire(player) && NyctoData.getActivePower(player).filter(NyctoPowers.VAMPIRIC_THRALL::equals).isPresent();
	}

	private static boolean canBeThralledBy(Player player, Mob mob) {
		if (ThrallClientState.isThrall(mob) || !mob.isAlive() || mob.getType().is(NyctoTags.CANNOT_BE_TARGETED_BY_THRALLS) || !mob.getType().is(NyctoTags.CAN_BE_THRALLED)) {
			return false;
		}
		if (mob instanceof AbstractHorse horse) {
			return horse.isTamed() && player.getUUID().equals(horse.getOwnerUUID());
		}
		if (mob instanceof TamableAnimal tamable) {
			return tamable.isTame() && tamable.isOwnedBy(player);
		}
		return mob.getHealth() <= 15;
	}

	private static void addThrallHintParticles(ClientLevel level, Mob mob) {
		if (mob.tickCount % 6 == 0) {
			double x = mob.getX() + (mob.getRandom().nextDouble() - 0.5) * mob.getBbWidth();
			double y = mob.getY() + mob.getBbHeight() + 0.35;
			double z = mob.getZ() + (mob.getRandom().nextDouble() - 0.5) * mob.getBbWidth();
			level.addParticle(NyctoParticleTypes.HYPNOSIS_INDICATOR.get(), x, y, z, 0, 0.02, 0);
		}
		if (mob.tickCount % 12 == 0) {
			double x = mob.getX() + (mob.getRandom().nextDouble() - 0.5) * mob.getBbWidth();
			double y = mob.getY() + mob.getRandom().nextDouble() * mob.getBbHeight();
			double z = mob.getZ() + (mob.getRandom().nextDouble() - 0.5) * mob.getBbWidth();
			level.addParticle(NyctoParticleTypes.HYPNOSIS_STAR.get(), x, y, z, 0, 0.01, 0);
			level.addParticle(NyctoParticleTypes.HYPNOSIS_SMALL.get(), x, y + 0.1, z, 0, 0.01, 0);
		}
	}

	private static void restoreNoLongerHighlighted(Minecraft minecraft, Set<Integer> current) {
		for (int id : LOCAL_GLOWING) {
			if (!current.contains(id)) {
				Entity entity = minecraft.level == null ? null : minecraft.level.getEntity(id);
				if (entity != null) {
					entity.setGlowingTag(PREVIOUS_GLOWING.getOrDefault(id, false));
				}
				PREVIOUS_GLOWING.remove(id);
			}
		}
	}

	private static void clearLocalGlow(Minecraft minecraft) {
		if (minecraft.level != null) {
			for (int id : LOCAL_GLOWING) {
				Entity entity = minecraft.level.getEntity(id);
				if (entity != null) {
					entity.setGlowingTag(PREVIOUS_GLOWING.getOrDefault(id, false));
				}
			}
		}
		LOCAL_GLOWING.clear();
		PREVIOUS_GLOWING.clear();
	}
}
