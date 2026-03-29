/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.world.power.Power;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.advancements.criterion.PlayerAppliesEffectsTrigger;
import moriyashiine.nycto.common.init.*;
import moriyashiine.nycto.common.tag.ModBlockTags;
import moriyashiine.nycto.common.tag.ModPowerTags;
import moriyashiine.nycto.common.world.entity.subpredicate.VampirePredicate;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.*;
import net.minecraft.advancements.criterion.*;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.predicates.DataComponentPredicates;
import net.minecraft.core.component.predicates.PotionsPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.TimeCheck;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends FabricAdvancementProvider {
	public ModAdvancementProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void generateAdvancement(HolderLookup.Provider registries, Consumer<AdvancementHolder> consumer) {
		HolderGetter<Block> blockGetter = registries.lookupOrThrow(Registries.BLOCK);
		HolderGetter<Item> itemGetter = registries.lookupOrThrow(Registries.ITEM);
		HolderGetter<EntityType<?>> entityTypeGetter = registries.lookupOrThrow(Registries.ENTITY_TYPE);
		HolderGetter<WorldClock> worldClockGetter = registries.lookupOrThrow(Registries.WORLD_CLOCK);
		HolderGetter<Power> powerGetter = registries.lookupOrThrow(NyctoRegistries.POWER_KEY);

		Optional<ContextAwarePredicate> vampirePredicate = Optional.of(ContextAwarePredicate.create(
				LootItemEntityPropertyCondition.hasProperties(
						LootContext.EntityTarget.THIS,
						EntityPredicate.Builder.entity()
								.subPredicate(new VampirePredicate(
										Optional.of(true),
										Optional.empty()))
				).build()));
		Optional<ContextAwarePredicate> completeVampirePredicate = Optional.of(ContextAwarePredicate.create(
				LootItemEntityPropertyCondition.hasProperties(
						LootContext.EntityTarget.THIS,
						EntityPredicate.Builder.entity()
								.subPredicate(new VampirePredicate(
										Optional.of(true),
										Optional.of(new VampirePredicate.PowerCountPredicate(powerGetter.getOrThrow(ModPowerTags.VAMPIRE_CHOOSABLE), MinMaxBounds.Ints.atLeast(6)))))
								.equipment(new EntityEquipmentPredicate.Builder()
										.head(new ItemPredicate.Builder().of(itemGetter, ModItems.VAMPIRE_HELMET))
										.chest(new ItemPredicate.Builder().of(itemGetter, ModItems.VAMPIRE_CHESTPLATE))
										.legs(new ItemPredicate.Builder().of(itemGetter, ModItems.VAMPIRE_LEGGINGS))
										.feet(new ItemPredicate.Builder().of(itemGetter, ModItems.VAMPIRE_BOOTS))
								)
				).build()));

		AdvancementHolder root = Advancement.Builder.advancement()
				.display(ModItems.VAMPIRE_BLOOD_BOTTLE,
						Component.translatable("advancements.nycto.nycto.root.title"),
						Component.translatable("advancements.nycto.nycto.root.description"),
						Nycto.id("gui/advancements/backgrounds/nycto"),
						AdvancementType.TASK,
						false,
						false,
						false)
				.addCriterion("night", CriteriaTriggers.TICK.createCriterion(new PlayerTrigger.TriggerInstance(Optional.of(
						ContextAwarePredicate.create(
								TimeCheck.time(worldClockGetter.getOrThrow(WorldClocks.OVERWORLD), IntRange.range(12500, 23500))
										.setPeriod(24000)
										.build())))))
				.save(consumer, Nycto.id("nycto/root").toString());
		Advancement.Builder.advancement()
				.parent(root)
				.display(ModItems.OAK_COFFIN,
						Component.translatable("advancements.nycto.nycto.sleep_in_coffin.title"),
						Component.translatable("advancements.nycto.nycto.sleep_in_coffin.description"),
						null,
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("sleep_in_coffin", CriteriaTriggers.SLEPT_IN_BED.createCriterion(new PlayerTrigger.TriggerInstance(Optional.of(
						ContextAwarePredicate.create(
								LootItemEntityPropertyCondition.hasProperties(
												LootContext.EntityTarget.THIS,
												EntityPredicate.Builder.entity()
														.located(LocationPredicate.Builder.location()
																.setBlock(BlockPredicate.Builder.block()
																		.of(blockGetter, ModBlockTags.COFFINS))))
										.build()))
				)))
				.save(consumer, Nycto.id("nycto/sleep_in_coffin").toString());
		AdvancementHolder craftVampiricDagger = Advancement.Builder.advancement()
				.parent(root)
				.display(ModItems.VAMPIRIC_DAGGER,
						Component.translatable("advancements.nycto.nycto.obtain_vampiric_dagger.title"),
						Component.translatable("advancements.nycto.nycto.obtain_vampiric_dagger.description"),
						null,
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("obtain_vampiric_dagger", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.VAMPIRIC_DAGGER))
				.save(consumer, Nycto.id("nycto/obtain_vampiric_dagger").toString());
		Advancement.Builder.advancement()
				.parent(craftVampiricDagger)
				.display(ModItems.BLOOD_BOTTLE,
						Component.translatable("advancements.nycto.nycto.extract_blood_bottle.title"),
						Component.translatable("advancements.nycto.nycto.extract_blood_bottle.description"),
						null,
						AdvancementType.TASK,
						true,
						true,
						false)
				.requirements(AdvancementRequirements.Strategy.OR)
				.addCriterion("extract_blood_bottle_crafting", RecipeCraftedTrigger.TriggerInstance.craftedItem(ModRecipeProvider.BLOOD_EXTRACTION))
				.addCriterion("extract_blood_bottle_inventory", ModTriggers.EXTRACT_BLOOD.createCriterion(new ConsumeItemTrigger.TriggerInstance(Optional.empty(), Optional.empty())))
				.save(consumer, Nycto.id("nycto/extract_blood_bottle").toString());
		AdvancementHolder killVampire = Advancement.Builder.advancement()
				.parent(root)
				.display(ModItems.VAMPIRE_CHESTPLATE,
						Component.translatable("advancements.nycto.nycto.kill_vampire.title"),
						Component.translatable("advancements.nycto.nycto.kill_vampire.description"),
						null,
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("kill_vampire", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypeGetter, ModEntityTypes.VAMPIRE))))
				.save(consumer, Nycto.id("nycto/kill_vampire").toString());
		Advancement.Builder.advancement()
				.parent(killVampire)
				.display(ModItems.WOODEN_STAKE,
						Component.translatable("advancements.nycto.nycto.kill_vampire_with_wooden_stake.title"),
						Component.translatable("advancements.nycto.nycto.kill_vampire_with_wooden_stake.description"),
						null,
						AdvancementType.TASK,
						true,
						true,
						false)
				.requirements(AdvancementRequirements.Strategy.OR)
				.addCriterion("kill_vampire_with_wooden_stake_melee", CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(new KilledTrigger.TriggerInstance(
						Optional.of(ContextAwarePredicate.create(
								LootItemEntityPropertyCondition.hasProperties(
										LootContext.EntityTarget.THIS,
										EntityPredicate.Builder.entity()
												.equipment(EntityEquipmentPredicate.Builder.equipment()
														.mainhand(ItemPredicate.Builder.item().of(itemGetter, ModItems.WOODEN_STAKE)))
								).build())),
						vampirePredicate,
						Optional.empty()
				)))
				.addCriterion("kill_vampire_with_wooden_stake_ranged", CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(new KilledTrigger.TriggerInstance(
						Optional.empty(),
						vampirePredicate,
						Optional.of(DamageSourcePredicate.Builder.damageType()
								.direct(EntityPredicate.Builder.entity()
										.of(entityTypeGetter, ModEntityTypes.WOODEN_STAKE))
								.build())
				)))
				.save(consumer, Nycto.id("nycto/kill_vampire_with_wooden_stake").toString());
		AdvancementHolder becomeVampire = Advancement.Builder.advancement()
				.parent(killVampire)
				.display(ModItems.VAMPIRE_BLOOD_BOTTLE,
						Component.translatable("advancements.nycto.nycto.become_vampire.title"),
						Component.translatable("advancements.nycto.nycto.become_vampire.description"),
						null,
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("become_vampire", ModTriggers.CHANGE_TRANSFORMATION.createCriterion(new PlayerTrigger.TriggerInstance(vampirePredicate)))
				.save(consumer, Nycto.id("nycto/become_vampire").toString());
		AdvancementHolder obtainVampirePower = Advancement.Builder.advancement()
				.parent(becomeVampire)
				.display(ModItems.VAMPIRE_ALTAR,
						Component.translatable("advancements.nycto.nycto.obtain_vampire_power.title"),
						Component.translatable("advancements.nycto.nycto.obtain_vampire_power.description"),
						null,
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("obtain_vampire_power", ModTriggers.CHANGE_POWERS.createCriterion(new PlayerTrigger.TriggerInstance(
						Optional.of(ContextAwarePredicate.create(
								LootItemEntityPropertyCondition.hasProperties(
										LootContext.EntityTarget.THIS,
										EntityPredicate.Builder.entity()
												.subPredicate(new VampirePredicate(
														Optional.of(true),
														Optional.of(new VampirePredicate.PowerCountPredicate(powerGetter.getOrThrow(ModPowerTags.VAMPIRE_CHOOSABLE), MinMaxBounds.Ints.atLeast(1)))))
								).build()))
				)))
				.save(consumer, Nycto.id("nycto/obtain_vampire_power").toString());
		Advancement.Builder.advancement()
				.parent(obtainVampirePower)
				.display(ModItems.VAMPIRE_UPGRADE_SMITHING_TEMPLATE,
						Component.translatable("advancements.nycto.nycto.complete_vampire.title"),
						Component.translatable("advancements.nycto.nycto.complete_vampire.description"),
						null,
						AdvancementType.CHALLENGE,
						true,
						true,
						false)
				.requirements(AdvancementRequirements.Strategy.OR)
				.addCriterion("complete_vampire_powers", ModTriggers.CHANGE_POWERS.createCriterion(new PlayerTrigger.TriggerInstance(completeVampirePredicate)))
				.addCriterion("complete_vampire_armor", CriteriaTriggers.INVENTORY_CHANGED.createCriterion(new InventoryChangeTrigger.TriggerInstance(completeVampirePredicate, InventoryChangeTrigger.TriggerInstance.Slots.ANY, List.of())))
				.rewards(new AdvancementRewards.Builder().addExperience(200))
				.save(consumer, Nycto.id("nycto/complete_vampire").toString());
		AdvancementHolder obtainGarlic = Advancement.Builder.advancement()
				.parent(root)
				.display(ModItems.GARLIC,
						Component.translatable("advancements.nycto.nycto.obtain_garlic.title"),
						Component.translatable("advancements.nycto.nycto.obtain_garlic.description"),
						null,
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("obtain_garlic", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GARLIC))
				.save(consumer, Nycto.id("nycto/obtain_garlic").toString());
		AdvancementHolder brewGarlicBrew = Advancement.Builder.advancement()
				.parent(obtainGarlic)
				.display(new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(ModPotions.GARLIC)).build()),
						Component.translatable("advancements.nycto.nycto.brew_garlic_brew.title"),
						Component.translatable("advancements.nycto.nycto.brew_garlic_brew.description"),
						null,
						AdvancementType.TASK,
						true,
						true,
						false)
				.requirements(AdvancementRequirements.Strategy.OR)
				.addCriterion("brew_garlic_brew", CriteriaTriggers.BREWED_POTION.createCriterion(new BrewedPotionTrigger.TriggerInstance(
						Optional.empty(),
						Optional.of(ModPotions.GARLIC)
				)))
				.addCriterion("brew_garlic_brew_long", CriteriaTriggers.BREWED_POTION.createCriterion(new BrewedPotionTrigger.TriggerInstance(
						Optional.empty(),
						Optional.of(ModPotions.LONG_GARLIC)
				)))
				.addCriterion("brew_garlic_brew_strong", CriteriaTriggers.BREWED_POTION.createCriterion(new BrewedPotionTrigger.TriggerInstance(
						Optional.empty(),
						Optional.of(ModPotions.STRONG_GARLIC)
				)))
				.save(consumer, Nycto.id("nycto/brew_garlic_brew").toString());
		Advancement.Builder.advancement()
				.parent(brewGarlicBrew)
				.display(new ItemStackTemplate(Items.SPLASH_POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(ModPotions.GARLIC)).build()),
						Component.translatable("advancements.nycto.nycto.use_garlic_brew_on_vampire.title"),
						Component.translatable("advancements.nycto.nycto.use_garlic_brew_on_vampire.description"),
						null,
						AdvancementType.TASK,
						true,
						true,
						false)
				.requirements(AdvancementRequirements.Strategy.OR)
				.addCriterion("use_garlic_brew_on_vampire", ModTriggers.PLAYER_APPLIES_EFFECTS.createCriterion(new PlayerAppliesEffectsTrigger.TriggerInstance(
						Optional.empty(),
						MobEffectsPredicate.Builder.effects()
								.and(ModMobEffects.VAMPIRE_WARD)
								.build(),
						vampirePredicate
				)))
				.addCriterion("drink_garlic_brew_as_vampire", CriteriaTriggers.CONSUME_ITEM.createCriterion(new ConsumeItemTrigger.TriggerInstance(
						vampirePredicate,
						Optional.of(
								ItemPredicate.Builder.item()
										.of(itemGetter, Items.POTION)
										.withComponents(DataComponentMatchers.Builder.components()
												.partial(DataComponentPredicates.POTIONS, new PotionsPredicate(HolderSet.direct(ModPotions.GARLIC, ModPotions.LONG_GARLIC, ModPotions.STRONG_GARLIC)))
												.build())
										.build()
						)
				)))
				.save(consumer, Nycto.id("nycto/use_garlic_brew_on_vampire").toString());
		AdvancementHolder craftGarlicWreath = Advancement.Builder.advancement()
				.parent(obtainGarlic)
				.display(ModItems.GARLIC_WREATH,
						Component.translatable("advancements.nycto.nycto.obtain_garlic_wreath.title"),
						Component.translatable("advancements.nycto.nycto.obtain_garlic_wreath.description"),
						null,
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("obtain_garlic_wreath", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GARLIC_WREATH))
				.save(consumer, Nycto.id("nycto/obtain_garlic_wreath").toString());
		Advancement.Builder.advancement()
				.parent(craftGarlicWreath)
				.display(ModItems.GARLIC_COATED_HALBERD,
						Component.translatable("advancements.nycto.nycto.obtain_garlic_coated_halberd.title"),
						Component.translatable("advancements.nycto.nycto.obtain_garlic_coated_halberd.description"),
						null,
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("obtain_garlic_coated_halberd", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GARLIC_COATED_HALBERD))
				.save(consumer, Nycto.id("nycto/obtain_garlic_coated_halberd").toString());
		Advancement.Builder.advancement()
				.parent(craftGarlicWreath)
				.display(ModItems.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE,
						Component.translatable("advancements.nycto.nycto.wear_vampire_hunter_armor.title"),
						Component.translatable("advancements.nycto.nycto.wear_vampire_hunter_armor.description"),
						null,
						AdvancementType.GOAL,
						true,
						true,
						false)
				.addCriterion("wear_vampire_hunter_armor", CriteriaTriggers.INVENTORY_CHANGED.createCriterion(new InventoryChangeTrigger.TriggerInstance(
						Optional.of(ContextAwarePredicate.create(
								LootItemEntityPropertyCondition.hasProperties(
										LootContext.EntityTarget.THIS,
										EntityPredicate.Builder.entity()
												.equipment(new EntityEquipmentPredicate.Builder()
														.head(new ItemPredicate.Builder().of(itemGetter, ModItems.VAMPIRE_HUNTER_HELMET))
														.chest(new ItemPredicate.Builder().of(itemGetter, ModItems.VAMPIRE_HUNTER_CHESTPLATE))
														.legs(new ItemPredicate.Builder().of(itemGetter, ModItems.VAMPIRE_HUNTER_LEGGINGS))
														.feet(new ItemPredicate.Builder().of(itemGetter, ModItems.VAMPIRE_HUNTER_BOOTS))
												)
								).build())),
						InventoryChangeTrigger.TriggerInstance.Slots.ANY, List.of())))
				.save(consumer, Nycto.id("nycto/wear_vampire_hunter_armor").toString());
		Advancement.Builder.advancement()
				.parent(root)
				.display(ModItems.HUNTER_CONTRACT,
						Component.translatable("advancements.nycto.nycto.buy_hunter_contract.title"),
						Component.translatable("advancements.nycto.nycto.buy_hunter_contract.description"),
						null,
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("buy_hunter_contract", CriteriaTriggers.TRADE.createCriterion(new TradeTrigger.TriggerInstance(
						Optional.empty(),
						Optional.empty(),
						Optional.of(
								ItemPredicate.Builder.item()
										.of(itemGetter, ModItems.HUNTER_CONTRACT)
										.build()))))
				.save(consumer, Nycto.id("nycto/buy_hunter_contract").toString());
	}
}
