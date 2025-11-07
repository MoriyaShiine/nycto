/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.power.Power;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.criterion.PlayerAppliesEffectsCriterion;
import moriyashiine.nycto.common.entity.subpredicate.VampirePredicate;
import moriyashiine.nycto.common.init.*;
import moriyashiine.nycto.common.tag.ModBlockTags;
import moriyashiine.nycto.common.tag.ModPowerTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.block.Block;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.TimeCheckLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.operator.BoundedIntUnaryOperator;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.component.ComponentPredicateTypes;
import net.minecraft.predicate.component.ComponentsPredicate;
import net.minecraft.predicate.entity.*;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.predicate.item.PotionContentsPredicate;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends FabricAdvancementProvider {
	public ModAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(output, registryLookup);
	}

	@Override
	public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {
		RegistryEntryLookup<Block> blockLookup = registryLookup.getOrThrow(RegistryKeys.BLOCK);
		RegistryEntryLookup<Item> itemLookup = registryLookup.getOrThrow(RegistryKeys.ITEM);
		RegistryEntryLookup<EntityType<?>> entityTypeLookup = registryLookup.getOrThrow(RegistryKeys.ENTITY_TYPE);
		RegistryEntryLookup<Power> powerLookup = registryLookup.getOrThrow(NyctoRegistries.POWER_KEY);

		Optional<LootContextPredicate> vampirePredicate = Optional.of(LootContextPredicate.create(
				EntityPropertiesLootCondition.builder(
						LootContext.EntityReference.THIS,
						EntityPredicate.Builder.create()
								.typeSpecific(new VampirePredicate(
										Optional.of(true),
										Optional.empty()))
				).build()));
		Optional<LootContextPredicate> completeVampirePredicate = Optional.of(LootContextPredicate.create(
				EntityPropertiesLootCondition.builder(
						LootContext.EntityReference.THIS,
						EntityPredicate.Builder.create()
								.typeSpecific(new VampirePredicate(
										Optional.of(true),
										Optional.of(new VampirePredicate.PowerCountPredicate(powerLookup.getOrThrow(ModPowerTags.VAMPIRE_CHOOSABLE), NumberRange.IntRange.atLeast(6)))))
								.equipment(new EntityEquipmentPredicate.Builder()
										.head(new ItemPredicate.Builder().items(itemLookup, ModItems.VAMPIRE_HELMET))
										.chest(new ItemPredicate.Builder().items(itemLookup, ModItems.VAMPIRE_CHESTPLATE))
										.legs(new ItemPredicate.Builder().items(itemLookup, ModItems.VAMPIRE_LEGGINGS))
										.feet(new ItemPredicate.Builder().items(itemLookup, ModItems.VAMPIRE_BOOTS))
								)
				).build()));

		AdvancementEntry root = Advancement.Builder.create()
				.display(ModItems.VAMPIRE_BLOOD_BOTTLE,
						Text.translatable("advancements.nycto.nycto.root.title"),
						Text.translatable("advancements.nycto.nycto.root.description"),
						Nycto.id("gui/advancements/backgrounds/nycto"),
						AdvancementFrame.TASK,
						false,
						false,
						false)
				.criterion("night", Criteria.TICK.create(new TickCriterion.Conditions(Optional.of(
						LootContextPredicate.create(
								TimeCheckLootCondition.create(
												BoundedIntUnaryOperator.create(12500, 23500))
										.period(24000)
										.build())))))
				.build(consumer, Nycto.id("nycto/root").toString());
		Advancement.Builder.create()
				.parent(root)
				.display(ModItems.OAK_COFFIN,
						Text.translatable("advancements.nycto.nycto.sleep_in_coffin.title"),
						Text.translatable("advancements.nycto.nycto.sleep_in_coffin.description"),
						null,
						AdvancementFrame.TASK,
						true,
						true,
						false)
				.criterion("sleep_in_coffin", Criteria.SLEPT_IN_BED.create(new TickCriterion.Conditions(Optional.of(
						LootContextPredicate.create(
								EntityPropertiesLootCondition.builder(
												LootContext.EntityReference.THIS,
												EntityPredicate.Builder.create()
														.location(LocationPredicate.Builder.create()
																.block(BlockPredicate.Builder.create()
																		.tag(blockLookup, ModBlockTags.COFFINS))))
										.build()))
				)))
				.build(consumer, Nycto.id("nycto/sleep_in_coffin").toString());
		AdvancementEntry craftVampiricDagger = Advancement.Builder.create()
				.parent(root)
				.display(ModItems.VAMPIRIC_DAGGER,
						Text.translatable("advancements.nycto.nycto.obtain_vampiric_dagger.title"),
						Text.translatable("advancements.nycto.nycto.obtain_vampiric_dagger.description"),
						null,
						AdvancementFrame.TASK,
						true,
						true,
						false)
				.criterion("obtain_vampiric_dagger", InventoryChangedCriterion.Conditions.items(ModItems.VAMPIRIC_DAGGER))
				.build(consumer, Nycto.id("nycto/obtain_vampiric_dagger").toString());
		Advancement.Builder.create()
				.parent(craftVampiricDagger)
				.display(ModItems.BLOOD_BOTTLE,
						Text.translatable("advancements.nycto.nycto.extract_blood_bottle.title"),
						Text.translatable("advancements.nycto.nycto.extract_blood_bottle.description"),
						null,
						AdvancementFrame.TASK,
						true,
						true,
						false)
				.criteriaMerger(AdvancementRequirements.CriterionMerger.OR)
				.criterion("extract_blood_bottle_crafting", RecipeCraftedCriterion.Conditions.create(ModRecipeProvider.BLOOD_EXTRACTION))
				.criterion("extract_blood_bottle_inventory", ModCriteria.EXTRACT_BLOOD.create(new ConsumeItemCriterion.Conditions(Optional.empty(), Optional.empty())))
				.build(consumer, Nycto.id("nycto/extract_blood_bottle").toString());
		AdvancementEntry killVampire = Advancement.Builder.create()
				.parent(root)
				.display(ModItems.VAMPIRE_CHESTPLATE,
						Text.translatable("advancements.nycto.nycto.kill_vampire.title"),
						Text.translatable("advancements.nycto.nycto.kill_vampire.description"),
						null,
						AdvancementFrame.TASK,
						true,
						true,
						false)
				.criterion("kill_vampire", OnKilledCriterion.Conditions.createPlayerKilledEntity(EntityPredicate.Builder.create().type(EntityTypePredicate.create(entityTypeLookup, ModEntityTypes.VAMPIRE))))
				.build(consumer, Nycto.id("nycto/kill_vampire").toString());
		Advancement.Builder.create()
				.parent(killVampire)
				.display(ModItems.WOODEN_STAKE,
						Text.translatable("advancements.nycto.nycto.kill_vampire_with_wooden_stake.title"),
						Text.translatable("advancements.nycto.nycto.kill_vampire_with_wooden_stake.description"),
						null,
						AdvancementFrame.TASK,
						true,
						true,
						false)
				.criteriaMerger(AdvancementRequirements.CriterionMerger.OR)
				.criterion("kill_vampire_with_wooden_stake_melee", Criteria.PLAYER_KILLED_ENTITY.create(new OnKilledCriterion.Conditions(
						Optional.of(LootContextPredicate.create(
								EntityPropertiesLootCondition.builder(
										LootContext.EntityReference.THIS,
										EntityPredicate.Builder.create()
												.equipment(EntityEquipmentPredicate.Builder.create()
														.mainhand(ItemPredicate.Builder.create().items(itemLookup, ModItems.WOODEN_STAKE)))
								).build())),
						vampirePredicate,
						Optional.empty()
				)))
				.criterion("kill_vampire_with_wooden_stake_ranged", Criteria.PLAYER_KILLED_ENTITY.create(new OnKilledCriterion.Conditions(
						Optional.empty(),
						vampirePredicate,
						Optional.of(DamageSourcePredicate.Builder.create()
								.directEntity(EntityPredicate.Builder.create()
										.type(entityTypeLookup, ModEntityTypes.WOODEN_STAKE))
								.build())
				)))
				.build(consumer, Nycto.id("nycto/kill_vampire_with_wooden_stake").toString());
		AdvancementEntry becomeVampire = Advancement.Builder.create()
				.parent(killVampire)
				.display(ModItems.VAMPIRE_BLOOD_BOTTLE,
						Text.translatable("advancements.nycto.nycto.become_vampire.title"),
						Text.translatable("advancements.nycto.nycto.become_vampire.description"),
						null,
						AdvancementFrame.TASK,
						true,
						true,
						false)
				.criterion("become_vampire", ModCriteria.CHANGE_TRANSFORMATION.create(new TickCriterion.Conditions(vampirePredicate)))
				.build(consumer, Nycto.id("nycto/become_vampire").toString());
		AdvancementEntry obtainVampirePower = Advancement.Builder.create()
				.parent(becomeVampire)
				.display(ModItems.VAMPIRE_ALTAR,
						Text.translatable("advancements.nycto.nycto.obtain_vampire_power.title"),
						Text.translatable("advancements.nycto.nycto.obtain_vampire_power.description"),
						null,
						AdvancementFrame.TASK,
						true,
						true,
						false)
				.criterion("obtain_vampire_power", ModCriteria.CHANGE_POWERS.create(new TickCriterion.Conditions(
						Optional.of(LootContextPredicate.create(
								EntityPropertiesLootCondition.builder(
										LootContext.EntityReference.THIS,
										EntityPredicate.Builder.create()
												.typeSpecific(new VampirePredicate(
														Optional.of(true),
														Optional.of(new VampirePredicate.PowerCountPredicate(powerLookup.getOrThrow(ModPowerTags.VAMPIRE_CHOOSABLE), NumberRange.IntRange.atLeast(1)))))
								).build()))
				)))
				.build(consumer, Nycto.id("nycto/obtain_vampire_power").toString());
		Advancement.Builder.create()
				.parent(obtainVampirePower)
				.display(ModItems.VAMPIRE_UPGRADE_SMITHING_TEMPLATE,
						Text.translatable("advancements.nycto.nycto.complete_vampire.title"),
						Text.translatable("advancements.nycto.nycto.complete_vampire.description"),
						null,
						AdvancementFrame.CHALLENGE,
						true,
						true,
						false)
				.criteriaMerger(AdvancementRequirements.CriterionMerger.OR)
				.criterion("complete_vampire_powers", ModCriteria.CHANGE_POWERS.create(new TickCriterion.Conditions(completeVampirePredicate)))
				.criterion("complete_vampire_armor", Criteria.INVENTORY_CHANGED.create(new InventoryChangedCriterion.Conditions(completeVampirePredicate, InventoryChangedCriterion.Conditions.Slots.ANY, List.of())))
				.rewards(new AdvancementRewards.Builder().setExperience(200))
				.build(consumer, Nycto.id("nycto/complete_vampire").toString());
		AdvancementEntry obtainGarlic = Advancement.Builder.create()
				.parent(root)
				.display(ModItems.GARLIC,
						Text.translatable("advancements.nycto.nycto.obtain_garlic.title"),
						Text.translatable("advancements.nycto.nycto.obtain_garlic.description"),
						null,
						AdvancementFrame.TASK,
						true,
						true,
						false)
				.criterion("obtain_garlic", InventoryChangedCriterion.Conditions.items(ModItems.GARLIC))
				.build(consumer, Nycto.id("nycto/obtain_garlic").toString());
		AdvancementEntry brewGarlicBrew = Advancement.Builder.create()
				.parent(obtainGarlic)
				.display(PotionContentsComponent.createStack(Items.POTION, ModPotions.GARLIC),
						Text.translatable("advancements.nycto.nycto.brew_garlic_brew.title"),
						Text.translatable("advancements.nycto.nycto.brew_garlic_brew.description"),
						null,
						AdvancementFrame.TASK,
						true,
						true,
						false)
				.criteriaMerger(AdvancementRequirements.CriterionMerger.OR)
				.criterion("brew_garlic_brew", Criteria.BREWED_POTION.create(new BrewedPotionCriterion.Conditions(
						Optional.empty(),
						Optional.of(ModPotions.GARLIC)
				)))
				.criterion("brew_garlic_brew_long", Criteria.BREWED_POTION.create(new BrewedPotionCriterion.Conditions(
						Optional.empty(),
						Optional.of(ModPotions.LONG_GARLIC)
				)))
				.criterion("brew_garlic_brew_strong", Criteria.BREWED_POTION.create(new BrewedPotionCriterion.Conditions(
						Optional.empty(),
						Optional.of(ModPotions.STRONG_GARLIC)
				)))
				.build(consumer, Nycto.id("nycto/brew_garlic_brew").toString());
		Advancement.Builder.create()
				.parent(brewGarlicBrew)
				.display(PotionContentsComponent.createStack(Items.SPLASH_POTION, ModPotions.GARLIC),
						Text.translatable("advancements.nycto.nycto.use_garlic_brew_on_vampire.title"),
						Text.translatable("advancements.nycto.nycto.use_garlic_brew_on_vampire.description"),
						null,
						AdvancementFrame.TASK,
						true,
						true,
						false)
				.criteriaMerger(AdvancementRequirements.CriterionMerger.OR)
				.criterion("use_garlic_brew_on_vampire", ModCriteria.PLAYER_APPLIES_EFFECTS.create(new PlayerAppliesEffectsCriterion.Conditions(
						Optional.empty(),
						EntityEffectPredicate.Builder.create()
								.addEffect(ModStatusEffects.VAMPIRE_WARD)
								.build(),
						vampirePredicate
				)))
				.criterion("drink_garlic_brew_as_vampire", Criteria.CONSUME_ITEM.create(new ConsumeItemCriterion.Conditions(
						vampirePredicate,
						Optional.of(
								ItemPredicate.Builder.create()
										.items(itemLookup, Items.POTION)
										.components(ComponentsPredicate.Builder.create()
												.partial(ComponentPredicateTypes.POTION_CONTENTS, new PotionContentsPredicate(RegistryEntryList.of(ModPotions.GARLIC, ModPotions.LONG_GARLIC, ModPotions.STRONG_GARLIC)))
												.build())
										.build()
						)
				)))
				.build(consumer, Nycto.id("nycto/use_garlic_brew_on_vampire").toString());
		AdvancementEntry craftGarlicWreath = Advancement.Builder.create()
				.parent(obtainGarlic)
				.display(ModItems.GARLIC_WREATH,
						Text.translatable("advancements.nycto.nycto.obtain_garlic_wreath.title"),
						Text.translatable("advancements.nycto.nycto.obtain_garlic_wreath.description"),
						null,
						AdvancementFrame.TASK,
						true,
						true,
						false)
				.criterion("obtain_garlic_wreath", InventoryChangedCriterion.Conditions.items(ModItems.GARLIC_WREATH))
				.build(consumer, Nycto.id("nycto/obtain_garlic_wreath").toString());
		Advancement.Builder.create()
				.parent(craftGarlicWreath)
				.display(ModItems.GARLIC_COATED_HALBERD,
						Text.translatable("advancements.nycto.nycto.obtain_garlic_coated_halberd.title"),
						Text.translatable("advancements.nycto.nycto.obtain_garlic_coated_halberd.description"),
						null,
						AdvancementFrame.TASK,
						true,
						true,
						false)
				.criterion("obtain_garlic_coated_halberd", InventoryChangedCriterion.Conditions.items(ModItems.GARLIC_COATED_HALBERD))
				.build(consumer, Nycto.id("nycto/obtain_garlic_coated_halberd").toString());
		Advancement.Builder.create()
				.parent(craftGarlicWreath)
				.display(ModItems.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE,
						Text.translatable("advancements.nycto.nycto.wear_vampire_hunter_armor.title"),
						Text.translatable("advancements.nycto.nycto.wear_vampire_hunter_armor.description"),
						null,
						AdvancementFrame.GOAL,
						true,
						true,
						false)
				.criterion("wear_vampire_hunter_armor", Criteria.INVENTORY_CHANGED.create(new InventoryChangedCriterion.Conditions(
						Optional.of(LootContextPredicate.create(
								EntityPropertiesLootCondition.builder(
										LootContext.EntityReference.THIS,
										EntityPredicate.Builder.create()
												.equipment(new EntityEquipmentPredicate.Builder()
														.head(new ItemPredicate.Builder().items(itemLookup, ModItems.VAMPIRE_HUNTER_HELMET))
														.chest(new ItemPredicate.Builder().items(itemLookup, ModItems.VAMPIRE_HUNTER_CHESTPLATE))
														.legs(new ItemPredicate.Builder().items(itemLookup, ModItems.VAMPIRE_HUNTER_LEGGINGS))
														.feet(new ItemPredicate.Builder().items(itemLookup, ModItems.VAMPIRE_HUNTER_BOOTS))
												)
								).build())),
						InventoryChangedCriterion.Conditions.Slots.ANY, List.of())))
				.build(consumer, Nycto.id("nycto/wear_vampire_hunter_armor").toString());
		Advancement.Builder.create()
				.parent(root)
				.display(ModItems.HUNTER_CONTRACT,
						Text.translatable("advancements.nycto.nycto.buy_hunter_contract.title"),
						Text.translatable("advancements.nycto.nycto.buy_hunter_contract.description"),
						null,
						AdvancementFrame.TASK,
						true,
						true,
						false)
				.criterion("buy_hunter_contract", Criteria.VILLAGER_TRADE.create(new VillagerTradeCriterion.Conditions(
						Optional.empty(),
						Optional.empty(),
						Optional.of(
								ItemPredicate.Builder.create()
										.items(itemLookup, ModItems.HUNTER_CONTRACT)
										.build()))))
				.build(consumer, Nycto.id("nycto/buy_hunter_contract").toString());
	}
}
