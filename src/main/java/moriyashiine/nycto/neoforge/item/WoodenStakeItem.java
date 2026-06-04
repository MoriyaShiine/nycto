/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.item;

import moriyashiine.nycto.neoforge.entity.projectile.WoodenStakeProjectile;
import moriyashiine.nycto.neoforge.hunter.NyctoHunterUtil;
import moriyashiine.nycto.neoforge.registry.NyctoBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;

public final class WoodenStakeItem extends ArrowItem {
	public WoodenStakeItem(Properties properties) {
		super(properties);
		DispenserBlock.registerProjectileBehavior(this);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Player player = context.getPlayer();
		if (player != null && !player.isShiftKeyDown()) {
			return InteractionResult.PASS;
		}
		BlockPlaceContext placeContext = new BlockPlaceContext(context);
		if (!placeContext.canPlace()) {
			return InteractionResult.FAIL;
		}
		Level level = context.getLevel();
		if (!level.isClientSide()) {
			BlockState state = NyctoBlocks.WOODEN_STAKE.get().getStateForPlacement(placeContext);
			if (state != null && level.setBlock(placeContext.getClickedPos(), state, 11)) {
				context.getItemInHand().consume(1, player);
			}
		}
		return InteractionResult.sidedSuccess(level.isClientSide());
	}

	@Override
	public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, ItemStack weapon) {
		WoodenStakeProjectile projectile = new WoodenStakeProjectile(level, shooter, ammo.copyWithCount(1), weapon);
		projectile.pickup = shooter.hasInfiniteMaterials() ? AbstractArrow.Pickup.CREATIVE_ONLY : AbstractArrow.Pickup.ALLOWED;
		return projectile;
	}

	@Override
	public Projectile asProjectile(Level level, Position position, ItemStack stack, Direction direction) {
		WoodenStakeProjectile projectile = new WoodenStakeProjectile(level, position.x(), position.y(), position.z(), stack.copyWithCount(1), null);
		projectile.pickup = AbstractArrow.Pickup.ALLOWED;
		return projectile;
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (attacker instanceof Player player && !player.hasInfiniteMaterials()) {
			player.getCooldowns().addCooldown(this, NyctoHunterUtil.woodenStakeCooldown(player));
			stack.shrink(1);
		}
		return true;
	}
}
