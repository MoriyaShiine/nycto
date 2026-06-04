/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.event;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.client.KeenSensesClientState;
import moriyashiine.nycto.neoforge.network.MistFormClientState;
import moriyashiine.nycto.neoforge.registry.NyctoHunterContent;
import moriyashiine.nycto.neoforge.util.NyctoBloodUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderNameTagEvent;
import net.neoforged.neoforge.common.util.TriState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@EventBusSubscriber(modid = NyctoNeoForge.MOD_ID, value = Dist.CLIENT)
public final class KeenSensesClientRenderEvents {
	private static final int QUALITY_BLOOD_COLOR = 0xFF0000;
	private static final int BLOOD_COLOR = 0xFFFFFF;
	private static final int NO_BLOOD_COLOR = 0x3F3F3F;
	private static final int EMPTY_HEART_COLOR = 0x3F3F3F;
	private static final int GARLIC_WREATH_AURA_RADIUS = 12;

	private static final Set<Integer> LOCAL_GLOWING = new HashSet<>();
	private static final Map<Integer, Boolean> PREVIOUS_GLOWING = new HashMap<>();

	private KeenSensesClientRenderEvents() {
	}

	@SubscribeEvent
	public static void onClientTick(ClientTickEvent.Post event) {
		Minecraft minecraft = Minecraft.getInstance();
		Player player = minecraft.player;
		if (minecraft.level == null || player == null || !KeenSensesClientState.isActive(player)) {
			clearLocalGlow(minecraft);
			return;
		}
		Set<Integer> current = new HashSet<>();
		for (Entity entity : minecraft.level.entitiesForRendering()) {
			if (shouldHighlight(player, entity)) {
				PREVIOUS_GLOWING.putIfAbsent(entity.getId(), entity.isCurrentlyGlowing());
				entity.setGlowingTag(true);
				current.add(entity.getId());
			}
		}
		for (int id : LOCAL_GLOWING) {
			if (!current.contains(id)) {
				Entity entity = minecraft.level.getEntity(id);
				if (entity != null) {
					entity.setGlowingTag(PREVIOUS_GLOWING.getOrDefault(id, false));
				}
				PREVIOUS_GLOWING.remove(id);
			}
		}
		LOCAL_GLOWING.clear();
		LOCAL_GLOWING.addAll(current);
	}

	@SubscribeEvent
	public static void onRenderNameTag(RenderNameTagEvent event) {
		Minecraft minecraft = Minecraft.getInstance();
		Player player = minecraft.player;
		Entity entity = event.getEntity();
		if (player == null || !(entity instanceof LivingEntity living) || !shouldHighlight(player, living)) {
			return;
		}
		event.setCanRender(TriState.TRUE);
		event.setContent(healthText(living));
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

	private static boolean shouldHighlight(Player player, Entity entity) {
		if (!(entity instanceof LivingEntity living) || entity == player || !living.isAlive()) {
			return false;
		}
		if (!(living instanceof Player || living instanceof Mob)) {
			return false;
		}
		int distance = KeenSensesClientState.distance();
		if (MistFormClientState.isActive(living)) {
			distance /= 4;
		}
		return distance > 0 && living.distanceTo(player) <= distance && !hasGarlicAura(living);
	}

	public static int outlineColor(Entity entity) {
		if (entity instanceof LivingEntity living && LOCAL_GLOWING.contains(entity.getId())) {
			return bloodColor(living);
		}
		return -1;
	}

	private static Component healthText(LivingEntity living) {
		float percentage = living.getMaxHealth() <= 0 ? 0 : living.getHealth() / living.getMaxHealth();
		Style filled = Style.EMPTY.withColor(TextColor.fromRgb(bloodColor(living)));
		Style empty = Style.EMPTY.withColor(TextColor.fromRgb(EMPTY_HEART_COLOR));
		MutableComponent health = Component.empty();
		for (int i = 0; i < 8; i++) {
			health.append(Component.literal("\u2665").withStyle(i / 8F < percentage ? filled : empty));
		}
		return health;
	}

	private static int bloodColor(LivingEntity living) {
		if (!NyctoBloodUtil.hasDrainableBlood(living)) {
			return NO_BLOOD_COLOR;
		}
		if (NyctoBloodUtil.hasQualityBloodType(living)) {
			return QUALITY_BLOOD_COLOR;
		}
		return BLOOD_COLOR;
	}

	private static boolean hasGarlicAura(LivingEntity living) {
		if (living.hasEffect(NyctoHunterContent.VAMPIRE_WARD)) {
			return true;
		}
		if (hunterArmorPieces(living) >= 2) {
			return true;
		}
		Level level = living.level();
		BlockPos origin = living.blockPosition();
		BlockPos min = origin.offset(-GARLIC_WREATH_AURA_RADIUS, -GARLIC_WREATH_AURA_RADIUS, -GARLIC_WREATH_AURA_RADIUS);
		BlockPos max = origin.offset(GARLIC_WREATH_AURA_RADIUS, GARLIC_WREATH_AURA_RADIUS, GARLIC_WREATH_AURA_RADIUS);
		for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
			if (pos.closerToCenterThan(living.position(), GARLIC_WREATH_AURA_RADIUS) && level.getBlockState(pos).is(NyctoHunterContent.GARLIC_WREATH.get())) {
				return true;
			}
		}
		return false;
	}

	private static int hunterArmorPieces(LivingEntity living) {
		int count = 0;
		for (ItemStack stack : living.getArmorSlots()) {
			if (stack.is(NyctoHunterContent.VAMPIRE_HUNTER_HELMET.get()) || stack.is(NyctoHunterContent.VAMPIRE_HUNTER_CHESTPLATE.get()) || stack.is(NyctoHunterContent.VAMPIRE_HUNTER_LEGGINGS.get()) || stack.is(NyctoHunterContent.VAMPIRE_HUNTER_BOOTS.get())) {
				count++;
			}
		}
		return count;
	}
}
