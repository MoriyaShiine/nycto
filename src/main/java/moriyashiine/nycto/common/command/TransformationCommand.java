/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.transformation.Transformation;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.RegistryEntryReferenceArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Collection;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class TransformationCommand implements CommandRegistrationCallback {
	@Override
	public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
		dispatcher.register(literal("transformation").requires(src -> src.hasPermissionLevel(4))
				.then(literal("get")
						.then(argument("player", EntityArgumentType.player())
								.executes(ctx -> {
									ServerPlayerEntity player = EntityArgumentType.getPlayer(ctx, "player");
									ctx.getSource().sendFeedback(() -> Text.translatable("command.nycto.transformation.get", player.getName(), Text.translatable(NyctoAPI.getTransformation(player).getTranslationKey())), false);
									return Command.SINGLE_SUCCESS;
								})))
				.then(literal("set")
						.then(argument("players", EntityArgumentType.players())
								.then(argument("transformation", RegistryEntryReferenceArgumentType.registryEntry(registryAccess, NyctoRegistries.TRANSFORMATION_KEY))
										.executes(ctx -> {
											Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(ctx, "players");
											Transformation transformation = RegistryEntryReferenceArgumentType.getRegistryEntry(ctx, "transformation", NyctoRegistries.TRANSFORMATION_KEY).value();
											for (ServerPlayerEntity player : players) {
												NyctoAPI.setTransformation(player, transformation);
											}
											if (players.size() == 1) {
												ctx.getSource().sendFeedback(() -> Text.translatable("command.nycto.transformation.set.single", players.stream().findFirst().get().getName(), Text.translatable(transformation.getTranslationKey())), true);
											} else {
												ctx.getSource().sendFeedback(() -> Text.translatable("command.nycto.transformation.set.multiple", players.size(), Text.translatable(transformation.getTranslationKey())), true);
											}
											return Command.SINGLE_SUCCESS;
										})))));
	}
}
