/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client;

import eu.midnightdust.lib.config.MidnightConfig;
import moriyashiine.heartymeals.api.event.DisableHudRepositioningEvent;
import moriyashiine.nycto.api.NyctoClientAPI;
import moriyashiine.nycto.client.event.*;
import moriyashiine.nycto.client.event.integration.HeartyMealsEvent;
import moriyashiine.nycto.client.event.power.*;
import moriyashiine.nycto.client.gui.hud.PowerHotbarHudElement;
import moriyashiine.nycto.client.gui.hud.SunExposureHudElement;
import moriyashiine.nycto.client.gui.hud.VampireHudElement;
import moriyashiine.nycto.client.gui.hud.power.CarnageHudElement;
import moriyashiine.nycto.client.gui.hud.power.KeenSensesHudElement;
import moriyashiine.nycto.client.gui.screens.inventory.VampireAltarScreen;
import moriyashiine.nycto.client.particle.BatSwarmParticle;
import moriyashiine.nycto.client.particle.BloodParticle;
import moriyashiine.nycto.client.particle.HypnotizedParticle;
import moriyashiine.nycto.client.particle.SmallSpellParticle;
import moriyashiine.nycto.client.renderer.entity.*;
import moriyashiine.nycto.client.renderer.entity.armor.model.HunterArmorModel;
import moriyashiine.nycto.client.renderer.entity.armor.model.ThralledHorseHornsModel;
import moriyashiine.nycto.client.renderer.entity.armor.model.VampireArmorModel;
import moriyashiine.nycto.client.renderer.entity.layers.BloodrushAuraLayer;
import moriyashiine.nycto.client.renderer.entity.layers.carnage.BatCarnageAuraLayer;
import moriyashiine.nycto.client.renderer.entity.layers.carnage.DarkFormCarnageAuraLayer;
import moriyashiine.nycto.client.renderer.entity.layers.carnage.PlayerCarnageAuraLayer;
import moriyashiine.nycto.client.renderer.entity.layers.carnage.VampireCarnageAuraLayer;
import moriyashiine.nycto.client.renderer.entity.model.*;
import moriyashiine.nycto.client.renderer.entity.vampiricthrall.HorseVampiricThrallRenderer;
import moriyashiine.nycto.client.renderer.entity.vampiricthrall.VexVampiricThrallRenderer;
import moriyashiine.nycto.client.renderer.entity.vampiricthrall.WolfVampiricThrallRenderer;
import moriyashiine.nycto.client.renderer.item.properties.conditional.FullDaggerProperty;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.*;
import moriyashiine.strawberrylib.api.event.TickEntityEvent;
import moriyashiine.strawberrylib.api.event.client.AddNightVisionScaleEvent;
import moriyashiine.strawberrylib.api.event.client.OutlineEntityEvent;
import moriyashiine.strawberrylib.api.event.client.ReplaceContextualInfoEvent;
import moriyashiine.strawberrylib.api.event.client.ReplaceHeartTexturesEvent;
import moriyashiine.strawberrylib.api.registry.client.particle.AnchoredParticle;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.ToggleKeyMapping;
import net.minecraft.client.model.ambient.BatModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterConditionalItemModelPropertyEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = Nycto.MOD_ID, value = Dist.CLIENT)
public final class NyctoClient {
	private static final KeyMapping.Category KEYMAPPING_CATEGORY = KeyMapping.Category.register(Nycto.id(Nycto.MOD_ID));
	private static final String POWER_HOTBAR_NAME = "key." + Nycto.MOD_ID + ".power_hotbar";

	public static final OptionInstance<Boolean> POWER_HOTBAR_TOGGLED = new OptionInstance<>(POWER_HOTBAR_NAME, OptionInstance.noTooltip(), (caption, value) -> value ? Component.translatable("options.key.toggle") : Component.translatable("options.key.hold"), OptionInstance.BOOLEAN_VALUES, false, value -> {
	});
	public static final KeyMapping POWER_HOTBAR_KEYMAPPING = new ToggleKeyMapping(POWER_HOTBAR_NAME, GLFW.GLFW_KEY_R, KEYMAPPING_CATEGORY, POWER_HOTBAR_TOGGLED::get, true);

	private NyctoClient() {
	}

