/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import moriyashiine.nycto.neoforge.power.NyctoPowerRegistry;
import moriyashiine.nycto.neoforge.network.SyncPlayerDataPayload;
import moriyashiine.nycto.neoforge.registry.NyctoMobEffects;
import moriyashiine.nycto.neoforge.registry.NyctoSoundEvents;
import net.minecraft.commands.Commands;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Optional;
import java.util.Set;

public final class NyctoGameplay {
	private static final AttributeModifier VAMPIRE_SPEED = new AttributeModifier(NyctoItems.id("vampire_speed"), 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
	private static final AttributeModifier VAMPIRE_DAMAGE = new AttributeModifier(NyctoItems.id("vampire_damage"), 1.0, AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier VAMPIRE_SAFE_FALL = new AttributeModifier(NyctoItems.id("vampire_safe_fall"), 1.0, AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier VAMPIRE_STEP_HEIGHT = new AttributeModifier(NyctoItems.id("vampire_step_height"), 1.0, AttributeModifier.Operation.ADD_VALUE);

	private NyctoGameplay() {
	}

	public static void registerCommands(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("nycto")
				.requires(source -> source.hasPermission(2))
				.then(Commands.literal("vampire")
						.then(Commands.argument("value", BoolArgumentType.bool())
								.executes(context -> {
									ServerPlayer player = context.getSource().getPlayerOrException();
									boolean vampire = BoolArgumentType.getBool(context, "value");
									NyctoData.setVampire(player, vampire);
									if (vampire) {
										syncVitals(player);
									} else {
										removeVampireAttributes(player);
									}
									context.getSource().sendSuccess(() -> message(vampire ? "commands.nycto.vampire.enabled" : "commands.nycto.vampire.disabled", vampire ? "\u5df2\u8bbe\u4e3a\u5438\u8840\u9b3c" : "\u5df2\u6062\u590d\u4e3a\u4eba\u7c7b"), true);
									return 1;
								})))
				.then(Commands.literal("blood")
						.then(Commands.literal("get")
								.executes(context -> {
									ServerPlayer player = context.getSource().getPlayerOrException();
									context.getSource().sendSuccess(() -> message("commands.nycto.blood.get", "\u5f53\u524d\u8840\u6db2\uff1a%s/%s", NyctoData.getBlood(player), NyctoData.getMaxBlood(player)), false);
									return NyctoData.getBlood(player);
								}))
						.then(Commands.literal("set")
								.then(Commands.argument("amount", IntegerArgumentType.integer(0))
										.executes(context -> {
											ServerPlayer player = context.getSource().getPlayerOrException();
											int blood = NyctoData.setBlood(player, IntegerArgumentType.getInteger(context, "amount"));
											context.getSource().sendSuccess(() -> message("commands.nycto.blood.set", "\u8840\u6db2\u5df2\u8bbe\u4e3a\uff1a%s/%s", blood, NyctoData.getMaxBlood(player)), true);
											return blood;
										})))
						.then(Commands.literal("add")
								.then(Commands.argument("amount", IntegerArgumentType.integer())
										.executes(context -> {
											ServerPlayer player = context.getSource().getPlayerOrException();
											int blood = NyctoData.addBlood(player, IntegerArgumentType.getInteger(context, "amount"));
											context.getSource().sendSuccess(() -> message("commands.nycto.blood.add", "\u8840\u6db2\u5df2\u8c03\u6574\u4e3a\uff1a%s/%s", blood, NyctoData.getMaxBlood(player)), true);
											return blood;
										}))))
				.then(Commands.literal("power")
						.then(Commands.literal("add")
								.then(Commands.argument("name", StringArgumentType.word())
										.executes(context -> {
											ServerPlayer player = context.getSource().getPlayerOrException();
											String power = StringArgumentType.getString(context, "name");
											String normalized = NyctoData.normalizePowerName(power);
											boolean weakness = NyctoData.isRejectedWeakness(normalized);
											boolean added = !weakness && NyctoData.addPower(player, normalized);
											String fallback = weakness ? "\u5df2\u62d2\u7edd\u5f31\u70b9\u80fd\u529b\uff1a%s" : added ? "\u5df2\u6dfb\u52a0\u80fd\u529b\uff1a%s" : "\u5df2\u62e5\u6709\u80fd\u529b\uff1a%s";
											String key = weakness ? "commands.nycto.power.rejected" : added ? "commands.nycto.power.added" : "commands.nycto.power.duplicate";
											context.getSource().sendSuccess(() -> message(key, fallback, normalized), true);
											return added ? 1 : 0;
										})))
						.then(Commands.literal("remove")
								.then(Commands.argument("name", StringArgumentType.word())
										.executes(context -> {
											ServerPlayer player = context.getSource().getPlayerOrException();
											String power = NyctoData.normalizePowerName(StringArgumentType.getString(context, "name"));
											boolean removed = NyctoData.removePower(player, power);
											context.getSource().sendSuccess(() -> message(removed ? "commands.nycto.power.removed" : "commands.nycto.power.missing", removed ? "\u5df2\u79fb\u9664\u80fd\u529b\uff1a%s" : "\u672a\u62e5\u6709\u80fd\u529b\uff1a%s", power), true);
											return removed ? 1 : 0;
										})))
						.then(Commands.literal("list")
								.executes(context -> {
									ServerPlayer player = context.getSource().getPlayerOrException();
									Set<String> powers = NyctoData.getPowers(player);
									String activePower = NyctoData.getActivePower(player).orElse("\u65e0");
									String list = powers.isEmpty() ? "\u65e0" : String.join(", ", powers);
									context.getSource().sendSuccess(() -> message("commands.nycto.power.list", "\u80fd\u529b\u5217\u8868\uff1a%s\uff1b\u5f53\u524d\uff1a%s", list, activePower), false);
									return powers.size();
								}))
						.then(Commands.literal("use")
								.then(Commands.argument("name", StringArgumentType.word())
										.executes(context -> {
											ServerPlayer player = context.getSource().getPlayerOrException();
											String power = NyctoData.normalizePowerName(StringArgumentType.getString(context, "name"));
											boolean used = usePower(player, power);
											context.getSource().sendSuccess(() -> powerUseMessage(player, power, used), true);
											return used ? 1 : 0;
										})))));
	}

	public static void onPlayerTick(PlayerTickEvent.Post event) {
		if (!(event.getEntity() instanceof ServerPlayer player)) {
			return;
		}
		NyctoData.tickCooldowns(player);
		if (!NyctoData.isVampire(player) && tickVampirism(player)) {
			return;
		}
		if (!NyctoData.isVampire(player)) {
			removeVampireAttributes(player);
			return;
		}
		syncVitals(player);
		if (player.tickCount % 200 == 0) {
			NyctoData.addBlood(player, -1);
		}
		if (NyctoData.hasPower(player, "night_vision") && NyctoData.isNightVisionEnabled(player)) {
			player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 260, 0, true, false));
			player.addEffect(new MobEffectInstance(NyctoMobEffects.NIGHT_VISION, 260, 0, true, true));
		}
		for (String power : NyctoData.getPowers(player)) {
			NyctoPowerRegistry.tick(player, power);
		}
	}

