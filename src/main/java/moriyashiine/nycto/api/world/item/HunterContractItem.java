/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.api.world.item;

import moriyashiine.nycto.common.init.ModEntityTypes;
import moriyashiine.nycto.common.world.entity.monster.Hunter;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class HunterContractItem extends Item {
	private static final Component SUCCEED_TEXT = Component.translatable("message.nycto.hunter_contract.succeed");
	private static final Component FAIL_TEXT = Component.translatable("message.nycto.hunter_contract.fail");

	private final Hunter.HunterType type;

	public HunterContractItem(Properties properties, Hunter.HunterType type) {
		super(properties);
		this.type = type;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		return use(context.getPlayer(), context.getHand(), spawnHunter(context.getLevel(), context.getPlayer(), type, 1));
	}

	@Override
	public InteractionResult use(Level level, Player user, InteractionHand hand) {
		return use(user, hand, spawnHunter(level, user, type, 1));
	}

	private static InteractionResult use(Player player, InteractionHand hand, InteractionResult result) {
		if (player != null) {
			player.sendOverlayMessage(result.consumesAction() ? SUCCEED_TEXT : FAIL_TEXT);
			if (result.consumesAction()) {
				player.getItemInHand(hand).consume(1, player);
			}
		}
		return result;
	}

	public static InteractionResult spawnHunter(Level level, Player player, Hunter.HunterType type, int amount) {
		if (level.getDifficulty() == Difficulty.PEACEFUL) {
			return InteractionResult.FAIL;
		}
		final int minH = 24, maxH = 48;
		for (int i = 0; i < 8; i++) {
			boolean spawnedAny = false;
			int dX = player.getRandom().nextIntBetweenInclusive(minH, maxH) * (player.getRandom().nextBoolean() ? 1 : -1);
			int dY = player.getRandom().nextIntBetweenInclusive(-6, 6);
			int dZ = player.getRandom().nextIntBetweenInclusive(minH, maxH) * (player.getRandom().nextBoolean() ? 1 : -1);
			for (int j = 0; j < amount; j++) {
				Hunter hunter = ModEntityTypes.HUNTER.create(level, EntitySpawnReason.TRIGGERED);
				if (hunter.randomTeleport(player.getX() + dX, player.getY() + dY, player.getZ() + dZ, false)) {
					if (level instanceof ServerLevel serverLevel) {
						hunter.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(hunter.blockPosition()), EntitySpawnReason.TRIGGERED, null);
						if (type.shouldTarget(player) && !player.isCreative()) {
							hunter.setUltimateTarget(player);
						} else {
							hunter.setContractPos(player.blockPosition());
						}
						boolean hasHorse = hunter.getRandom().nextInt(8) == 0;
						hunter.equipGear(type, hasHorse);
						serverLevel.addFreshEntity(hunter);
						if (hasHorse) {
							Hunter.mountHorse(serverLevel, hunter);
						}
					}
					spawnedAny = true;
				}
			}
			if (spawnedAny) {
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.FAIL;
	}
}
