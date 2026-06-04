/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.client.gui.NyctoHudLayer;
import moriyashiine.nycto.neoforge.client.gui.VampireAltarScreen;
import moriyashiine.nycto.neoforge.client.model.BloodBarrierModel;
import moriyashiine.nycto.neoforge.client.model.DarkFormModel;
import moriyashiine.nycto.neoforge.client.model.HunterModel;
import moriyashiine.nycto.neoforge.client.model.NyctoArmorClientExtensions;
import moriyashiine.nycto.neoforge.client.model.NyctoHumanoidArmorModel;
import moriyashiine.nycto.neoforge.client.model.ThralledHorseHornsModel;
import moriyashiine.nycto.neoforge.client.model.VampireModel;
import moriyashiine.nycto.neoforge.client.particle.NyctoBatParticle;
import moriyashiine.nycto.neoforge.client.particle.NyctoSpriteParticle;
import moriyashiine.nycto.neoforge.client.renderer.AconiteArrowRenderer;
import moriyashiine.nycto.neoforge.client.renderer.BloodFlechetteRenderer;
import moriyashiine.nycto.neoforge.client.renderer.DarkFormRenderer;
import moriyashiine.nycto.neoforge.client.renderer.HunterRenderer;
import moriyashiine.nycto.neoforge.client.renderer.VampireRenderer;
import moriyashiine.nycto.neoforge.client.renderer.WoodenStakeRenderer;
import moriyashiine.nycto.neoforge.client.renderer.layer.BloodBarrierLayer;
import moriyashiine.nycto.neoforge.client.renderer.layer.BloodrushAuraLayer;
import moriyashiine.nycto.neoforge.client.renderer.layer.CarnageAuraLayer;
import moriyashiine.nycto.neoforge.client.renderer.layer.DarkFormCarnageAuraLayer;
import moriyashiine.nycto.neoforge.client.renderer.layer.ThralledHorseHornsLayer;
import moriyashiine.nycto.neoforge.client.renderer.layer.VampireCarnageAuraLayer;
import moriyashiine.nycto.neoforge.NyctoItems;
import moriyashiine.nycto.neoforge.registry.NyctoEntityTypes;
import moriyashiine.nycto.neoforge.registry.NyctoHunterContent;
import moriyashiine.nycto.neoforge.registry.NyctoMenuTypes;
import moriyashiine.nycto.neoforge.registry.NyctoParticleTypes;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = NyctoNeoForge.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class NyctoClientNeoForge {
	private static final String KEYMAPPING_CATEGORY = "key.category.nycto.nycto";
	private static final String POWER_HOTBAR_NAME = "key.nycto.power_hotbar";

	public static final KeyMapping POWER_HOTBAR_KEYMAPPING = new KeyMapping(POWER_HOTBAR_NAME, GLFW.GLFW_KEY_R, KEYMAPPING_CATEGORY);

	private static boolean registeredHudLayer = false;
	private static boolean registeredScreens = false;
	private static boolean registeredPowerHotbarKey = false;
	private static boolean registeredEntityRenderers = false;
	private static boolean registeredEntityLayers = false;
	private static boolean registeredLayerDefinitions = false;
	private static boolean registeredParticles = false;
	private static boolean registeredItemProperties = false;
	private static boolean registeredClientExtensions = false;
	private static boolean registeredItemColors = false;

	private NyctoClientNeoForge() {
	}

	public static void register(IEventBus modEventBus) {
		modEventBus.addListener(NyctoClientNeoForge::registerGuiLayers);
		modEventBus.addListener(NyctoClientNeoForge::registerScreens);
		modEventBus.addListener(NyctoClientNeoForge::registerKeyMappings);
		modEventBus.addListener(NyctoClientNeoForge::registerEntityRenderers);
		modEventBus.addListener(NyctoClientNeoForge::registerLayerDefinitions);
		modEventBus.addListener(NyctoClientNeoForge::addEntityLayers);
		modEventBus.addListener(NyctoClientNeoForge::registerParticles);
		modEventBus.addListener(NyctoClientNeoForge::registerItemProperties);
		modEventBus.addListener(NyctoClientNeoForge::registerItemColors);
		modEventBus.addListener(NyctoClientNeoForge::registerClientExtensions);
	}

	@SubscribeEvent
	public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
		if (registeredItemColors) {
			return;
		}
		event.register((stack, tintIndex) -> tintIndex == 0 ? 0xFF7F0000 : 0xFFFFFFFF, NyctoItems.BLOOD_BOTTLE.get());
		registeredItemColors = true;
	}

	@SubscribeEvent
	public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
		if (registeredClientExtensions) {
			return;
		}
		event.registerItem(NyctoArmorClientExtensions.vampire(),
				NyctoItems.VAMPIRE_HELMET.get(),
				NyctoItems.VAMPIRE_CHESTPLATE.get(),
				NyctoItems.VAMPIRE_LEGGINGS.get(),
				NyctoItems.VAMPIRE_BOOTS.get());
		event.registerItem(NyctoArmorClientExtensions.hunter(),
				NyctoHunterContent.VAMPIRE_HUNTER_HELMET.get(),
				NyctoHunterContent.VAMPIRE_HUNTER_CHESTPLATE.get(),
				NyctoHunterContent.VAMPIRE_HUNTER_LEGGINGS.get(),
				NyctoHunterContent.VAMPIRE_HUNTER_BOOTS.get());
		registeredClientExtensions = true;
	}

	@SubscribeEvent
	public static void registerItemProperties(FMLClientSetupEvent event) {
		if (registeredItemProperties) {
			return;
		}
		event.enqueueWork(() -> {
			ItemProperties.register(Items.CROSSBOW, NyctoNeoForge.id("wooden_stake"), (stack, level, entity, seed) -> stack.getOrDefault(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY).contains(NyctoItems.WOODEN_STAKE.get()) ? 1 : 0);
			ItemProperties.register(NyctoItems.VAMPIRIC_DAGGER.get(), NyctoNeoForge.id("full_dagger"), (stack, level, entity, seed) -> moriyashiine.nycto.neoforge.item.VampiricDaggerItem.isFull(stack) ? 1 : 0);
			ItemProperties.register(NyctoHunterContent.HALBERD.get(), NyctoNeoForge.id("held_halberd"), (stack, level, entity, seed) -> entity != null ? 1 : 0);
			ItemProperties.register(NyctoHunterContent.GARLIC_COATED_HALBERD.get(), NyctoNeoForge.id("held_halberd"), (stack, level, entity, seed) -> entity != null ? 1 : 0);
			ItemProperties.register(NyctoHunterContent.ACONITE_COATED_HALBERD.get(), NyctoNeoForge.id("held_halberd"), (stack, level, entity, seed) -> entity != null ? 1 : 0);
		});
		registeredItemProperties = true;
	}

	@SubscribeEvent
	public static void registerScreens(RegisterMenuScreensEvent event) {
		if (registeredScreens) {
			return;
		}
		event.register(NyctoMenuTypes.VAMPIRE_ALTAR.get(), VampireAltarScreen::new);
		registeredScreens = true;
	}

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		if (registeredPowerHotbarKey) {
			return;
		}
		event.register(POWER_HOTBAR_KEYMAPPING);
		registeredPowerHotbarKey = true;
	}

	@SubscribeEvent
	public static void registerGuiLayers(RegisterGuiLayersEvent event) {
		if (registeredHudLayer) {
			return;
		}
		event.registerAbove(VanillaGuiLayers.EXPERIENCE_LEVEL, NyctoNeoForge.id("vampire_hud"), NyctoHudLayer::render);
		registeredHudLayer = true;
	}

	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		if (registeredEntityRenderers) {
			return;
		}
		event.registerEntityRenderer(NyctoEntityTypes.VAMPIRE.get(), VampireRenderer::new);
		event.registerEntityRenderer(NyctoEntityTypes.DARK_FORM.get(), DarkFormRenderer::new);
		event.registerEntityRenderer(NyctoEntityTypes.WOODEN_STAKE.get(), WoodenStakeRenderer::new);
		event.registerEntityRenderer(NyctoEntityTypes.ACONITE_ARROW.get(), AconiteArrowRenderer::new);
		event.registerEntityRenderer(NyctoEntityTypes.FIREBOMB.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(NyctoEntityTypes.BLOOD_FLECHETTE.get(), BloodFlechetteRenderer::new);
		event.registerEntityRenderer(NyctoHunterContent.HUNTER.get(), HunterRenderer::new);
		registeredEntityRenderers = true;
	}

	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		if (registeredLayerDefinitions) {
			return;
		}
		event.registerLayerDefinition(BloodBarrierModel.LAYER, BloodBarrierModel::createBodyLayer);
		event.registerLayerDefinition(DarkFormModel.LAYER, DarkFormModel::createBodyLayer);
		event.registerLayerDefinition(HunterModel.LAYER, HunterModel::createBodyLayer);
		event.registerLayerDefinition(VampireModel.LAYER, VampireModel::createBodyLayer);
		event.registerLayerDefinition(BloodrushAuraLayer.LAYER, BloodrushAuraLayer::createBodyLayer);
		event.registerLayerDefinition(BloodrushAuraLayer.LAYER_SLIM, BloodrushAuraLayer::createSlimBodyLayer);
		event.registerLayerDefinition(CarnageAuraLayer.LAYER, CarnageAuraLayer::createBodyLayer);
		event.registerLayerDefinition(CarnageAuraLayer.LAYER_SLIM, CarnageAuraLayer::createSlimBodyLayer);
		event.registerLayerDefinition(DarkFormCarnageAuraLayer.LAYER, DarkFormModel::createBodyLayer);
		event.registerLayerDefinition(VampireCarnageAuraLayer.LAYER, VampireCarnageAuraLayer::createBodyLayer);
		event.registerLayerDefinition(ThralledHorseHornsModel.LAYER, ThralledHorseHornsModel::createBodyLayer);
		event.registerLayerDefinition(NyctoHumanoidArmorModel.VAMPIRE_HELMET, NyctoHumanoidArmorModel::createVampireHelmetLayer);
		event.registerLayerDefinition(NyctoHumanoidArmorModel.VAMPIRE_CHESTPLATE, NyctoHumanoidArmorModel::createVampireChestplateLayer);
		event.registerLayerDefinition(NyctoHumanoidArmorModel.VAMPIRE_LEGGINGS, NyctoHumanoidArmorModel::createVampireLeggingsLayer);
		event.registerLayerDefinition(NyctoHumanoidArmorModel.VAMPIRE_BOOTS, NyctoHumanoidArmorModel::createVampireBootsLayer);
		event.registerLayerDefinition(NyctoHumanoidArmorModel.HUNTER_HELMET, NyctoHumanoidArmorModel::createHunterHelmetLayer);
		event.registerLayerDefinition(NyctoHumanoidArmorModel.HUNTER_CHESTPLATE, NyctoHumanoidArmorModel::createHunterChestplateLayer);
		event.registerLayerDefinition(NyctoHumanoidArmorModel.HUNTER_LEGGINGS, NyctoHumanoidArmorModel::createHunterLeggingsLayer);
		event.registerLayerDefinition(NyctoHumanoidArmorModel.HUNTER_BOOTS, NyctoHumanoidArmorModel::createHunterBootsLayer);
		registeredLayerDefinitions = true;
	}

	@SubscribeEvent
	public static void addEntityLayers(EntityRenderersEvent.AddLayers event) {
		if (registeredEntityLayers) {
			return;
		}
		for (PlayerSkin.Model skin : event.getSkins()) {
			PlayerRenderer renderer = event.getSkin(skin);
			if (renderer != null) {
				renderer.addLayer(new BloodBarrierLayer<>(renderer, event.getEntityModels()));
				renderer.addLayer(new BloodrushAuraLayer(renderer, event.getEntityModels(), skin == PlayerSkin.Model.SLIM));
				renderer.addLayer(new CarnageAuraLayer(renderer, event.getEntityModels(), skin == PlayerSkin.Model.SLIM));
			}
		}
		VampireRenderer renderer = event.getRenderer(NyctoEntityTypes.VAMPIRE.get());
		if (renderer != null) {
			renderer.addLayer(new BloodBarrierLayer<>(renderer, event.getEntityModels()));
			renderer.addLayer(new VampireCarnageAuraLayer(renderer, event.getEntityModels()));
		}
		HorseRenderer horseRenderer = event.getRenderer(EntityType.HORSE);
		if (horseRenderer != null) {
			horseRenderer.addLayer(new ThralledHorseHornsLayer(horseRenderer, event.getEntityModels()));
		}
		registeredEntityLayers = true;
	}

	@SubscribeEvent
	public static void registerParticles(RegisterParticleProvidersEvent event) {
		if (registeredParticles) {
			return;
		}
		event.registerSpriteSet(NyctoParticleTypes.AMBROSIA.get(), sprites -> new NyctoSpriteParticle.Provider(sprites, NyctoSpriteParticle.ParticleStyle.ambrosia()));
		event.registerSpriteSet(NyctoParticleTypes.BLOOD.get(), sprites -> new NyctoSpriteParticle.Provider(sprites, NyctoSpriteParticle.ParticleStyle.blood()));
		event.registerSpriteSet(NyctoParticleTypes.BAT_SWARM_CENTER.get(), NyctoBatParticle.BatSwarmProvider::new);
		event.registerSpriteSet(NyctoParticleTypes.BAT_SWARM_LEFT.get(), NyctoBatParticle.BatSwarmProvider::new);
		event.registerSpriteSet(NyctoParticleTypes.BAT_SWARM_RIGHT.get(), NyctoBatParticle.BatSwarmProvider::new);
		event.registerSpriteSet(NyctoParticleTypes.BATSTEP_CENTER.get(), NyctoBatParticle.BatstepProvider::new);
		event.registerSpriteSet(NyctoParticleTypes.BATSTEP_LEFT.get(), NyctoBatParticle.BatstepProvider::new);
		event.registerSpriteSet(NyctoParticleTypes.BATSTEP_RIGHT.get(), NyctoBatParticle.BatstepProvider::new);
		event.registerSpriteSet(NyctoParticleTypes.HYPNOSIS_INDICATOR.get(), sprites -> new NyctoSpriteParticle.Provider(sprites, NyctoSpriteParticle.ParticleStyle.hypnosis()));
		event.registerSpriteSet(NyctoParticleTypes.HYPNOSIS_INDICATOR_INVERSE.get(), sprites -> new NyctoSpriteParticle.Provider(sprites, NyctoSpriteParticle.ParticleStyle.hypnosis()));
		event.registerSpriteSet(NyctoParticleTypes.HYPNOSIS_SMALL.get(), sprites -> new NyctoSpriteParticle.Provider(sprites, NyctoSpriteParticle.ParticleStyle.hypnosisSmall()));
		event.registerSpriteSet(NyctoParticleTypes.HYPNOSIS_STAR.get(), sprites -> new NyctoSpriteParticle.Provider(sprites, NyctoSpriteParticle.ParticleStyle.hypnosis()));
		event.registerSpriteSet(NyctoParticleTypes.HYPNOTIZED.get(), sprites -> new NyctoSpriteParticle.Provider(sprites, NyctoSpriteParticle.ParticleStyle.hypnosis()));
		event.registerSpriteSet(NyctoParticleTypes.THRALLED.get(), sprites -> new NyctoSpriteParticle.Provider(sprites, NyctoSpriteParticle.ParticleStyle.hypnosis()));
		registeredParticles = true;
	}
}
