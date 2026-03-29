/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.api.world.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.function.Predicate;

public class TransformationCheckerBlockItem extends BlockItem {
	private final Predicate<LivingEntity> predicate;

	public TransformationCheckerBlockItem(Block block, Properties properties, Predicate<LivingEntity> predicate) {
		super(block, properties);
		this.predicate = predicate;
	}

	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
		return TransformationCheckerItem.useOnEntity(stack, player, target, predicate, () -> super.interactLivingEntity(stack, player, target, hand));
	}
}
