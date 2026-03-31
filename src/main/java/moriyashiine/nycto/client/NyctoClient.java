/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client;

import eu.midnightdust.lib.config.MidnightConfig;
import moriyashiine.heartymeals.api.event.DisableHudRepositioningEvent;
import moriyashiine.nycto.client.event.*;
import moriyashiine.nycto.client.event.integration.HeartyMealsEvent;
import moriyashiine.nycto.client.event.power.*;
import moriyashiine.nycto.client.gui.hud.PowerHotbarHudElement;
import moriyashiine.nycto.client.gui.hud.SunExposureHudElement;
import moriyashiine.nycto.client.gui.hud.VampireChargeJumpHudElement;
import moriyashiine.nycto.client.gui.hud.VampireHudElement;
import moriyashiine.nycto.client.gui.hud.power.CarnageHudElement;
import moriyashiine.nycto.client.gui.hud.power.KeenSensesHudElement;
import moriyashiine.nycto.client.gui.screens.inventory.VampireAltarScreen;
import moriyashiine.nycto.client.particle.BatSwarmParticle;
import moriyashiine.nycto.client.particle.BloodParticle;
import moriyashiine.nycto.client.particle.HypnotizedParticle;
import moriyashiine.nycto.client.particle.SmallSpellParticle;
import moriyashiine.nycto.client.payload.*;
import moriyashiine.nycto.client.renderer.entity.*;
import moriyashiine.nycto.client.renderer.entity.armor.HunterArmorRenderer;
import moriyashiine.nycto.client.renderer.entity.armor.VampireArmorRenderer;
import moriyashiine.nycto.client.renderer.entity.armor.model.HunterArmorModel;
import moriyashiine.nycto.client.renderer.entity.armor.model.ThralledHorseHornsModel;
import moriyashiine.nycto.client.renderer.entity.armor.model.VampireArmorModel;
import moriyashiine.nycto.client.renderer.entity.layers.BloodrushAuraLayer;
import moriyashiine.nycto.client.renderer.entity.layers.carnage.BatCarnageAuraLayer;
import moriyashiine.nycto.client.renderer.entity.layers.carnage.DarkFormCarnageAuraLayer;
import moriyashiine.nycto.client.renderer.entity.layers.carnage.PlayerCarnageAuraLayer;
import moriyashiine.nycto.client.renderer.entity.layers.carnage.VampireCarnageAuraLayer;
import moriyashiine.nycto.client.renderer.entity.model.BloodBarrierModel;
import moriyashiine.nycto.client.renderer.entity.model.DarkFormModel;
import moriyashiine.nycto.client.renderer.entity.model.HunterModel;
import moriyashiine.nycto.client.renderer.entity.model.VampireModel;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModEntityTypes;
import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.init.ModMenuTypes;
import moriyashiine.nycto.common.init.ModParticleTypes;
import moriyashiine.strawberrylib.api.event.TickEntityEvent;
import moriyashiine.strawberrylib.api.event.client.AddNightVisionScaleEvent;
import moriyashiine.strawberrylib.api.event.client.DisableContextualInfoEvent;
import moriyashiine.strawberrylib.api.event.client.OutlineEntityEvent;
import moriyashiine.strawberrylib.api.event.client.ReplaceHeartTexturesEvent;
import moriyashiine.strawberrylib.api.registry.client.particle.AnchoredParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.ToggleKeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.ambient.BatModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import org.lwjgl.glfw.GLFW;

public class NyctoClient implements ClientModInitializer {
	private static final KeyMapping.Category KEYMAPPING_CATEGORY = KeyMapping.Category.register(Nycto.id(Nycto.MOD_ID));
	private static final String POWER_HOTBAR_NAME = "key." + Nycto.MOD_ID + ".power_hotbar";

