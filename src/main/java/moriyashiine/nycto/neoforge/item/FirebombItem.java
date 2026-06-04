/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.item;

import moriyashiine.nycto.neoforge.entity.projectile.FirebombProjectile;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public final class FirebombItem extends Item implements ProjectileItem {
	private static final float POWER = 0.75F;

	public FirebombItem(Properties properties) {
		super(properties);
		DispenserBlock.registerProjectileBehavior(this);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SPLASH_POTION_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
		if (level instanceof ServerLevel serverLevel) {
			FirebombProjectile projectile = new FirebombProjectile(serverLevel, player, stack);
			projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), -20, POWER, 1);
			serverLevel.addFreshEntity(projectile);
		}
		player.awardStat(Stats.ITEM_USED.get(this));
		stack.consume(1, player);
		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}

	@Override
	public Projectile asProjectile(Level level, Position position, ItemStack stack, Direction direction) {
		return new FirebombProjectile(level, position.x(), position.y(), position.z(), stack);
	}

	@Override
	public DispenseConfig createDispenseConfig() {
		return DispenseConfig.builder()
				.uncertainty(DispenseConfig.DEFAULT.uncertainty() * 0.5F)
				.power(DispenseConfig.DEFAULT.power() * 1.25F)
				.build();
	}
}
