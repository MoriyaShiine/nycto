/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.NyctoNeoForge;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ModRegistration {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NyctoNeoForge.MOD_ID);
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NyctoNeoForge.MOD_ID);
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, NyctoNeoForge.MOD_ID);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, NyctoNeoForge.MOD_ID);
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, NyctoNeoForge.MOD_ID);
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, NyctoNeoForge.MOD_ID);
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, NyctoNeoForge.MOD_ID);
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, NyctoNeoForge.MOD_ID);
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, NyctoNeoForge.MOD_ID);
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, NyctoNeoForge.MOD_ID);
	public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, NyctoNeoForge.MOD_ID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NyctoNeoForge.MOD_ID);
	public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, NyctoNeoForge.MOD_ID);

	private ModRegistration() {
	}

	public static void register(IEventBus bus) {
		ATTRIBUTES.register(bus);
		BLOCKS.register(bus);
		BLOCK_ENTITY_TYPES.register(bus);
		ENTITY_TYPES.register(bus);
		ENTITY_DATA_SERIALIZERS.register(bus);
		ITEMS.register(bus);
		MENU_TYPES.register(bus);
		MOB_EFFECTS.register(bus);
		POTIONS.register(bus);
		PARTICLE_TYPES.register(bus);
		RECIPE_SERIALIZERS.register(bus);
		SOUND_EVENTS.register(bus);
		CREATIVE_MODE_TABS.register(bus);

		bus.addListener(ModEntityTypes::registerAttributes);
		bus.addListener(ModEntityTypes::registerSpawnPlacements);
		bus.addListener(ModItems::addCreativeTabContents);
		bus.addListener(ModPotions::registerBrewingRecipes);
	}

	public static ResourceLocation id(String name) {
		return ResourceLocation.fromNamespaceAndPath(NyctoNeoForge.MOD_ID, name);
	}

	public static <T> void register(DeferredRegister<T> registry, String name, T value) {
		registry.register(name, () -> value);
	}

	public static <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registry, String name) {
		return ResourceKey.create(registry, id(name));
	}
}