	public static final OptionInstance<Boolean> POWER_HOTBAR_TOGGLED = new OptionInstance<>(POWER_HOTBAR_NAME, OptionInstance.noTooltip(), (_, value) -> value ? Component.translatable("options.key.toggle") : Component.translatable("options.key.hold"), OptionInstance.BOOLEAN_VALUES, false, _ -> {
	});
	public static final KeyMapping POWER_HOTBAR_KEYMAPPING = KeyMappingHelper.registerKeyMapping(new ToggleKeyMapping(POWER_HOTBAR_NAME, GLFW.GLFW_KEY_R, KEYMAPPING_CATEGORY, POWER_HOTBAR_TOGGLED::get, true));

	@Override
	public void onInitializeClient() {
		MidnightConfig.init(Nycto.MOD_ID, ModConfig.class);
		initItems();
		initEntities();
		initParticles();
		initScreens();
		initPayloads();
		initEvents();
	}

	private void initItems() {
		ArmorRenderer.register(context -> new VampireArmorRenderer(context, EquipmentSlot.HEAD), ModItems.VAMPIRE_HELMET);
		ArmorRenderer.register(context -> new VampireArmorRenderer(context, EquipmentSlot.CHEST), ModItems.VAMPIRE_CHESTPLATE);
		ArmorRenderer.register(context -> new VampireArmorRenderer(context, EquipmentSlot.LEGS), ModItems.VAMPIRE_LEGGINGS);
		ArmorRenderer.register(context -> new VampireArmorRenderer(context, EquipmentSlot.FEET), ModItems.VAMPIRE_BOOTS);

		Identifier vampireHunterArmorTexture = Nycto.id("textures/entity/equipment/humanoid/vampire_hunter.png");
		ArmorRenderer.register(context -> new HunterArmorRenderer(context, EquipmentSlot.HEAD, vampireHunterArmorTexture), ModItems.VAMPIRE_HUNTER_HELMET);
		ArmorRenderer.register(context -> new HunterArmorRenderer(context, EquipmentSlot.CHEST, vampireHunterArmorTexture), ModItems.VAMPIRE_HUNTER_CHESTPLATE);
		ArmorRenderer.register(context -> new HunterArmorRenderer(context, EquipmentSlot.LEGS, vampireHunterArmorTexture), ModItems.VAMPIRE_HUNTER_LEGGINGS);
		ArmorRenderer.register(context -> new HunterArmorRenderer(context, EquipmentSlot.FEET, vampireHunterArmorTexture), ModItems.VAMPIRE_HUNTER_BOOTS);

		Identifier werewolfHunterArmorTexture = Nycto.id("textures/entity/equipment/humanoid/werewolf_hunter.png");
		ArmorRenderer.register(context -> new HunterArmorRenderer(context, EquipmentSlot.HEAD, werewolfHunterArmorTexture), ModItems.WEREWOLF_HUNTER_HELMET);
		ArmorRenderer.register(context -> new HunterArmorRenderer(context, EquipmentSlot.CHEST, werewolfHunterArmorTexture), ModItems.WEREWOLF_HUNTER_CHESTPLATE);
		ArmorRenderer.register(context -> new HunterArmorRenderer(context, EquipmentSlot.LEGS, werewolfHunterArmorTexture), ModItems.WEREWOLF_HUNTER_LEGGINGS);
		ArmorRenderer.register(context -> new HunterArmorRenderer(context, EquipmentSlot.FEET, werewolfHunterArmorTexture), ModItems.WEREWOLF_HUNTER_BOOTS);
	}

