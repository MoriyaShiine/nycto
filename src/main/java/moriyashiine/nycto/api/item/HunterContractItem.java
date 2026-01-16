/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.api.item;

import moriyashiine.nycto.common.entity.mob.HunterEntity;
import moriyashiine.nycto.common.init.ModEntityTypes;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class HunterContractItem extends Item {
	private static final Text SUCCEED_TEXT = Text.translatable("message.nycto.hunter_contract.succeed");
	private static final Text FAIL_TEXT = Text.translatable("message.nycto.hunter_contract.fail");

	private final HunterEntity.HunterType type;

	public HunterContractItem(Settings settings, HunterEntity.HunterType type) {
		super(settings);
		this.type = type;
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		return use(context.getPlayer(), context.getHand(), spawnHunter(context.getWorld(), context.getPlayer(), type, 1));
	}

	@Override
	public ActionResult use(World world, PlayerEntity user, Hand hand) {
		return use(user, hand, spawnHunter(world, user, type, 1));
	}

	private static ActionResult use(PlayerEntity user, Hand hand, ActionResult result) {
		if (user != null) {
			user.sendMessage(result.isAccepted() ? SUCCEED_TEXT : FAIL_TEXT, true);
			if (result.isAccepted()) {
				user.getStackInHand(hand).decrementUnlessCreative(1, user);
			}
		}
		return result;
	}

	public static ActionResult spawnHunter(World world, PlayerEntity user, HunterEntity.HunterType type, int amount) {
		if (world.getDifficulty() == Difficulty.PEACEFUL) {
			return ActionResult.FAIL;
		}
		final int minH = 24, maxH = 48;
		for (int i = 0; i < 8; i++) {
			boolean spawnedAny = false;
			int dX = user.getRandom().nextBetween(minH, maxH) * (user.getRandom().nextBoolean() ? 1 : -1);
			int dY = user.getRandom().nextBetween(-6, 6);
			int dZ = user.getRandom().nextBetween(minH, maxH) * (user.getRandom().nextBoolean() ? 1 : -1);
			for (int j = 0; j < amount; j++) {
				HunterEntity hunter = ModEntityTypes.HUNTER.create(world, SpawnReason.TRIGGERED);
				if (hunter.teleport(user.getX() + dX, user.getY() + dY, user.getZ() + dZ, false)) {
					if (world instanceof ServerWorld serverWorld) {
						hunter.initialize(serverWorld, serverWorld.getLocalDifficulty(hunter.getBlockPos()), SpawnReason.TRIGGERED, null);
						if (type.shouldTarget(user) && NyctoUtil.isTargetable(user)) {
							hunter.setUltimateTarget(user);
						} else {
							hunter.setContractPos(user.getBlockPos());
						}
						boolean horse = hunter.getRandom().nextInt(8) == 0;
						hunter.equipGear(type, horse);
						serverWorld.spawnEntity(hunter);
						if (horse) {
							HunterEntity.mountHorse(serverWorld, hunter);
						}
					}
					spawnedAny = true;
				}
			}
			if (spawnedAny) {
				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.FAIL;
	}
}
