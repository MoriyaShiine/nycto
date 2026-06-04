/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.registry;

import com.mojang.serialization.MapCodec;
import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.NyctoItems;
import moriyashiine.nycto.neoforge.entity.hunter.HunterEntity;
import moriyashiine.nycto.neoforge.entity.projectile.WoodenStakeProjectile;
import moriyashiine.nycto.neoforge.hunter.NyctoHunterUtil;
import moriyashiine.nycto.neoforge.item.AconiteArrowItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.common.BasicItemListing;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import javax.annotation.Nullable;

public final class NyctoHunterContent {
	private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NyctoNeoForge.MOD_ID);
	private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NyctoNeoForge.MOD_ID);
	private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, NyctoNeoForge.MOD_ID);
	private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, NyctoNeoForge.MOD_ID);

	private static final int HEAL_BLOCK_TICKS = 120;
	private static final int GARLIC_WREATH_AURA_RADIUS = 12;

	public static final DeferredHolder<MobEffect, MobEffect> VAMPIRE_WARD = MOB_EFFECTS.register("vampire_ward", () -> new VampireWardEffect(MobEffectCategory.HARMFUL, 0xBDEFA8));

	public static final DeferredBlock<Block> GARLIC = BLOCKS.register("garlic", () -> new GarlicCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CARROTS)));
	public static final DeferredBlock<Block> WILD_GARLIC = BLOCKS.register("wild_garlic", () -> new WildGarlicBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ALLIUM)));
	public static final DeferredBlock<Block> GARLIC_WREATH = BLOCKS.register("garlic_wreath", () -> new GarlicWreathBlock(BlockBehaviour.Properties.of().noCollission().strength(0.2F).sound(SoundType.GRASS)));
	public static final DeferredBlock<Block> ACONITE = BLOCKS.register("aconite", () -> new AconiteCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CARROTS)));
	public static final DeferredBlock<Block> WILD_ACONITE = BLOCKS.register("wild_aconite", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.ALLIUM)));
	public static final DeferredBlock<Block> ACONITE_GARLAND = BLOCKS.register("aconite_garland", () -> new AconiteGarlandBlock(BlockBehaviour.Properties.of().noCollission().strength(0.2F).sound(SoundType.GRASS)));

	public static final DeferredItem<BlockItem> GARLIC_ITEM = ITEMS.register("garlic", () -> new BlockItem(GARLIC.get(), new Item.Properties().food(garlicFood())));
	public static final DeferredItem<BlockItem> WILD_GARLIC_ITEM = ITEMS.register("wild_garlic", () -> new BlockItem(WILD_GARLIC.get(), new Item.Properties()));
	public static final DeferredItem<BlockItem> GARLIC_WREATH_ITEM = ITEMS.register("garlic_wreath", () -> new BlockItem(GARLIC_WREATH.get(), new Item.Properties()));
	public static final DeferredItem<BlockItem> ACONITE_SEEDS = ITEMS.register("aconite_seeds", () -> new BlockItem(ACONITE.get(), new Item.Properties()));
	public static final DeferredItem<BlockItem> ACONITE_ITEM = ITEMS.register("aconite", () -> new BlockItem(ACONITE.get(), new Item.Properties()));
	public static final DeferredItem<BlockItem> WILD_ACONITE_ITEM = ITEMS.register("wild_aconite", () -> new BlockItem(WILD_ACONITE.get(), new Item.Properties()));
	public static final DeferredItem<BlockItem> ACONITE_GARLAND_ITEM = ITEMS.register("aconite_garland", () -> new BlockItem(ACONITE_GARLAND.get(), new Item.Properties()));
	public static final DeferredItem<Item> GARLIC_BREW = ITEMS.register("garlic_brew", () -> new GarlicBrewItem(new Item.Properties().stacksTo(16)));
	public static final DeferredItem<Item> SPLASH_GARLIC_BREW = ITEMS.register("splash_garlic_brew", () -> new SplashGarlicBrewItem(new Item.Properties().stacksTo(16), 4, 220));
	public static final DeferredItem<Item> LINGERING_GARLIC_BREW = ITEMS.register("lingering_garlic_brew", () -> new SplashGarlicBrewItem(new Item.Properties().stacksTo(16), 6, 420));
	public static final DeferredItem<Item> HALBERD = ITEMS.register("halberd", () -> new AxeItem(Tiers.DIAMOND, new Item.Properties().durability(1561).attributes(halberdAttributes())));
	public static final DeferredItem<Item> GARLIC_COATED_HALBERD = ITEMS.register("garlic_coated_halberd", () -> new AxeItem(Tiers.DIAMOND, new Item.Properties().durability(1561).attributes(halberdAttributes())));
	public static final DeferredItem<Item> ACONITE_COATED_HALBERD = ITEMS.register("aconite_coated_halberd", () -> new AxeItem(Tiers.DIAMOND, new Item.Properties().durability(1561).attributes(halberdAttributes())));
	public static final DeferredItem<Item> ACONITE_ARROW = ITEMS.register("aconite_arrow", () -> new AconiteArrowItem(new Item.Properties()));
	public static final DeferredItem<Item> FIREBOMB = ITEMS.register("firebomb", () -> new moriyashiine.nycto.neoforge.item.FirebombItem(new Item.Properties().stacksTo(16)));
	public static final DeferredItem<Item> HUNTER_CONTRACT = ITEMS.register("hunter_contract", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final DeferredItem<Item> VAMPIRE_HUNTER_CONTRACT = ITEMS.register("vampire_hunter_contract", () -> new HunterContractItem(new Item.Properties().stacksTo(16)));
	public static final DeferredItem<Item> VAMPIRE_HUNTER_HELMET = ITEMS.register("vampire_hunter_helmet", () -> new ArmorItem(NyctoArmorMaterials.VAMPIRE_HUNTER, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(18))));
	public static final DeferredItem<Item> VAMPIRE_HUNTER_CHESTPLATE = ITEMS.register("vampire_hunter_chestplate", () -> new ArmorItem(NyctoArmorMaterials.VAMPIRE_HUNTER, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(18))));
	public static final DeferredItem<Item> VAMPIRE_HUNTER_LEGGINGS = ITEMS.register("vampire_hunter_leggings", () -> new ArmorItem(NyctoArmorMaterials.VAMPIRE_HUNTER, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(18))));
	public static final DeferredItem<Item> VAMPIRE_HUNTER_BOOTS = ITEMS.register("vampire_hunter_boots", () -> new ArmorItem(NyctoArmorMaterials.VAMPIRE_HUNTER, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(18))));

	public static final DeferredHolder<EntityType<?>, EntityType<HunterEntity>> HUNTER = ENTITY_TYPES.register("hunter", () -> EntityType.Builder.of(HunterEntity::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8).build(ResourceLocation.fromNamespaceAndPath(NyctoNeoForge.MOD_ID, "hunter").toString()));
	public static final DeferredItem<Item> HUNTER_SPAWN_EGG = ITEMS.register("hunter_spawn_egg", () -> new DeferredSpawnEggItem(HUNTER, 0x3F362F, 0xD7B46A, new Item.Properties()));

	private NyctoHunterContent() {
	}

	public static void register(IEventBus bus) {
		BLOCKS.register(bus);
		ITEMS.register(bus);
		MOB_EFFECTS.register(bus);
		ENTITY_TYPES.register(bus);
	}

	public static void registerEvents() {
		NeoForge.EVENT_BUS.addListener(NyctoHunterContent::onLivingDamage);
		NeoForge.EVENT_BUS.addListener(NyctoHunterContent::onLivingHeal);
		NeoForge.EVENT_BUS.addListener(NyctoHunterContent::onMobEffectAdded);
		NeoForge.EVENT_BUS.addListener(NyctoHunterContent::onEntityTick);
		NeoForge.EVENT_BUS.addListener(NyctoHunterContent::onEntityJoinLevel);
		NeoForge.EVENT_BUS.addListener(NyctoHunterContent::onVillagerTrades);
	}

	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(HUNTER.get(), HunterEntity.createAttributes().build());
	}

	public static void addCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
			event.accept(GARLIC_ITEM.get());
			event.accept(GARLIC_BREW.get());
			event.accept(SPLASH_GARLIC_BREW.get());
			event.accept(LINGERING_GARLIC_BREW.get());
		}
		if (event.getTabKey() == CreativeModeTabs.COMBAT) {
			event.accept(NyctoItems.WOODEN_STAKE.get());
			event.accept(HALBERD.get());
			event.accept(GARLIC_COATED_HALBERD.get());
			event.accept(ACONITE_COATED_HALBERD.get());
			event.accept(ACONITE_ARROW.get());
			event.accept(FIREBOMB.get());
			event.accept(HUNTER_CONTRACT.get());
			event.accept(VAMPIRE_HUNTER_CONTRACT.get());
			event.accept(VAMPIRE_HUNTER_HELMET.get());
			event.accept(VAMPIRE_HUNTER_CHESTPLATE.get());
			event.accept(VAMPIRE_HUNTER_LEGGINGS.get());
			event.accept(VAMPIRE_HUNTER_BOOTS.get());
		}
		if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
			event.accept(HUNTER_SPAWN_EGG.get());
		}
		if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
			event.accept(WILD_GARLIC_ITEM.get());
			event.accept(GARLIC_WREATH_ITEM.get());
			event.accept(WILD_ACONITE_ITEM.get());
			event.accept(ACONITE_GARLAND_ITEM.get());
		}
		if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
			event.accept(ACONITE_SEEDS.get());
			event.accept(ACONITE_ITEM.get());
		}
	}

	private static void onLivingDamage(LivingDamageEvent.Pre event) {
		LivingEntity victim = event.getEntity();
		Entity attacker = event.getSource().getEntity();
		boolean woodenStakeProjectile = event.getSource().getDirectEntity() instanceof WoodenStakeProjectile;
		if (NyctoHunterUtil.isVampire(victim) && (attacker instanceof LivingEntity || woodenStakeProjectile)) {
			LivingEntity livingAttacker = attacker instanceof LivingEntity entity ? entity : null;
			ItemStack weapon = woodenStakeProjectile ? NyctoItems.WOODEN_STAKE.get().getDefaultInstance() : livingAttacker.getMainHandItem();
			float bonus = getWeaponBonus(weapon);
			if (bonus > 0) {
				int armorCount = livingAttacker == null ? 0 : NyctoHunterUtil.getVampireHunterArmorCount(livingAttacker);
				event.setNewDamage(event.getNewDamage() + bonus + armorCount * 0.75F);
				applyVampireSuppression(victim, HEAL_BLOCK_TICKS);
				if (!woodenStakeProjectile) {
					weapon.hurtAndBreak(1, livingAttacker, EquipmentSlot.MAINHAND);
				}
			}
		}
		if (NyctoHunterUtil.isVampire(attacker) && victim instanceof LivingEntity livingVictim) {
			int armorCount = NyctoHunterUtil.getVampireHunterArmorCount(livingVictim);
			if (armorCount > 0) {
				event.setNewDamage(event.getNewDamage() * Math.max(0.35F, 1 - armorCount * 0.16F));
			}
			if (NyctoHunterUtil.blocksVampireCriticals(livingVictim) && attacker instanceof LivingEntity livingAttacker && isCriticalLikeAttack(livingAttacker)) {
				event.setNewDamage(event.getNewDamage() / 1.5F);
			}
		}
	}

	private static ItemAttributeModifiers halberdAttributes() {
		return AxeItem.createAttributes(Tiers.DIAMOND, 5.0F, -3.2F)
				.withModifierAdded(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(NyctoNeoForge.id("halberd_entity_interaction_range"), 0.5, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
	}

	private static void onLivingHeal(LivingHealEvent event) {
		if (NyctoHunterUtil.isVampire(event.getEntity()) && event.getEntity().hasEffect(VAMPIRE_WARD)) {
			event.setCanceled(true);
		}
	}

	private static void onMobEffectAdded(MobEffectEvent.Added event) {
		MobEffectInstance effect = event.getEffectInstance();
		if (effect.getEffect().is(VAMPIRE_WARD) && NyctoHunterUtil.isVampire(event.getEntity())) {
			applyVampireWardConsequences(event.getEntity(), Math.max(HEAL_BLOCK_TICKS, effect.getDuration()), effect.getAmplifier());
		}
	}

	private static void onEntityTick(EntityTickEvent.Post event) {
		Entity entity = event.getEntity();
		if (entity.level().isClientSide() || entity.tickCount % 40 != 0) {
			return;
		}
		if (NyctoHunterUtil.isLivingVampire(entity)) {
			BlockPos origin = entity.blockPosition();
			for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-GARLIC_WREATH_AURA_RADIUS, -4, -GARLIC_WREATH_AURA_RADIUS), origin.offset(GARLIC_WREATH_AURA_RADIUS, 4, GARLIC_WREATH_AURA_RADIUS))) {
				if (pos.closerToCenterThan(entity.position(), GARLIC_WREATH_AURA_RADIUS) && entity.level().getBlockState(pos).is(GARLIC_WREATH.get())) {
					applyVampireSuppression((LivingEntity) entity, 120);
					break;
				}
			}
			return;
		}
		if (entity instanceof LivingEntity living && NyctoHunterUtil.hasGarlicAura(living)) {
			AABB aura = living.getBoundingBox().inflate(5);
			for (LivingEntity vampire : living.level().getEntitiesOfClass(LivingEntity.class, aura, NyctoHunterUtil::isVampire)) {
				applyVampireSuppression(vampire, 120);
			}
		}
	}

	private static void onVillagerTrades(VillagerTradesEvent event) {
		if (event.getType() == VillagerProfession.CLERIC) {
			event.getTrades().get(5).add(new BasicItemListing(8, new ItemStack(HUNTER_CONTRACT.get()), 6, 30, 0.05F));
		}
	}

	private static void onEntityJoinLevel(EntityJoinLevelEvent event) {
		if (event.getLevel().isClientSide() || !(event.getEntity() instanceof WoodenStakeProjectile projectile) || !(projectile.getOwner() instanceof Player player) || player.hasInfiniteMaterials()) {
			return;
		}
		int cooldown = NyctoHunterUtil.woodenStakeCooldown(player);
		player.getCooldowns().addCooldown(Items.CROSSBOW, cooldown);
		player.getCooldowns().addCooldown(NyctoItems.WOODEN_STAKE.get(), cooldown);
	}

	private static float getWeaponBonus(ItemStack weapon) {
		if (weapon.is(NyctoItems.WOODEN_STAKE.get())) {
			return 3;
		}
		if (weapon.is(GARLIC_COATED_HALBERD.get())) {
			return 7;
		}
		if (weapon.is(HALBERD.get())) {
			return 3;
		}
		return 0;
	}

	private static void applyVampireSuppression(LivingEntity entity, int duration) {
		entity.addEffect(new MobEffectInstance(VAMPIRE_WARD, duration, 0));
		applyVampireWardConsequences(entity, duration, 0);
	}

	private static void applyVampireWardConsequences(LivingEntity entity, int duration, int amplifier) {
		if (!NyctoHunterUtil.isVampire(entity)) {
			return;
		}
		moriyashiine.nycto.neoforge.NyctoData.applyHealBlock(entity, duration);
		entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, duration, Math.max(1, amplifier + 1)));
		entity.addEffect(new MobEffectInstance(MobEffects.POISON, Math.min(duration, 100), 0));
		if (entity.level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(), 4, entity.getBbWidth() * 0.35, entity.getBbHeight() * 0.2, entity.getBbWidth() * 0.35, 0.02);
		}
	}

	private static void applyGarlicContact(Level level, Entity entity) {
		if (level.isClientSide() || !(entity instanceof LivingEntity living) || living.hurtTime > 0 || !NyctoHunterUtil.isVampire(living)) {
			return;
		}
		living.hurt(level.damageSources().magic(), 1);
		applyVampireSuppression(living, 80);
	}

	private static boolean isCriticalLikeAttack(LivingEntity attacker) {
		return attacker.fallDistance > 0.0F && !attacker.onGround() && !attacker.isInWater() && !attacker.isPassenger();
	}

	private static FoodProperties garlicFood() {
		return new FoodProperties.Builder().nutrition(2).saturationModifier(0.2F).build();
	}

	private static final class VampireWardEffect extends MobEffect {
		private VampireWardEffect(MobEffectCategory category, int color) {
			super(category, color);
		}
	}

	private static final class WildGarlicBlock extends Block {
		private WildGarlicBlock(Properties properties) {
			super(properties);
		}

		@Override
		protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
			applyGarlicContact(level, entity);
		}
	}

	private static final class GarlicCropBlock extends CropBlock {
		private static final MapCodec<GarlicCropBlock> CODEC = simpleCodec(GarlicCropBlock::new);
		private static final IntegerProperty AGE = BlockStateProperties.AGE_3;
		private static final VoxelShape[] SHAPE_BY_AGE = {
				Block.box(2, 0, 2, 14, 3, 14),
				Block.box(2, 0, 2, 14, 5, 14),
				Block.box(2, 0, 2, 14, 7, 14),
				Block.box(1, 0, 1, 15, 9, 15)
		};

		private GarlicCropBlock(Properties properties) {
			super(properties);
		}

		@Override
		public MapCodec<GarlicCropBlock> codec() {
			return CODEC;
		}

		@Override
		protected IntegerProperty getAgeProperty() {
			return AGE;
		}

		@Override
		public int getMaxAge() {
			return 3;
		}

		@Override
		protected ItemLike getBaseSeedId() {
			return GARLIC_ITEM.get();
		}

		@Override
		protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
			builder.add(AGE);
		}

		@Override
		protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
			return SHAPE_BY_AGE[getAge(state)];
		}

		@Override
		protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
			applyGarlicContact(level, entity);
		}
	}

	private static final class AconiteCropBlock extends CropBlock {
		private static final MapCodec<AconiteCropBlock> CODEC = simpleCodec(AconiteCropBlock::new);
		private static final IntegerProperty AGE = BlockStateProperties.AGE_3;
		private static final VoxelShape[] SHAPE_BY_AGE = {
				Block.box(2, 0, 2, 14, 3, 14),
				Block.box(2, 0, 2, 14, 5, 14),
				Block.box(2, 0, 2, 14, 7, 14),
				Block.box(1, 0, 1, 15, 9, 15)
		};

		private AconiteCropBlock(Properties properties) {
			super(properties);
		}

		@Override
		public MapCodec<AconiteCropBlock> codec() {
			return CODEC;
		}

		@Override
		protected IntegerProperty getAgeProperty() {
			return AGE;
		}

		@Override
		public int getMaxAge() {
			return 3;
		}

		@Override
		protected ItemLike getBaseSeedId() {
			return ACONITE_SEEDS.get();
		}

		@Override
		protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
			builder.add(AGE);
		}

		@Override
		protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
			return SHAPE_BY_AGE[getAge(state)];
		}
	}

	private static final class GarlicWreathBlock extends Block {
		private static final MapCodec<GarlicWreathBlock> CODEC = simpleCodec(GarlicWreathBlock::new);
		private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
		private static final BooleanProperty DOWN = BlockStateProperties.DOWN;
		private static final VoxelShape BOTTOM_SHAPE = Block.box(0, 0, 0, 16, 3, 16);
		private static final VoxelShape[] WALL_SHAPES = {
				Block.box(0, 0, 13, 16, 16, 16),
				Block.box(0, 0, 0, 16, 16, 3),
				Block.box(13, 0, 0, 16, 16, 16),
				Block.box(0, 0, 0, 3, 16, 16)
		};

		private GarlicWreathBlock(Properties properties) {
			super(properties);
			registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(DOWN, false));
		}

		@Override
		public MapCodec<GarlicWreathBlock> codec() {
			return CODEC;
		}

		@Nullable
		@Override
		public BlockState getStateForPlacement(BlockPlaceContext context) {
			Direction clickedFace = context.getClickedFace();
			if (clickedFace == Direction.DOWN) {
				return null;
			}
			boolean down = clickedFace == Direction.UP;
			BlockState state = defaultBlockState()
					.setValue(FACING, down ? context.getHorizontalDirection().getOpposite() : Direction.from2DDataValue(clickedFace.get2DDataValue()))
					.setValue(DOWN, down);
			return state.canSurvive(context.getLevel(), context.getClickedPos()) ? state : null;
		}

		@Override
		protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
			if (state.getValue(DOWN)) {
				BlockPos supportPos = pos.below();
				return level.getBlockState(supportPos).isFaceSturdy(level, supportPos, Direction.UP);
			}
			Direction supportDirection = state.getValue(FACING).getOpposite();
			BlockPos supportPos = pos.relative(supportDirection);
			return level.getBlockState(supportPos).isFaceSturdy(level, supportPos, supportDirection);
		}

		@Override
		protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
			if (direction == Direction.DOWN || direction.getOpposite() == state.getValue(FACING)) {
				if (!state.canSurvive(level, pos)) {
					return Blocks.AIR.defaultBlockState();
				}
			}
			return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
		}

		@Override
		protected BlockState rotate(BlockState state, Rotation rotation) {
			return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
		}

		@Override
		protected BlockState mirror(BlockState state, Mirror mirror) {
			return state.rotate(mirror.getRotation(state.getValue(FACING)));
		}

		@Override
		protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
			builder.add(FACING, DOWN);
		}

		@Override
		protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
			if (state.getValue(DOWN)) {
				return BOTTOM_SHAPE;
			}
			return WALL_SHAPES[state.getValue(FACING).get3DDataValue() - 2];
		}

		@Override
		protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
			if (!level.isClientSide() && NyctoHunterUtil.isLivingVampire(entity)) {
				applyVampireSuppression((LivingEntity) entity, 80);
			}
		}
	}

	private static final class AconiteGarlandBlock extends Block {
		private static final MapCodec<AconiteGarlandBlock> CODEC = simpleCodec(AconiteGarlandBlock::new);
		private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
		private static final BooleanProperty HANGING = BlockStateProperties.HANGING;
		private static final VoxelShape[] WALL_SHAPES = {
				Block.box(0, 0, 13, 16, 16, 16),
				Block.box(0, 0, 0, 16, 16, 3),
				Block.box(13, 0, 0, 16, 16, 16),
				Block.box(0, 0, 0, 3, 16, 16)
		};
		private static final VoxelShape[] HANGING_SHAPES = {
				Block.box(0, 1, 6, 16, 16, 10),
				Block.box(6, 1, 0, 10, 16, 16)
		};

		private AconiteGarlandBlock(Properties properties) {
			super(properties);
			registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HANGING, false));
		}

		@Override
		public MapCodec<AconiteGarlandBlock> codec() {
			return CODEC;
		}

		@Nullable
		@Override
		public BlockState getStateForPlacement(BlockPlaceContext context) {
			Direction clickedFace = context.getClickedFace();
			if (clickedFace == Direction.UP) {
				return null;
			}
			boolean hanging = clickedFace == Direction.DOWN;
			BlockState state = defaultBlockState()
					.setValue(FACING, hanging ? context.getHorizontalDirection().getOpposite() : Direction.from2DDataValue(clickedFace.get2DDataValue()))
					.setValue(HANGING, hanging);
			return state.canSurvive(context.getLevel(), context.getClickedPos()) ? state : null;
		}

		@Override
		protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
			if (state.getValue(HANGING)) {
				BlockPos supportPos = pos.above();
				return level.getBlockState(supportPos).isFaceSturdy(level, supportPos, Direction.DOWN);
			}
			Direction supportDirection = state.getValue(FACING).getOpposite();
			BlockPos supportPos = pos.relative(supportDirection);
			return level.getBlockState(supportPos).isFaceSturdy(level, supportPos, supportDirection);
		}

		@Override
		protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
			if (direction == Direction.UP || direction.getOpposite() == state.getValue(FACING)) {
				if (!state.canSurvive(level, pos)) {
					return Blocks.AIR.defaultBlockState();
				}
			}
			return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
		}

		@Override
		protected BlockState rotate(BlockState state, Rotation rotation) {
			return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
		}

		@Override
		protected BlockState mirror(BlockState state, Mirror mirror) {
			return state.rotate(mirror.getRotation(state.getValue(FACING)));
		}

		@Override
		protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
			builder.add(FACING, HANGING);
		}

		@Override
		protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
			if (state.getValue(HANGING)) {
				return HANGING_SHAPES[state.getValue(FACING).getAxis() == Direction.Axis.Z ? 0 : 1];
			}
			return WALL_SHAPES[state.getValue(FACING).get3DDataValue() - 2];
		}
	}

	private static class GarlicBrewItem extends Item {
		private GarlicBrewItem(Properties properties) {
			super(properties);
		}

		@Override
		public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
			ItemStack stack = player.getItemInHand(hand);
			if (!level.isClientSide()) {
				if (NyctoHunterUtil.isVampire(player)) {
					applyVampireSuppression(player, 220);
				}
				stack.consume(1, player);
				if (!player.getAbilities().instabuild) {
					player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
				}
			}
			return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
		}
	}

	private static final class SplashGarlicBrewItem extends Item {
		private final int radius;
		private final int duration;

		private SplashGarlicBrewItem(Properties properties, int radius, int duration) {
			super(properties);
			this.radius = radius;
			this.duration = duration;
		}

		@Override
		public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
			ItemStack stack = player.getItemInHand(hand);
			BlockHitResult hit = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
			Vec3 center = hit.getType() == HitResult.Type.BLOCK ? hit.getLocation() : player.position().add(player.getLookAngle().scale(4));
			level.playSound(null, center.x, center.y, center.z, SoundEvents.SPLASH_POTION_BREAK, SoundSource.PLAYERS, 1, 1);
			if (!level.isClientSide()) {
				applyGarlicArea(level, center, radius, duration);
				stack.consume(1, player);
			}
			return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
		}
	}

	private static final class HunterContractItem extends Item {
		private HunterContractItem(Properties properties) {
			super(properties);
		}

		@Override
		public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
			ItemStack stack = player.getItemInHand(hand);
			if (level instanceof ServerLevel serverLevel) {
				HunterEntity hunter = createContractHunter(serverLevel, player);
				if (hunter == null) {
					player.displayClientMessage(Component.translatable("message.nycto.hunter_contract.fail"), true);
				} else {
					serverLevel.addFreshEntity(hunter);
					stack.consume(1, player);
					player.displayClientMessage(Component.translatable("message.nycto.hunter_contract.succeed"), true);
				}
			}
			return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
		}
	}

	private static HunterEntity createContractHunter(ServerLevel level, Player player) {
		if (level.getDifficulty() == Difficulty.PEACEFUL) {
			return null;
		}
		for (int i = 0; i < 24; i++) {
			double angle = level.random.nextDouble() * Math.PI * 2;
			int distance = 24 + level.random.nextInt(25);
			int x = player.getBlockX() + (int) Math.round(Math.cos(angle) * distance);
			int z = player.getBlockZ() + (int) Math.round(Math.sin(angle) * distance);
			BlockPos surface = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos(x, player.getBlockY(), z));
			if (!level.hasChunkAt(surface) || !level.getWorldBorder().isWithinBounds(surface) || !level.getBlockState(surface).isAir() || !level.getBlockState(surface.below()).isSolid()) {
				continue;
			}
			HunterEntity hunter = HUNTER.get().create(level);
			if (hunter == null) {
				return null;
			}
			hunter.moveTo(surface.getX() + 0.5, surface.getY(), surface.getZ() + 0.5, level.random.nextFloat() * 360, 0);
			if (!level.noCollision(hunter)) {
				hunter.discard();
				continue;
			}
			hunter.finalizeSpawn(level, level.getCurrentDifficultyAt(surface), MobSpawnType.TRIGGERED, null);
			if (level.random.nextInt(8) == 0) {
				mountContractHorse(level, hunter);
			}
			if (NyctoHunterUtil.isVampire(player) && !player.isCreative() && !player.isSpectator()) {
				hunter.setUltimateTarget(player);
			} else {
				hunter.setContractPos(player.blockPosition());
			}
			return hunter;
		}
		return null;
	}

	private static void mountContractHorse(ServerLevel level, HunterEntity hunter) {
		Horse horse = EntityType.HORSE.create(level);
		if (horse == null) {
			return;
		}
		horse.moveTo(hunter.getX(), hunter.getY(), hunter.getZ(), hunter.getYRot(), 0);
		if (!level.noCollision(horse)) {
			horse.discard();
			return;
		}
		horse.finalizeSpawn(level, level.getCurrentDifficultyAt(hunter.blockPosition()), MobSpawnType.TRIGGERED, null);
		horse.setTamed(true);
		if (horse.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
			horse.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3375);
		}
		level.addFreshEntity(horse);
		hunter.equipMountedLoadout();
		hunter.startRiding(horse);
	}

	private static void applyGarlicArea(Level level, Vec3 center, int radius, int duration) {
		AABB box = new AABB(center, center).inflate(radius);
		for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, NyctoHunterUtil::isVampire)) {
			applyVampireSuppression(living, duration);
		}
	}

	public static void applyFirebombImpact(Level level, Vec3 center, @Nullable Entity owner) {
		AABB box = new AABB(center, center).inflate(3, 2, 3);
		for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, entity -> entity != owner && entity.isAlive() && !entity.isSpectator() && !entity.isInWaterOrRain())) {
			living.igniteForSeconds(8);
		}
	}

	private static void igniteArea(Level level, BlockPos center, int radius) {
		for (BlockPos pos : BlockPos.betweenClosed(center.offset(-radius, -1, -radius), center.offset(radius, 1, radius))) {
			if (level.random.nextInt(3) == 0 && level.getBlockState(pos).isAir() && level.getBlockState(pos.below()).isSolid()) {
				level.setBlock(pos, Blocks.FIRE.defaultBlockState(), 11);
			}
		}
	}
}