	private void initEntities() {
		EntityRenderers.register(ModEntityTypes.WOODEN_STAKE, WoodenStakeRenderer::new);
		EntityRenderers.register(ModEntityTypes.ACONITE_ARROW, AconiteArrowRenderer::new);
		EntityRenderers.register(ModEntityTypes.FIREBOMB, ThrownItemRenderer::new);
		EntityRenderers.register(ModEntityTypes.BLOOD_FLECHETTE, BloodFlechetteRenderer::new);
		ModelLayerRegistry.registerModelLayer(BloodrushAuraLayer.LAYER, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(1), false), 64, 64));
		ModelLayerRegistry.registerModelLayer(BloodrushAuraLayer.LAYER_SLIM, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(1), true), 64, 64));
		ModelLayerRegistry.registerModelLayer(BloodBarrierModel.LAYER, BloodBarrierModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(PlayerCarnageAuraLayer.LAYER, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(1), false), 64, 64));
		ModelLayerRegistry.registerModelLayer(PlayerCarnageAuraLayer.LAYER_SLIM, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(1), true), 64, 64));
		ModelLayerRegistry.registerModelLayer(BatCarnageAuraLayer.LAYER, BatModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(DarkFormCarnageAuraLayer.LAYER, DarkFormModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(VampireCarnageAuraLayer.LAYER, VampireModel::createBodyLayer);

		ModelLayerRegistry.registerModelLayer(VampireModel.LAYER, VampireModel::createBodyLayer);
		EntityRenderers.register(ModEntityTypes.VAMPIRE, VampireRenderer::new);
		ModelLayerRegistry.registerModelLayer(HunterModel.LAYER, HunterModel::createHunterBodyLayer);
		EntityRenderers.register(ModEntityTypes.HUNTER, HunterRenderer::new);
		ModelLayerRegistry.registerModelLayer(DarkFormModel.LAYER, DarkFormModel::createBodyLayer);
		EntityRenderers.register(ModEntityTypes.DARK_FORM, DarkFormRenderer::new);

		ModelLayerRegistry.registerArmorModelLayers(VampireArmorModel.MODEL_LAYERS, VampireArmorModel::createArmorMeshSet);
		ModelLayerRegistry.registerArmorModelLayers(HunterArmorModel.MODEL_LAYERS, HunterArmorModel::createArmorMeshSet);
		ModelLayerRegistry.registerModelLayer(ThralledHorseHornsModel.MODEL_LAYER, () -> ThralledHorseHornsModel.createBodyLayer().apply(MeshTransformer.scaling(1.1F)));
	}

	private void initParticles() {
		ParticleProviderRegistry.getInstance().register(ModParticleTypes.BLOOD, BloodParticle.Provider::new);

		ParticleProviderRegistry.getInstance().register(ModParticleTypes.BAT_SWARM_CENTER, BatSwarmParticle.BatSwarmProvider::new);
		ParticleProviderRegistry.getInstance().register(ModParticleTypes.BAT_SWARM_LEFT, BatSwarmParticle.BatSwarmProvider::new);
		ParticleProviderRegistry.getInstance().register(ModParticleTypes.BAT_SWARM_RIGHT, BatSwarmParticle.BatSwarmProvider::new);

		ParticleProviderRegistry.getInstance().register(ModParticleTypes.BATSTEP_CENTER, BatSwarmParticle.BatstepProvider::new);
		ParticleProviderRegistry.getInstance().register(ModParticleTypes.BATSTEP_LEFT, BatSwarmParticle.BatstepProvider::new);
		ParticleProviderRegistry.getInstance().register(ModParticleTypes.BATSTEP_RIGHT, BatSwarmParticle.BatstepProvider::new);

		ParticleProviderRegistry.getInstance().register(ModParticleTypes.HYPNOSIS_INDICATOR, AnchoredParticle.Provider::new);
		ParticleProviderRegistry.getInstance().register(ModParticleTypes.HYPNOSIS_INDICATOR_INVERSE, AnchoredParticle.Provider::new);
		ParticleProviderRegistry.getInstance().register(ModParticleTypes.HYPNOSIS_SMALL, SmallSpellParticle.Provider::new);
		ParticleProviderRegistry.getInstance().register(ModParticleTypes.HYPNOSIS_STAR, SpellParticle.Provider::new);
		ParticleProviderRegistry.getInstance().register(ModParticleTypes.HYPNOTIZED, HypnotizedParticle.Provider::new);

		ParticleProviderRegistry.getInstance().register(ModParticleTypes.THRALLED, HypnotizedParticle.Provider::new);
	}

	private void initScreens() {
		MenuScreens.register(ModMenuTypes.VAMPIRE_ALTAR, VampireAltarScreen::new);
	}

	private void initPayloads() {
		ClientPlayNetworking.registerGlobalReceiver(SyncTruncatedWorldSeedPayload.TYPE, new SyncTruncatedWorldSeedPayload.Receiver());
		ClientPlayNetworking.registerGlobalReceiver(ModifyPowerPayload.TYPE, new ModifyPowerPayload.Receiver());
		ClientPlayNetworking.registerGlobalReceiver(SetTransformationPayload.TYPE, new SetTransformationPayload.Receiver());
		ClientPlayNetworking.registerGlobalReceiver(SetPowerCooldownPayload.TYPE, new SetPowerCooldownPayload.Receiver());
		ClientPlayNetworking.registerGlobalReceiver(AddBloodBarrierParticlesPayload.TYPE, new AddBloodBarrierParticlesPayload.Receiver());
		ClientPlayNetworking.registerGlobalReceiver(PlayBloodrushSoundPayload.TYPE, new PlayBloodrushSoundPayload.Receiver());
	}

	private void initEvents() {
		// internal
		ClientTickEvents.END_LEVEL_TICK.register(new ConfigSyncEvent());
		ClientTickEvents.END_CLIENT_TICK.register(new FormChangeClientEvent());
		ReplaceHeartTexturesEvent.EVENT.register(new HealBlockClientEvent());
		ItemTooltipCallback.EVENT.register(new ItemDescriptionsEvent());
		UseBlockCallback.EVENT.register(new PowerClientEvent.UseBlock());
		UseEntityCallback.EVENT.register(new PowerClientEvent.UseEntity());
		UseItemCallback.EVENT.register(new PowerClientEvent.UseItem());
		ClientTickEvents.END_LEVEL_TICK.register(new PowerClientEvent.Tick());
		LivingEntityFeatureRenderEvents.ALLOW_CAPE_RENDER.register(new ShowCapeEvent());
		// vampire
		DisableContextualInfoEvent.EVENT.register(new VampireClientEvent());
		// power
		AddNightVisionScaleEvent.EVENT.register(new NightVisionEvent());
		OutlineEntityEvent.EVENT.register(new BloodFlechettesClientEvent());
		ClientTickEvents.END_LEVEL_TICK.register(new KeenSensesClientEvent.Tick());
		OutlineEntityEvent.EVENT.register(new KeenSensesClientEvent.Outline());
		TickEntityEvent.EVENT.register(new HypnotizeClientEvent.Tick());
		OutlineEntityEvent.EVENT.register(new HypnotizeClientEvent.Outline());
		TickEntityEvent.EVENT.register(new VampiricThrallClientEvent.Tick());
		OutlineEntityEvent.EVENT.register(new VampiricThrallClientEvent.Outline());
		// integration
		if (FabricLoader.getInstance().isModLoaded("heartymeals")) {
			DisableHudRepositioningEvent.EVENT.register(new HeartyMealsEvent());
		}
		// hud elements
		HudElementRegistry.attachElementAfter(VanillaHudElements.HOTBAR, Nycto.id("power_hotbar"), new PowerHotbarHudElement());
		HudElementRegistry.attachElementAfter(VanillaHudElements.MISC_OVERLAYS, Nycto.id("sun_exposure"), new SunExposureHudElement());
		HudElementRegistry.attachElementAfter(VanillaHudElements.CROSSHAIR, Nycto.id("vampire"), new VampireHudElement());
		HudElementRegistry.attachElementAfter(VanillaHudElements.HOTBAR, Nycto.id("vampire_charge_jump"), new VampireChargeJumpHudElement());
		// power hud elements
		HudElementRegistry.attachElementAfter(VanillaHudElements.MISC_OVERLAYS, Nycto.id("carnage"), new CarnageHudElement());
		HudElementRegistry.attachElementAfter(VanillaHudElements.MISC_OVERLAYS, Nycto.id("keen_senses"), new KeenSensesHudElement());
	}
}
