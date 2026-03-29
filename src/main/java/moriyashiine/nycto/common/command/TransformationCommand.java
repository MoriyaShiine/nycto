/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.world.transformation.Transformation;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class TransformationCommand implements CommandRegistrationCallback {
	@Override
	public void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext, Commands.CommandSelection selection) {
		dispatcher.register(literal("transformation").requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
				.then(literal("get")
						.then(argument("player", EntityArgument.player())
								.executes(ctx -> {
									ServerPlayer player = EntityArgument.getPlayer(ctx, "player");
									ctx.getSource().sendSuccess(() -> Component.translatable("command.nycto.transformation.get", player.getName(), Component.translatable(NyctoAPI.getTransformation(player).getOrCreateDescriptionId())), false);
									return Command.SINGLE_SUCCESS;
								})))
				.then(literal("set")
						.then(argument("players", EntityArgument.players())
								.then(argument("transformation", ResourceArgument.resource(buildContext, NyctoRegistries.TRANSFORMATION_KEY))
										.executes(ctx -> {
											Collection<ServerPlayer> players = EntityArgument.getPlayers(ctx, "players");
											Transformation transformation = ResourceArgument.getResource(ctx, "transformation", NyctoRegistries.TRANSFORMATION_KEY).value();
											for (ServerPlayer player : players) {
												NyctoAPI.setTransformation(player, transformation);
											}
											if (players.size() == 1) {
												ctx.getSource().sendSuccess(() -> Component.translatable("command.nycto.transformation.set.single", players.stream().findFirst().get().getName(), Component.translatable(transformation.getOrCreateDescriptionId())), true);
											} else {
												ctx.getSource().sendSuccess(() -> Component.translatable("command.nycto.transformation.set.multiple", players.size(), Component.translatable(transformation.getOrCreateDescriptionId())), true);
											}
											return Command.SINGLE_SUCCESS;
										})))));
	}
}
