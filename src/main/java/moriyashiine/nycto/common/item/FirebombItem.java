/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.item;

import moriyashiine.nycto.common.entity.projectile.FirebombEntity;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public class FirebombItem extends Item implements ProjectileItem {
	private static final float POWER = 0.75F;

	public FirebombItem(Item.Settings settings) {
		super(settings);
		DispenserBlock.registerProjectileBehavior(this);
	}

	@Override
	public ActionResult use(World world, PlayerEntity user, Hand hand) {
		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
		ItemStack stack = user.getStackInHand(hand);
		if (world instanceof ServerWorld serverWorld) {
			ProjectileEntity.spawnWithVelocity(FirebombEntity::new, serverWorld, stack, user, -20, POWER, 1);
		}
		user.incrementStat(Stats.USED.getOrCreateStat(this));
		stack.decrementUnlessCreative(1, user);
		return ActionResult.SUCCESS;
	}

	@Override
	public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
		return new FirebombEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
	}

	@Override
	public ProjectileItem.Settings getProjectileSettings() {
		return ProjectileItem.Settings.builder()
				.uncertainty(ProjectileItem.Settings.DEFAULT.uncertainty() * 0.5F)
				.power(ProjectileItem.Settings.DEFAULT.power() * 1.25F)
				.build();
	}
}
