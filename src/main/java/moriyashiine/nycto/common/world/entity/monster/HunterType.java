/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.monster;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModBannerPatterns;
import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.tag.ModItemTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BannerPatterns;

import java.util.ArrayList;
import java.util.List;

public record HunterType(Identifier texture, ResourceKey<EquipmentAsset> assetKey, TagKey<Item> armorTagKey) {
	public static final Codec<HunterType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Identifier.CODEC.fieldOf("texture").forGetter(HunterType::texture),
			ResourceKey.codec(EquipmentAssets.ROOT_ID).fieldOf("asset_key").forGetter(HunterType::assetKey),
			TagKey.codec(Registries.ITEM).fieldOf("armor_tag_key").forGetter(HunterType::armorTagKey)
	).apply(instance, HunterType::new));
	public static final StreamCodec<FriendlyByteBuf, HunterType> STREAM_CODEC = StreamCodec.composite(
			Identifier.STREAM_CODEC, HunterType::texture,
			ResourceKey.streamCodec(EquipmentAssets.ROOT_ID), HunterType::assetKey,
			TagKey.streamCodec(Registries.ITEM), HunterType::armorTagKey,
			HunterType::new
	);

	public static final List<HunterType> TYPES = new ArrayList<>();

	public static final HunterType VAMPIRE = create(new HunterType(
			Nycto.id("textures/entity/hunter/vampire_hunter.png"),
			ResourceKey.create(EquipmentAssets.ROOT_ID, Nycto.id("vampire_hunter")),
			ModItemTags.VAMPIRE_HUNTER_ARMOR));
	public static final HunterType WEREWOLF = create(new HunterType(
			Nycto.id("textures/entity/hunter/werewolf_hunter.png"),
			ResourceKey.create(EquipmentAssets.ROOT_ID, Nycto.id("werewolf_hunter")),
			ModItemTags.WEREWOLF_HUNTER_ARMOR));

	public static HunterType create(HunterType type) {
		TYPES.add(type);
		return type;
	}

	public boolean shouldTarget(LivingEntity entity) {
		if (this == VAMPIRE) {
			return NyctoAPI.isVampire(entity);
		} else if (this == WEREWOLF) {
			return NyctoAPI.isWerewolf(entity);
		}
		return false;
	}

	public void equipItems(Hunter hunter, boolean hasHorse) {
		if (hasHorse) {
			ItemStack shield = Items.SHIELD.getDefaultInstance();
			shield.set(DataComponents.BANNER_PATTERNS, new BannerPatternLayers.Builder()
					.add(hunter.registryAccess().lookupOrThrow(Registries.BANNER_PATTERN).getOrThrow(BannerPatterns.BASE), DyeColor.BLACK)
					.add(hunter.registryAccess().lookupOrThrow(Registries.BANNER_PATTERN).getOrThrow(ModBannerPatterns.HUNTERS_MARK), DyeColor.YELLOW)
					.build());
			hunter.setItemSlot(EquipmentSlot.OFFHAND, shield);
		}
		if (this == VAMPIRE) {
			hunter.setItemSlot(EquipmentSlot.HEAD, ModItems.VAMPIRE_HUNTER_HELMET.getDefaultInstance());
			hunter.setItemSlot(EquipmentSlot.CHEST, ModItems.VAMPIRE_HUNTER_CHESTPLATE.getDefaultInstance());
			hunter.setItemSlot(EquipmentSlot.LEGS, ModItems.VAMPIRE_HUNTER_LEGGINGS.getDefaultInstance());
			hunter.setItemSlot(EquipmentSlot.FEET, ModItems.VAMPIRE_HUNTER_BOOTS.getDefaultInstance());
			if (hasHorse) {
				hunter.setItemSlot(EquipmentSlot.MAINHAND, ModItems.GARLIC_COATED_HALBERD.getDefaultInstance());
			} else {
				hunter.setItemSlot(EquipmentSlot.MAINHAND, ModItems.WOODEN_STAKE.getDefaultInstance());
				hunter.setItemSlot(EquipmentSlot.OFFHAND, Items.CROSSBOW.getDefaultInstance());
			}
		} else if (this == WEREWOLF) {
			hunter.setItemSlot(EquipmentSlot.HEAD, ModItems.WEREWOLF_HUNTER_HELMET.getDefaultInstance());
			hunter.setItemSlot(EquipmentSlot.CHEST, ModItems.WEREWOLF_HUNTER_CHESTPLATE.getDefaultInstance());
			hunter.setItemSlot(EquipmentSlot.LEGS, ModItems.WEREWOLF_HUNTER_LEGGINGS.getDefaultInstance());
			hunter.setItemSlot(EquipmentSlot.FEET, ModItems.WEREWOLF_HUNTER_BOOTS.getDefaultInstance());
			if (hasHorse) {
				hunter.setItemSlot(EquipmentSlot.MAINHAND, ModItems.ACONITE_COATED_HALBERD.getDefaultInstance());
			} else {
				hunter.setItemSlot(EquipmentSlot.MAINHAND, Items.IRON_SWORD.getDefaultInstance());
				hunter.setItemSlot(EquipmentSlot.OFFHAND, Items.BOW.getDefaultInstance());
			}
		}
	}
}