	@SubscribeEvent
	public static void setup(FMLClientSetupEvent event) {
		MidnightConfig.init(Nycto.MOD_ID, ModConfig.class);
		initEvents();
	}

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(POWER_HOTBAR_KEYMAPPING);
	}

	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModEntityTypes.WOODEN_STAKE, WoodenStakeRenderer::new);
		event.registerEntityRenderer(ModEntityTypes.ACONITE_ARROW, AconiteArrowRenderer::new);
		event.registerEntityRenderer(ModEntityTypes.FIREBOMB, ThrownItemRenderer::new);
		event.registerEntityRenderer(ModEntityTypes.BLOOD_FLECHETTE, BloodFlechetteRenderer::new);
		event.registerEntityRenderer(ModEntityTypes.VAMPIRE, VampireRenderer::new);
		event.registerEntityRenderer(ModEntityTypes.HUNTER, HunterRenderer::new);
		event.registerEntityRenderer(ModEntityTypes.DARK_FORM, DarkFormRenderer::new);
	}

	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(BloodrushAuraLayer.LAYER, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(1), false), 64, 64));
		event.registerLayerDefinition(BloodrushAuraLayer.LAYER_SLIM, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(1), true), 64, 64));
		event.registerLayerDefinition(BloodBarrierModel.LAYER, BloodBarrierModel::createBodyLayer);
		event.registerLayerDefinition(PlayerCarnageAuraLayer.LAYER, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(1), false), 64, 64));
		event.registerLayerDefinition(PlayerCarnageAuraLayer.LAYER_SLIM, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(1), true), 64, 64));
		event.registerLayerDefinition(BatCarnageAuraLayer.LAYER, BatModel::createBodyLayer);
		event.registerLayerDefinition(DarkFormCarnageAuraLayer.LAYER, DarkFormModel::createBodyLayer);
		event.registerLayerDefinition(VampireCarnageAuraLayer.LAYER, VampireModel::createBodyLayer);
		event.registerLayerDefinition(VampireModel.LAYER, VampireModel::createBodyLayer);
		event.registerLayerDefinition(HunterModel.LAYER, HunterModel::createHunterBodyLayer);
		event.registerLayerDefinition(DarkFormModel.LAYER, DarkFormModel::createBodyLayer);

		registerArmorLayerDefinitions(event, VampireArmorModel.MODEL_LAYERS, VampireArmorModel.createArmorMeshSet());
		registerArmorLayerDefinitions(event, HunterArmorModel.MODEL_LAYERS, HunterArmorModel.createArmorMeshSet());
		event.registerLayerDefinition(ThralledHorseHornsModel.MODEL_LAYER, () -> ThralledHorseHornsModel.createBodyLayer().apply(MeshTransformer.scaling(1.1F)));
		event.registerLayerDefinition(WolfHunterArmorModel.VAMPIRE_HUNTER_LAYER, WolfHunterArmorModel::createVampireHunterBodyLayer);
		event.registerLayerDefinition(WolfHunterArmorModel.WEREWOLF_HUNTER_LAYER, WolfHunterArmorModel::createWerewolfHunterBodyLayer);

		NyctoClientAPI.registerVampiricThrallRenderer(EntityType.HORSE, new HorseVampiricThrallRenderer());
		NyctoClientAPI.registerVampiricThrallRenderer(EntityType.VEX, new VexVampiricThrallRenderer());
		NyctoClientAPI.registerVampiricThrallRenderer(EntityType.WOLF, new WolfVampiricThrallRenderer());
		NyctoClientAPI.registerHunterTypeWolfArmorModelLayer(ModHunterTypes.VAMPIRE, WolfHunterArmorModel.VAMPIRE_HUNTER_LAYER);
		NyctoClientAPI.registerHunterTypeWolfArmorModelLayer(ModHunterTypes.WEREWOLF, WolfHunterArmorModel.WEREWOLF_HUNTER_LAYER);
	}

	@SubscribeEvent
	public static void registerParticles(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(ModParticleTypes.AMBROSIA, BloodParticle.Provider::new);
		event.registerSpriteSet(ModParticleTypes.BLOOD, BloodParticle.Provider::new);

		event.registerSpriteSet(ModParticleTypes.BAT_SWARM_CENTER, BatSwarmParticle.BatSwarmProvider::new);
		event.registerSpriteSet(ModParticleTypes.BAT_SWARM_LEFT, BatSwarmParticle.BatSwarmProvider::new);
		event.registerSpriteSet(ModParticleTypes.BAT_SWARM_RIGHT, BatSwarmParticle.BatSwarmProvider::new);

		event.registerSpriteSet(ModParticleTypes.BATSTEP_CENTER, BatSwarmParticle.BatstepProvider::new);
		event.registerSpriteSet(ModParticleTypes.BATSTEP_LEFT, BatSwarmParticle.BatstepProvider::new);
		event.registerSpriteSet(ModParticleTypes.BATSTEP_RIGHT, BatSwarmParticle.BatstepProvider::new);

		event.registerSpriteSet(ModParticleTypes.HYPNOSIS_INDICATOR, AnchoredParticle.Provider::new);
		event.registerSpriteSet(ModParticleTypes.HYPNOSIS_INDICATOR_INVERSE, AnchoredParticle.Provider::new);
		event.registerSpriteSet(ModParticleTypes.HYPNOSIS_SMALL, SmallSpellParticle.Provider::new);
		event.registerSpriteSet(ModParticleTypes.HYPNOSIS_STAR, SpellParticle.Provider::new);
		event.registerSpriteSet(ModParticleTypes.HYPNOTIZED, HypnotizedParticle.Provider::new);

		event.registerSpriteSet(ModParticleTypes.THRALLED, HypnotizedParticle.Provider::new);
	}

	@SubscribeEvent
	public static void registerScreens(RegisterMenuScreensEvent event) {
		event.register(ModMenuTypes.VAMPIRE_ALTAR, VampireAltarScreen::new);
	}

	@SubscribeEvent
	public static void registerHudLayers(RegisterGuiLayersEvent event) {
		event.registerAboveAll(Nycto.id("power_hotbar"), new PowerHotbarHudElement()::extractRenderState);
		event.registerAboveAll(Nycto.id("sun_exposure"), new SunExposureHudElement()::extractRenderState);
		event.registerAboveAll(Nycto.id("vampire"), new VampireHudElement()::extractRenderState);
		event.registerAboveAll(Nycto.id("carnage"), new CarnageHudElement()::extractRenderState);
		event.registerAboveAll(Nycto.id("keen_senses"), new KeenSensesHudElement()::extractRenderState);
	}

	@SubscribeEvent
	public static void registerConditionalItemModelProperties(RegisterConditionalItemModelPropertyEvent event) {
		event.register(Nycto.id("full_dagger"), FullDaggerProperty.MAP_CODEC);
	}

	private static void registerArmorLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event, net.minecraft.client.renderer.entity.ArmorModelSet<net.minecraft.client.model.geom.ModelLayerLocation> layers, net.minecraft.client.renderer.entity.ArmorModelSet<LayerDefinition> definitions) {
		event.registerLayerDefinition(layers.get(EquipmentSlot.HEAD), () -> definitions.get(EquipmentSlot.HEAD));
		event.registerLayerDefinition(layers.get(EquipmentSlot.CHEST), () -> definitions.get(EquipmentSlot.CHEST));
		event.registerLayerDefinition(layers.get(EquipmentSlot.LEGS), () -> definitions.get(EquipmentSlot.LEGS));
		event.registerLayerDefinition(layers.get(EquipmentSlot.FEET), () -> definitions.get(EquipmentSlot.FEET));
	}

	private static void initEvents() {
		ReplaceHeartTexturesEvent.EVENT.register(new HealBlockClientEvent());
		ReplaceContextualInfoEvent.EVENT.register(new VampireClientEvent());
		AddNightVisionScaleEvent.EVENT.register(new NightVisionEvent());
		OutlineEntityEvent.EVENT.register(new BloodFlechettesClientEvent());
		OutlineEntityEvent.EVENT.register(new KeenSensesClientEvent.Outline());
		TickEntityEvent.EVENT.register(new HypnotizeClientEvent.Tick());
		OutlineEntityEvent.EVENT.register(new HypnotizeClientEvent.Outline());
		TickEntityEvent.EVENT.register(new VampiricThrallClientEvent.Tick());
		OutlineEntityEvent.EVENT.register(new VampiricThrallClientEvent.Outline());
		if (ModList.get().isLoaded("heartymeals")) {
			DisableHudRepositioningEvent.EVENT.register(new HeartyMealsEvent());
		}
	}
}