	public static void clonePlayer(PlayerEvent.Clone event) {
		if (event.getOriginal() instanceof ServerPlayer original && event.getEntity() instanceof ServerPlayer target) {
			NyctoData.copy(original, target);
			syncPlayer(target);
		}
	}

	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			if (NyctoData.isVampire(player)) {
				syncVitals(player);
			}
			syncPlayer(player);
		}
	}

	public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			if (NyctoData.isVampire(player)) {
				syncVitals(player);
			}
			syncPlayer(player);
		}
	}

	public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			if (NyctoData.isVampire(player)) {
				syncVitals(player);
			}
			syncPlayer(player);
		}
	}

	private static boolean tickVampirism(ServerPlayer player) {
		MobEffectInstance vampirism = player.getEffect(NyctoMobEffects.VAMPIRISM);
		if (vampirism == null) {
			return false;
		}
		if (vampirism.getDuration() <= 1) {
			player.removeEffect(NyctoMobEffects.VAMPIRISM);
			NyctoData.setVampire(player, true);
			NyctoData.setBlood(player, NyctoData.DEFAULT_STARTING_BLOOD);
			NyctoData.addPower(player, "night_vision");
			syncVitals(player);
			player.level().playSound(null, player.getX(), player.getY(), player.getZ(), NyctoSoundEvents.GENERIC_TRANSFORM_VAMPIRE.get(), player.getSoundSource(), 1, 1);
			player.sendSystemMessage(Component.translatable("message.nycto.vampire_transformed"));
			syncPlayer(player);
		}
		return true;
	}

	public static boolean useActivePower(ServerPlayer player) {
		Optional<String> activePower = NyctoData.getActivePower(player);
		return activePower.isPresent() && usePower(player, activePower.get());
	}

	public static boolean usePower(ServerPlayer player, String power) {
		boolean used = NyctoPowerRegistry.use(player, power);
		syncPlayer(player);
		return used;
	}

	public static void syncPlayer(ServerPlayer player) {
		SyncPlayerDataPayload.send(player);
	}

	static void syncVitals(ServerPlayer player) {
		addAttribute(player, Attributes.MOVEMENT_SPEED, VAMPIRE_SPEED);
		addAttribute(player, Attributes.ATTACK_DAMAGE, VAMPIRE_DAMAGE);
		addAttribute(player, Attributes.SAFE_FALL_DISTANCE, VAMPIRE_SAFE_FALL);
		addAttribute(player, Attributes.STEP_HEIGHT, VAMPIRE_STEP_HEIGHT);
	}

	private static void addAttribute(ServerPlayer player, Holder<Attribute> attribute, AttributeModifier modifier) {
		AttributeInstance instance = player.getAttribute(attribute);
		if (instance != null && !instance.hasModifier(modifier.id())) {
			instance.addTransientModifier(modifier);
		}
	}

	private static void removeVampireAttributes(ServerPlayer player) {
		removeAttribute(player, Attributes.MOVEMENT_SPEED, VAMPIRE_SPEED);
		removeAttribute(player, Attributes.ATTACK_DAMAGE, VAMPIRE_DAMAGE);
		removeAttribute(player, Attributes.SAFE_FALL_DISTANCE, VAMPIRE_SAFE_FALL);
		removeAttribute(player, Attributes.STEP_HEIGHT, VAMPIRE_STEP_HEIGHT);
	}

	private static void removeAttribute(ServerPlayer player, Holder<Attribute> attribute, AttributeModifier modifier) {
		AttributeInstance instance = player.getAttribute(attribute);
		if (instance != null && instance.hasModifier(modifier.id())) {
			instance.removeModifier(modifier);
		}
	}

	private static Component powerUseMessage(ServerPlayer player, String power, boolean used) {
		if (used) {
			return message("commands.nycto.power.used", "\u5df2\u4f7f\u7528\u80fd\u529b\uff1a%s", power);
		}
		if (!NyctoData.isVampire(player)) {
			return message("commands.nycto.power.not_vampire", "\u4f60\u4e0d\u662f\u5438\u8840\u9b3c");
		}
		if (!NyctoData.hasPower(player, power)) {
			return message("commands.nycto.power.missing", "\u672a\u62e5\u6709\u80fd\u529b\uff1a%s", power);
		}
		int cooldown = NyctoData.getCooldown(player, power);
		if (cooldown > 0) {
			return message("commands.nycto.power.cooldown", "\u80fd\u529b\u51b7\u5374\u4e2d\uff1a%s tick", cooldown);
		}
		if (!NyctoPowerRegistry.isRegistered(power)) {
			return message("commands.nycto.power.unregistered", "\u80fd\u529b\u5c1a\u672a\u63a5\u5165\uff1a%s", power);
		}
		return message("commands.nycto.power.failed", "\u80fd\u529b\u65e0\u6cd5\u4f7f\u7528\uff1a%s", power);
	}

	private static Component message(String key, String fallback, Object... args) {
		return Component.translatableWithFallback(key, fallback, args);
	}
}
