package moriyashiine.nycto.datagen.provider;

import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.world.power.Power;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.advancements.criterion.PlayerAppliesEffectsTrigger;
import moriyashiine.nycto.common.init.*;
import moriyashiine.nycto.common.tag.NyctoBlockTags;
import moriyashiine.nycto.common.tag.NyctoPowerTags;
import moriyashiine.nycto.common.world.entity.subpredicate.VampirePredicate;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.*;
import net.minecraft.advancements.predicates.*;
import net.minecraft.advancements.predicates.entity.EntityEquipmentPredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.predicates.entity.EntityTypePredicate;
import net.minecraft.advancements.triggers.*;
import net.minecraft.core.Holder;
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
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.IntRangePredicate;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.TimeCheck;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class NyctoAdvancementProvider extends FabricAdvancementProvider {
	public NyctoAdvancementProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void generateAdvancement(HolderLookup.Provider registries, Consumer<AdvancementHolder> consumer) {
		HolderGetter<Block> blocks = registries.lookupOrThrow(Registries.BLOCK);
		HolderGetter<Item> items = registries.lookupOrThrow(Registries.ITEM);
		HolderGetter<EntityType<?>> entityTypes = registries.lookupOrThrow(Registries.ENTITY_TYPE);
		HolderGetter<Recipe<?>> recipes = registries.lookupOrThrow(Registries.RECIPE);
		HolderGetter<WorldClock> worldClocks = registries.lookupOrThrow(Registries.WORLD_CLOCK);
		HolderGetter<Power> powers = registries.lookupOrThrow(NyctoRegistries.POWER_KEY);

		Optional<Holder<LootItemCondition>> vampirePredicate = Optional.of(Holder.direct(
				LootItemEntityPropertyCondition.hasProperties(
						LootContext.EntityTarget.THIS,
						EntityPredicate.Builder.entity()
								.put(VampirePredicate.CODEC,
										new VampirePredicate(
												Optional.of(true),
												Optional.empty()))
				).build()));
		Optional<Holder<LootItemCondition>> completeVampirePredicate = Optional.of(Holder.direct(
				LootItemEntityPropertyCondition.hasProperties(
						LootContext.EntityTarget.THIS,
						EntityPredicate.Builder.entity()
								.put(VampirePredicate.CODEC,
										new VampirePredicate(
												Optional.of(true),
												Optional.of(new VampirePredicate.PowerCountPredicate(powers.getOrThrow(NyctoPowerTags.VAMPIRE_CHOOSABLE), MinMaxBounds.Ints.atLeast(6)))))
								.equipment(new EntityEquipmentPredicate.Builder()
										.head(new ItemPredicate.Builder().of(items, NyctoItems.VAMPIRE_HELMET))
										.chest(new ItemPredicate.Builder().of(items, NyctoItems.VAMPIRE_CHESTPLATE))
										.legs(new ItemPredicate.Builder().of(items, NyctoItems.VAMPIRE_LEGGINGS))
										.feet(new ItemPredicate.Builder().of(items, NyctoItems.VAMPIRE_BOOTS))
								)
				).build()));

		AdvancementHolder root = Advancement.Builder.advancement()
				.rootDisplay(NyctoItems.VAMPIRE_BLOOD_BOTTLE,
						Component.translatable("advancements.nycto.nycto.root.title"),
						Component.translatable("advancements.nycto.nycto.root.description"),
						Nycto.id("gui/advancements/backgrounds/nycto"),
						AdvancementType.TASK,
						false,
						false,
						false)
				.addCriterion("night", CriteriaTriggers.TICK.createCriterion(new PlayerTrigger.TriggerInstance(Optional.of(
						Holder.direct(
								TimeCheck.time(worldClocks.getOrThrow(WorldClocks.OVERWORLD), IntRangePredicate.range(12500, 23500))
										.setPeriod(24000)
										.build())))))
				.save(consumer, Nycto.id("nycto/root"));
		Advancement.Builder.advancement()
				.parent(root)
				.display(NyctoItems.OAK_COFFIN,
						Component.translatable("advancements.nycto.nycto.sleep_in_coffin.title"),
						Component.translatable("advancements.nycto.nycto.sleep_in_coffin.description"),
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("sleep_in_coffin", CriteriaTriggers.SLEPT_IN_BED.createCriterion(new PlayerTrigger.TriggerInstance(Optional.of(
						Holder.direct(
								LootItemEntityPropertyCondition.hasProperties(
												LootContext.EntityTarget.THIS,
												EntityPredicate.Builder.entity()
														.located(LocationPredicate.Builder.location()
																.setBlock(BlockPredicate.Builder.block()
																		.of(blocks, NyctoBlockTags.COFFINS))))
										.build()))
				)))
				.save(consumer, Nycto.id("nycto/sleep_in_coffin"));
		AdvancementHolder craftVampiricDagger = Advancement.Builder.advancement()
				.parent(root)
				.display(NyctoItems.VAMPIRIC_DAGGER,
						Component.translatable("advancements.nycto.nycto.obtain_vampiric_dagger.title"),
						Component.translatable("advancements.nycto.nycto.obtain_vampiric_dagger.description"),
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("obtain_vampiric_dagger", InventoryChangeTrigger.TriggerInstance.hasItems(NyctoItems.VAMPIRIC_DAGGER))
				.save(consumer, Nycto.id("nycto/obtain_vampiric_dagger"));
		Advancement.Builder.advancement()
				.parent(craftVampiricDagger)
				.display(NyctoItems.BLOOD_BOTTLE,
						Component.translatable("advancements.nycto.nycto.extract_blood_bottle.title"),
						Component.translatable("advancements.nycto.nycto.extract_blood_bottle.description"),
						AdvancementType.TASK,
						true,
						true,
						false)
				.requirements(AdvancementRequirements.Strategy.OR)
				.addCriterion("extract_blood_bottle_crafting", RecipeCraftedTrigger.TriggerInstance.craftedItem(HolderSet.direct(recipes.getOrThrow(NyctoRecipeProvider.BLOOD_EXTRACTION))))
				.addCriterion("extract_blood_bottle_inventory", NyctoTriggers.EXTRACT_BLOOD.createCriterion(new ConsumeItemTrigger.TriggerInstance(Optional.empty(), Optional.empty())))
				.save(consumer, Nycto.id("nycto/extract_blood_bottle"));
		AdvancementHolder killVampire = Advancement.Builder.advancement()
				.parent(root)
				.display(NyctoItems.VAMPIRE_CHESTPLATE,
						Component.translatable("advancements.nycto.nycto.kill_vampire.title"),
						Component.translatable("advancements.nycto.nycto.kill_vampire.description"),
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("kill_vampire", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypes, NyctoEntityTypes.VAMPIRE))))
				.save(consumer, Nycto.id("nycto/kill_vampire"));
		Advancement.Builder.advancement()
				.parent(killVampire)
				.display(NyctoItems.WOODEN_STAKE,
						Component.translatable("advancements.nycto.nycto.kill_vampire_with_wooden_stake.title"),
						Component.translatable("advancements.nycto.nycto.kill_vampire_with_wooden_stake.description"),
						AdvancementType.TASK,
						true,
						true,
						false)
				.requirements(AdvancementRequirements.Strategy.OR)
				.addCriterion("kill_vampire_with_wooden_stake_melee", CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(new KilledTrigger.TriggerInstance(
						Optional.of(Holder.direct(
								LootItemEntityPropertyCondition.hasProperties(
										LootContext.EntityTarget.THIS,
										EntityPredicate.Builder.entity()
												.equipment(EntityEquipmentPredicate.Builder.equipment()
														.mainhand(ItemPredicate.Builder.item().of(items, NyctoItems.WOODEN_STAKE)))
								).build())),
						vampirePredicate,
						Optional.empty()
				)))
				.addCriterion("kill_vampire_with_wooden_stake_ranged", CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(new KilledTrigger.TriggerInstance(
						Optional.empty(),
						vampirePredicate,
						Optional.of(DamageSourcePredicate.Builder.damageType()
								.direct(EntityPredicate.Builder.entity()
										.of(entityTypes, NyctoEntityTypes.WOODEN_STAKE))
								.build())
				)))
				.save(consumer, Nycto.id("nycto/kill_vampire_with_wooden_stake"));
		AdvancementHolder becomeVampire = Advancement.Builder.advancement()
				.parent(killVampire)
				.display(NyctoItems.VAMPIRE_BLOOD_BOTTLE,
						Component.translatable("advancements.nycto.nycto.become_vampire.title"),
						Component.translatable("advancements.nycto.nycto.become_vampire.description"),
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("become_vampire", NyctoTriggers.CHANGE_TRANSFORMATION.createCriterion(new PlayerTrigger.TriggerInstance(vampirePredicate)))
				.save(consumer, Nycto.id("nycto/become_vampire"));
		AdvancementHolder obtainVampirePower = Advancement.Builder.advancement()
				.parent(becomeVampire)
				.display(NyctoItems.VAMPIRE_ALTAR,
						Component.translatable("advancements.nycto.nycto.obtain_vampire_power.title"),
						Component.translatable("advancements.nycto.nycto.obtain_vampire_power.description"),
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("obtain_vampire_power", NyctoTriggers.CHANGE_POWERS.createCriterion(new PlayerTrigger.TriggerInstance(
						Optional.of(Holder.direct(
								LootItemEntityPropertyCondition.hasProperties(
										LootContext.EntityTarget.THIS,
										EntityPredicate.Builder.entity()
												.put(VampirePredicate.CODEC,
														new VampirePredicate(
																Optional.of(true),
																Optional.of(new VampirePredicate.PowerCountPredicate(powers.getOrThrow(NyctoPowerTags.VAMPIRE_CHOOSABLE), MinMaxBounds.Ints.atLeast(1)))))
								).build()))
				)))
				.save(consumer, Nycto.id("nycto/obtain_vampire_power"));
		Advancement.Builder.advancement()
				.parent(obtainVampirePower)
				.display(NyctoItems.VAMPIRE_UPGRADE_SMITHING_TEMPLATE,
						Component.translatable("advancements.nycto.nycto.complete_vampire.title"),
						Component.translatable("advancements.nycto.nycto.complete_vampire.description"),
						AdvancementType.CHALLENGE,
						true,
						true,
						false)
				.requirements(AdvancementRequirements.Strategy.OR)
				.addCriterion("complete_vampire_powers", NyctoTriggers.CHANGE_POWERS.createCriterion(new PlayerTrigger.TriggerInstance(completeVampirePredicate)))
				.addCriterion("complete_vampire_armor", CriteriaTriggers.INVENTORY_CHANGED.createCriterion(new InventoryChangeTrigger.TriggerInstance(completeVampirePredicate, InventoryChangeTrigger.TriggerInstance.Slots.ANY, List.of())))
				.rewards(new AdvancementRewards.Builder().addExperience(200))
				.save(consumer, Nycto.id("nycto/complete_vampire"));
		AdvancementHolder obtainGarlic = Advancement.Builder.advancement()
				.parent(root)
				.display(NyctoItems.GARLIC,
						Component.translatable("advancements.nycto.nycto.obtain_garlic.title"),
						Component.translatable("advancements.nycto.nycto.obtain_garlic.description"),
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("obtain_garlic", InventoryChangeTrigger.TriggerInstance.hasItems(NyctoItems.GARLIC))
				.save(consumer, Nycto.id("nycto/obtain_garlic"));
		AdvancementHolder brewGarlicBrew = Advancement.Builder.advancement()
				.parent(obtainGarlic)
				.display(new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(NyctoPotions.GARLIC)).build()),
						Component.translatable("advancements.nycto.nycto.brew_garlic_brew.title"),
						Component.translatable("advancements.nycto.nycto.brew_garlic_brew.description"),
						AdvancementType.TASK,
						true,
						true,
						false)
				.requirements(AdvancementRequirements.Strategy.OR)
				.addCriterion("brew_garlic_brew", CriteriaTriggers.BREWED_POTION.createCriterion(new BrewedPotionTrigger.TriggerInstance(
						Optional.empty(),
						Optional.of(PotionsPredicate.ofPotion(NyctoPotions.GARLIC))
				)))
				.addCriterion("brew_garlic_brew_long", CriteriaTriggers.BREWED_POTION.createCriterion(new BrewedPotionTrigger.TriggerInstance(
						Optional.empty(),
						Optional.of(PotionsPredicate.ofPotion(NyctoPotions.LONG_GARLIC))
				)))
				.addCriterion("brew_garlic_brew_strong", CriteriaTriggers.BREWED_POTION.createCriterion(new BrewedPotionTrigger.TriggerInstance(
						Optional.empty(),
						Optional.of(PotionsPredicate.ofPotion(NyctoPotions.STRONG_GARLIC))
				)))
				.save(consumer, Nycto.id("nycto/brew_garlic_brew"));
		Advancement.Builder.advancement()
				.parent(brewGarlicBrew)
				.display(new ItemStackTemplate(Items.SPLASH_POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(NyctoPotions.GARLIC)).build()),
						Component.translatable("advancements.nycto.nycto.use_garlic_brew_on_vampire.title"),
						Component.translatable("advancements.nycto.nycto.use_garlic_brew_on_vampire.description"),
						AdvancementType.TASK,
						true,
						true,
						false)
				.requirements(AdvancementRequirements.Strategy.OR)
				.addCriterion("use_garlic_brew_on_vampire", NyctoTriggers.PLAYER_APPLIES_EFFECTS.createCriterion(new PlayerAppliesEffectsTrigger.TriggerInstance(
						Optional.empty(),
						Optional.of(MobEffectsPredicate.Builder.effects()
								.and(NyctoMobEffects.VAMPIRE_WARD)
								.build()),
						vampirePredicate
				)))
				.addCriterion("drink_garlic_brew_as_vampire", CriteriaTriggers.CONSUME_ITEM.createCriterion(new ConsumeItemTrigger.TriggerInstance(
						vampirePredicate,
						Optional.of(
								ItemPredicate.Builder.item()
										.of(items, Items.POTION)
										.withComponents(DataComponentMatchers.Builder.components()
												.partial(DataComponentPredicates.POTIONS, PotionsPredicate.ofPotions(HolderSet.direct(NyctoPotions.GARLIC, NyctoPotions.LONG_GARLIC, NyctoPotions.STRONG_GARLIC)))
												.build())
										.build()
						)
				)))
				.save(consumer, Nycto.id("nycto/use_garlic_brew_on_vampire"));
		AdvancementHolder craftGarlicWreath = Advancement.Builder.advancement()
				.parent(obtainGarlic)
				.display(NyctoItems.GARLIC_WREATH,
						Component.translatable("advancements.nycto.nycto.obtain_garlic_wreath.title"),
						Component.translatable("advancements.nycto.nycto.obtain_garlic_wreath.description"),
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("obtain_garlic_wreath", InventoryChangeTrigger.TriggerInstance.hasItems(NyctoItems.GARLIC_WREATH))
				.save(consumer, Nycto.id("nycto/obtain_garlic_wreath"));
		Advancement.Builder.advancement()
				.parent(craftGarlicWreath)
				.display(NyctoItems.GARLIC_COATED_HALBERD,
						Component.translatable("advancements.nycto.nycto.obtain_garlic_coated_halberd.title"),
						Component.translatable("advancements.nycto.nycto.obtain_garlic_coated_halberd.description"),
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("obtain_garlic_coated_halberd", InventoryChangeTrigger.TriggerInstance.hasItems(NyctoItems.GARLIC_COATED_HALBERD))
				.save(consumer, Nycto.id("nycto/obtain_garlic_coated_halberd"));
		Advancement.Builder.advancement()
				.parent(craftGarlicWreath)
				.display(NyctoItems.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE,
						Component.translatable("advancements.nycto.nycto.wear_vampire_hunter_armor.title"),
						Component.translatable("advancements.nycto.nycto.wear_vampire_hunter_armor.description"),
						AdvancementType.GOAL,
						true,
						true,
						false)
				.addCriterion("wear_vampire_hunter_armor", CriteriaTriggers.INVENTORY_CHANGED.createCriterion(new InventoryChangeTrigger.TriggerInstance(
						Optional.of(Holder.direct(
								LootItemEntityPropertyCondition.hasProperties(
										LootContext.EntityTarget.THIS,
										EntityPredicate.Builder.entity()
												.equipment(new EntityEquipmentPredicate.Builder()
														.head(new ItemPredicate.Builder().of(items, NyctoItems.VAMPIRE_HUNTER_HELMET))
														.chest(new ItemPredicate.Builder().of(items, NyctoItems.VAMPIRE_HUNTER_CHESTPLATE))
														.legs(new ItemPredicate.Builder().of(items, NyctoItems.VAMPIRE_HUNTER_LEGGINGS))
														.feet(new ItemPredicate.Builder().of(items, NyctoItems.VAMPIRE_HUNTER_BOOTS))
												)
								).build())),
						InventoryChangeTrigger.TriggerInstance.Slots.ANY, List.of())))
				.save(consumer, Nycto.id("nycto/wear_vampire_hunter_armor"));
		Advancement.Builder.advancement()
				.parent(root)
				.display(NyctoItems.HUNTER_CONTRACT,
						Component.translatable("advancements.nycto.nycto.buy_hunter_contract.title"),
						Component.translatable("advancements.nycto.nycto.buy_hunter_contract.description"),
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("buy_hunter_contract", CriteriaTriggers.TRADE.createCriterion(new TradeTrigger.TriggerInstance(
						Optional.empty(),
						Optional.empty(),
						Optional.of(
								ItemPredicate.Builder.item()
										.of(items, NyctoItems.HUNTER_CONTRACT)
										.build()))))
				.save(consumer, Nycto.id("nycto/buy_hunter_contract"));
	}
}
