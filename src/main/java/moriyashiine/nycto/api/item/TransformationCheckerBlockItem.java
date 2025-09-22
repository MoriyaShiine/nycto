/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.api.item;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.util.function.Predicate;

public class TransformationCheckerBlockItem extends BlockItem {
	private final Predicate<LivingEntity> predicate;

	public TransformationCheckerBlockItem(Block block, Settings settings, Predicate<LivingEntity> predicate) {
		super(block, settings);
		this.predicate = predicate;
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		return TransformationCheckerItem.useOnEntity(stack, user, entity, predicate, () -> super.useOnEntity(stack, user, entity, hand));
	}
}
