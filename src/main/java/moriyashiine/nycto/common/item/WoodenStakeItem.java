/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.item;

import moriyashiine.nycto.common.entity.projectile.WoodenStakeEntity;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WoodenStakeItem extends ArrowItem {
	public static final int DAMAGE = 3;
	private static final int COOLDOWN = 100;

	public WoodenStakeItem(Item.Settings settings) {
		super(settings.attributeModifiers(ToolMaterial.WOOD.createToolAttributeModifiers(DAMAGE, -3.6F)));
		DispenserBlock.registerProjectileBehavior(this);
	}

	@Override
	public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter, @Nullable ItemStack shotFrom) {
		return new WoodenStakeEntity(world, shooter, stack, shotFrom);
	}

	@Override
	public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
		WoodenStakeEntity woodenStakeEntity = new WoodenStakeEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack.copyWithCount(1), null);
		woodenStakeEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
		return woodenStakeEntity;
	}

	@Override
	public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (attacker instanceof PlayerEntity player) {
			if (player.isCreative()) {
				return;
			}
			player.getItemCooldownManager().set(stack, getCooldown(attacker));
			for (Item item : Registries.ITEM) {
				if (item.getDefaultStack().isIn(ItemTags.CROSSBOW_ENCHANTABLE)) {
					player.getItemCooldownManager().set(item.getDefaultStack(), getCrossbowCooldown(attacker));
				}
			}
			attacker.sendEquipmentBreakStatus(this, EquipmentSlot.MAINHAND);
			stack.decrement(1);
		}
	}

	public static int getCooldown(LivingEntity entity) {
		return COOLDOWN / (NyctoUtil.hasReducedWoodenStakeCooldown(entity) ? 2 : 1);
	}

	public static int getCrossbowCooldown(LivingEntity entity) {
		return COOLDOWN / (NyctoUtil.hasReducedWoodenStakeCooldown(entity) ? 2 : 1);
	}
}
