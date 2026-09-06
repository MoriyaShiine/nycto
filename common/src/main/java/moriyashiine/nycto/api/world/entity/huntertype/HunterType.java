package moriyashiine.nycto.api.world.entity.huntertype;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.serialization.Codec;
import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.common.init.NyctoBannerPatterns;
import moriyashiine.nycto.common.world.entity.monster.Hunter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BannerPatterns;

public class HunterType {
	public static final Codec<HunterType> CODEC = NyctoRegistries.HUNTER_TYPE.byNameCodec();
	public static final StreamCodec<RegistryFriendlyByteBuf, HunterType> STREAM_CODEC = ByteBufCodecs.registry(NyctoRegistries.HUNTER_TYPE_KEY);

	public final Identifier hunterEntityTexture;
	public final ResourceKey<EquipmentAsset> assetKey;
	public final TagKey<Item> armorTagKey;

	public HunterType(Identifier hunterEntityTexture, ResourceKey<EquipmentAsset> assetKey, TagKey<Item> armorTagKey) {
		this.hunterEntityTexture = hunterEntityTexture;
		this.assetKey = assetKey;
		this.armorTagKey = armorTagKey;
	}

	public Multimap<Integer, Goal> getGoals(Hunter hunter) {
		return HashMultimap.create();
	}

	public boolean shouldTarget(LivingEntity entity) {
		return false;
	}

	public void equipItems(Hunter hunter, boolean hasHorse) {
		if (hasHorse) {
			ItemStack shield = Items.SHIELD.getDefaultInstance();
			shield.set(DataComponents.BANNER_PATTERNS, new BannerPatternLayers.Builder()
					.add(hunter.registryAccess().lookupOrThrow(Registries.BANNER_PATTERN).getOrThrow(BannerPatterns.BASE), DyeColor.BLACK)
					.add(hunter.registryAccess().lookupOrThrow(Registries.BANNER_PATTERN).getOrThrow(NyctoBannerPatterns.HUNTERS_MARK), DyeColor.YELLOW)
					.build());
			hunter.setItemSlot(EquipmentSlot.OFFHAND, shield);
		}
	}

	public boolean hasMeleeAttack() {
		return true;
	}

	public void performNonCrossbowRangedAttack(Hunter hunter, LivingEntity target, float power) {
	}

	public boolean shouldUseCustomItem(Hunter hunter) {
		return false;
	}

	public void useCustomItem(Hunter hunter) {
	}

	public int getCustomItemCooldown(Hunter hunter) {
		return 0;
	}
}
