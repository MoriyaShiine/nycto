/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.item.WoodenStakeItem;
import moriyashiine.nycto.neoforge.item.VampireArmorItem;
import moriyashiine.nycto.neoforge.item.VampiricDaggerItem;
import moriyashiine.nycto.neoforge.registry.NyctoBlocks;
import moriyashiine.nycto.neoforge.registry.NyctoEntityTypes;
import moriyashiine.nycto.neoforge.registry.NyctoMobEffects;
import moriyashiine.nycto.neoforge.registry.NyctoSoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BedItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NyctoItems {
	private static final int BLOOD_BOTTLE_FILL = 10;

	private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NyctoNeoForge.MOD_ID);

	public static final DeferredItem<Item> VAMPIRE_BLOOD_BOTTLE = ITEMS.register("vampire_blood_bottle", () -> new VampireBloodBottleItem(new Item.Properties().stacksTo(16)));
	public static final DeferredItem<Item> BLOOD_BOTTLE = ITEMS.register("blood_bottle", () -> new BloodBottleItem(new Item.Properties().stacksTo(16)));
	public static final DeferredItem<Item> VAMPIRE_SPAWN_EGG = ITEMS.register("vampire_spawn_egg", () -> new DeferredSpawnEggItem(NyctoEntityTypes.VAMPIRE, 0x332438, 0xB3131D, new Item.Properties()));
	public static final DeferredItem<Item> WOODEN_STAKE = ITEMS.register("wooden_stake", () -> new WoodenStakeItem(new Item.Properties().stacksTo(16)));
	public static final DeferredItem<VampiricDaggerItem> VAMPIRIC_DAGGER = ITEMS.registerItem("vampiric_dagger", VampiricDaggerItem::new, new Item.Properties());

	public static final DeferredItem<VampireArmorItem> VAMPIRE_HELMET = ITEMS.register("vampire_helmet", () -> new VampireArmorItem(ArmorItem.Type.HELMET, new Item.Properties()));
	public static final DeferredItem<VampireArmorItem> VAMPIRE_CHESTPLATE = ITEMS.register("vampire_chestplate", () -> new VampireArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final DeferredItem<VampireArmorItem> VAMPIRE_LEGGINGS = ITEMS.register("vampire_leggings", () -> new VampireArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final DeferredItem<VampireArmorItem> VAMPIRE_BOOTS = ITEMS.register("vampire_boots", () -> new VampireArmorItem(ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final DeferredItem<BlockItem> VAMPIRE_ALTAR = ITEMS.registerSimpleBlockItem("vampire_altar", NyctoBlocks.VAMPIRE_ALTAR);
	public static final DeferredItem<BlockItem> BLOOD_FOUNTAIN = ITEMS.registerSimpleBlockItem("blood_fountain", NyctoBlocks.BLOOD_FOUNTAIN);
	public static final DeferredItem<BedItem> OAK_COFFIN = ITEMS.register("oak_coffin", () -> new BedItem(NyctoBlocks.OAK_COFFIN.get(), new Item.Properties()));
	public static final DeferredItem<BedItem> SPRUCE_COFFIN = ITEMS.register("spruce_coffin", () -> new BedItem(NyctoBlocks.SPRUCE_COFFIN.get(), new Item.Properties()));
	public static final DeferredItem<BedItem> BIRCH_COFFIN = ITEMS.register("birch_coffin", () -> new BedItem(NyctoBlocks.BIRCH_COFFIN.get(), new Item.Properties()));
	public static final DeferredItem<BedItem> JUNGLE_COFFIN = ITEMS.register("jungle_coffin", () -> new BedItem(NyctoBlocks.JUNGLE_COFFIN.get(), new Item.Properties()));
	public static final DeferredItem<BedItem> ACACIA_COFFIN = ITEMS.register("acacia_coffin", () -> new BedItem(NyctoBlocks.ACACIA_COFFIN.get(), new Item.Properties()));
	public static final DeferredItem<BedItem> DARK_OAK_COFFIN = ITEMS.register("dark_oak_coffin", () -> new BedItem(NyctoBlocks.DARK_OAK_COFFIN.get(), new Item.Properties()));
	public static final DeferredItem<BedItem> PALE_OAK_COFFIN = ITEMS.register("pale_oak_coffin", () -> new BedItem(NyctoBlocks.PALE_OAK_COFFIN.get(), new Item.Properties()));
	public static final DeferredItem<BedItem> MANGROVE_COFFIN = ITEMS.register("mangrove_coffin", () -> new BedItem(NyctoBlocks.MANGROVE_COFFIN.get(), new Item.Properties()));
	public static final DeferredItem<BedItem> CHERRY_COFFIN = ITEMS.register("cherry_coffin", () -> new BedItem(NyctoBlocks.CHERRY_COFFIN.get(), new Item.Properties()));
	public static final DeferredItem<BedItem> BAMBOO_COFFIN = ITEMS.register("bamboo_coffin", () -> new BedItem(NyctoBlocks.BAMBOO_COFFIN.get(), new Item.Properties()));
	public static final DeferredItem<BedItem> CRIMSON_COFFIN = ITEMS.register("crimson_coffin", () -> new BedItem(NyctoBlocks.CRIMSON_COFFIN.get(), new Item.Properties()));
	public static final DeferredItem<BedItem> WARPED_COFFIN = ITEMS.register("warped_coffin", () -> new BedItem(NyctoBlocks.WARPED_COFFIN.get(), new Item.Properties()));

	private NyctoItems() {
	}

	public static void register(IEventBus bus) {
		ITEMS.register(bus);
	}

	public static void addCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
			event.accept(VAMPIRE_BLOOD_BOTTLE.get());
			event.accept(BLOOD_BOTTLE.get());
			event.accept(WOODEN_STAKE.get());
		}
		if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
			event.accept(VAMPIRE_SPAWN_EGG.get());
		}
		if (event.getTabKey() == CreativeModeTabs.COMBAT) {
			event.accept(VAMPIRIC_DAGGER.get());
			event.accept(VAMPIRE_HELMET.get());
			event.accept(VAMPIRE_CHESTPLATE.get());
			event.accept(VAMPIRE_LEGGINGS.get());
			event.accept(VAMPIRE_BOOTS.get());
		}
		if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
			event.accept(VAMPIRE_ALTAR.get());
			event.accept(BLOOD_FOUNTAIN.get());
			event.accept(OAK_COFFIN.get());
			event.accept(SPRUCE_COFFIN.get());
			event.accept(BIRCH_COFFIN.get());
			event.accept(JUNGLE_COFFIN.get());
			event.accept(ACACIA_COFFIN.get());
			event.accept(DARK_OAK_COFFIN.get());
			event.accept(PALE_OAK_COFFIN.get());
			event.accept(MANGROVE_COFFIN.get());
			event.accept(CHERRY_COFFIN.get());
			event.accept(BAMBOO_COFFIN.get());
			event.accept(CRIMSON_COFFIN.get());
			event.accept(WARPED_COFFIN.get());
		}
	}

	public static ResourceLocation id(String path) {
		return NyctoNeoForge.id(path);
	}

	private static final class VampireBloodBottleItem extends Item {
		private VampireBloodBottleItem(Properties properties) {
			super(properties);
		}

		@Override
		public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
			ItemStack stack = player.getItemInHand(hand);
			player.startUsingItem(hand);
			return InteractionResultHolder.consume(stack);
		}

		@Override
		public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
			if (entity instanceof Player player && !level.isClientSide()) {
				level.playSound(null, player.getX(), player.getY(), player.getZ(), NyctoSoundEvents.BLOOD_BOTTLE_DRINK.get(), SoundSource.PLAYERS, 1, 1);
				if (NyctoData.isVampire(player)) {
					NyctoData.addBlood(player, BLOOD_BOTTLE_FILL);
					player.sendSystemMessage(Component.translatable("message.nycto.blood_bottle"));
				} else {
					player.addEffect(new MobEffectInstance(NyctoMobEffects.VAMPIRISM, 20 * 30, 0, false, true, true));
					player.sendSystemMessage(Component.translatable("message.nycto.vampire_blood_bottle"));
				}
			}
			return finishBottle(stack, entity);
		}

		@Override
		public UseAnim getUseAnimation(ItemStack stack) {
			return UseAnim.DRINK;
		}

		@Override
		public int getUseDuration(ItemStack stack, LivingEntity entity) {
			return 40;
		}
	}

	private static final class BloodBottleItem extends Item {
		private BloodBottleItem(Properties properties) {
			super(properties);
		}

		@Override
		public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
			ItemStack stack = player.getItemInHand(hand);
			player.startUsingItem(hand);
			return InteractionResultHolder.consume(stack);
		}

		@Override
		public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
			if (entity instanceof Player player && !level.isClientSide()) {
				level.playSound(null, player.getX(), player.getY(), player.getZ(), NyctoSoundEvents.BLOOD_BOTTLE_DRINK.get(), SoundSource.PLAYERS, 1, 1);
				if (NyctoData.isVampire(player)) {
					NyctoData.addBlood(player, BLOOD_BOTTLE_FILL);
					player.sendSystemMessage(Component.translatable("message.nycto.blood_bottle"));
				} else {
					player.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0));
					player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, 0));
					player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
					player.sendSystemMessage(Component.translatable("message.nycto.blood_bottle_human"));
				}
			}
			return finishBottle(stack, entity);
		}

		@Override
		public UseAnim getUseAnimation(ItemStack stack) {
			return UseAnim.DRINK;
		}

		@Override
		public int getUseDuration(ItemStack stack, LivingEntity entity) {
			return 40;
		}
	}

	private static ItemStack finishBottle(ItemStack stack, LivingEntity entity) {
		if (entity instanceof Player player && player.getAbilities().instabuild) {
			return stack;
		}
		ItemStack remainder = new ItemStack(Items.GLASS_BOTTLE);
		stack.shrink(1);
		if (stack.isEmpty()) {
			return remainder;
		}
		if (entity instanceof Player player && !player.getInventory().add(remainder)) {
			player.drop(remainder, false);
		}
		return stack;
	}
}
