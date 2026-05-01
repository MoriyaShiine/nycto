/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.api.world.entity.huntertype;

import com.mojang.serialization.Codec;
import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.common.init.ModBannerPatterns;
import moriyashiine.nycto.common.world.entity.monster.Hunter;
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
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BannerPatterns;

public class HunterType {
	public static final Codec<HunterType> CODEC = NyctoRegistries.HUNTER_TYPE.byNameCodec();
	public static final StreamCodec<FriendlyByteBuf, HunterType> STREAM_CODEC = StreamCodec.composite(
			Identifier.STREAM_CODEC, NyctoRegistries.HUNTER_TYPE::getKey,
			identifier -> NyctoRegistries.HUNTER_TYPE.get(identifier).orElseThrow().value()
	);

	public final Identifier hunterEntityTexture;
	public final ResourceKey<EquipmentAsset> assetKey;
	public final TagKey<Item> armorTagKey;

	public HunterType(Identifier hunterEntityTexture, ResourceKey<EquipmentAsset> assetKey, TagKey<Item> armorTagKey) {
		this.hunterEntityTexture = hunterEntityTexture;
		this.assetKey = assetKey;
		this.armorTagKey = armorTagKey;
	}

	public boolean shouldTarget(LivingEntity entity) {
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
	}
}
