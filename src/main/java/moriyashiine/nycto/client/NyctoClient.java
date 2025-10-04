/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client;

import moriyashiine.heartymeals.api.event.DisableHudRepositioningEvent;
import moriyashiine.nycto.client.event.*;
import moriyashiine.nycto.client.event.integration.HeartyMealsEvent;
import moriyashiine.nycto.client.event.power.*;
import moriyashiine.nycto.client.gui.screen.ingame.VampireAltarScreen;
import moriyashiine.nycto.client.hud.PowerHotbarHudElement;
import moriyashiine.nycto.client.hud.SunExposureHudElement;
import moriyashiine.nycto.client.hud.VampireChargeJumpHudElement;
import moriyashiine.nycto.client.hud.VampireHudElement;
import moriyashiine.nycto.client.hud.power.CarnageHudElement;
import moriyashiine.nycto.client.hud.power.KeenSensesHudElement;
import moriyashiine.nycto.client.particle.BatSwarmParticle;
import moriyashiine.nycto.client.particle.BloodParticle;
import moriyashiine.nycto.client.particle.HypnotizedParticle;
import moriyashiine.nycto.client.particle.SmallSpellParticle;
import moriyashiine.nycto.client.payload.*;
import moriyashiine.nycto.client.render.armor.HunterArmorRenderer;
import moriyashiine.nycto.client.render.armor.VampireArmorRenderer;
import moriyashiine.nycto.client.render.armor.model.HunterArmorModel;
import moriyashiine.nycto.client.render.armor.model.ThralledHorseHornsModel;
import moriyashiine.nycto.client.render.armor.model.VampireArmorModel;
import moriyashiine.nycto.client.render.entity.*;
import moriyashiine.nycto.client.render.entity.feature.BloodrushAuraFeatureRenderer;
import moriyashiine.nycto.client.render.entity.feature.carnage.BatCarnageAuraFeatureRenderer;
import moriyashiine.nycto.client.render.entity.feature.carnage.DarkFormCarnageAuraFeatureRenderer;
import moriyashiine.nycto.client.render.entity.feature.carnage.PlayerCarnageAuraFeatureRenderer;
import moriyashiine.nycto.client.render.entity.feature.carnage.VampireCarnageAuraFeatureRenderer;
import moriyashiine.nycto.client.render.entity.model.BloodBarrierModel;
import moriyashiine.nycto.client.render.entity.model.DarkFormEntityModel;
import moriyashiine.nycto.client.render.entity.model.HunterEntityModel;
import moriyashiine.nycto.client.render.entity.model.VampireEntityModel;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.*;
import moriyashiine.strawberrylib.api.event.client.DisableHudBarEvent;
import moriyashiine.strawberrylib.api.event.client.ModifyNightVisionStrengthEvent;
import moriyashiine.strawberrylib.api.event.client.OutlineEntityEvent;
import moriyashiine.strawberrylib.api.event.client.ReplaceHeartTexturesEvent;
import moriyashiine.strawberrylib.api.registry.client.particle.AnchoredParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.option.StickyKeyBinding;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.BatEntityModel;
import net.minecraft.client.render.entity.model.ModelTransformer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class NyctoClient implements ClientModInitializer {
	private static final KeyBinding.Category KEY_CATEGORY = KeyBinding.Category.create(Nycto.id(Nycto.MOD_ID));
	private static final String POWER_HOTBAR_TRANSLATION_KEY = "key." + Nycto.MOD_ID + ".power_hotbar";

	public static final SimpleOption<Boolean> POWER_HOTBAR_TOGGLED = new SimpleOption<>(POWER_HOTBAR_TRANSLATION_KEY, SimpleOption.emptyTooltip(), (optionText, value) -> value ? Text.translatable("options.key.toggle") : Text.translatable("options.key.hold"), SimpleOption.BOOLEAN, false, value -> {
	});
	public static final KeyBinding POWER_HOTBAR_KEYBINDING = KeyBindingHelper.registerKeyBinding(new StickyKeyBinding(POWER_HOTBAR_TRANSLATION_KEY, GLFW.GLFW_KEY_R, KEY_CATEGORY, POWER_HOTBAR_TOGGLED::getValue, true));

	@Override
	public void onInitializeClient() {
		initBlocks();
		initItems();
		initEntities();
		initParticles();
		initScreens();
		initEvents();
		initPayloads();
	}

	private void initBlocks() {
		BlockRenderLayerMap.putBlock(ModBlocks.VAMPIRE_ALTAR, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.WEREWOLF_ALTAR, BlockRenderLayer.TRANSLUCENT);
		BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT, ModBlocks.WILD_GARLIC, ModBlocks.WILD_ACONITE);
		BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT, ModBlocks.GARLIC_WREATH, ModBlocks.ACONITE_GARLAND);
		BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT, ModBlocks.GARLIC, ModBlocks.ACONITE);
		BlockRenderLayerMap.putBlock(ModBlocks.FIREBOMB, BlockRenderLayer.CUTOUT);
	}

	private void initItems() {
		ArmorRenderer.register(new VampireArmorRenderer(), ModItems.VAMPIRE_HELMET, ModItems.VAMPIRE_CHESTPLATE, ModItems.VAMPIRE_LEGGINGS, ModItems.VAMPIRE_BOOTS);
		ArmorRenderer.register(new HunterArmorRenderer(Nycto.id("textures/entity/equipment/vampire_hunter_armor.png")), ModItems.VAMPIRE_HUNTER_HELMET, ModItems.VAMPIRE_HUNTER_CHESTPLATE, ModItems.VAMPIRE_HUNTER_LEGGINGS, ModItems.VAMPIRE_HUNTER_BOOTS);
		ArmorRenderer.register(new HunterArmorRenderer(Nycto.id("textures/entity/equipment/werewolf_hunter_armor.png")), ModItems.WEREWOLF_HUNTER_HELMET, ModItems.WEREWOLF_HUNTER_CHESTPLATE, ModItems.WEREWOLF_HUNTER_LEGGINGS, ModItems.WEREWOLF_HUNTER_BOOTS);
	}

	private void initEntities() {
		EntityRendererFactories.register(ModEntityTypes.WOODEN_STAKE, WoodenStakeEntityRenderer::new);
		EntityRendererFactories.register(ModEntityTypes.ACONITE_ARROW, AconiteArrowEntityRenderer::new);
		EntityRendererFactories.register(ModEntityTypes.FIREBOMB, FlyingItemEntityRenderer::new);
		EntityRendererFactories.register(ModEntityTypes.BLOOD_FLECHETTE, BloodFlechetteEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(BloodrushAuraFeatureRenderer.LAYER, () -> TexturedModelData.of(PlayerEntityModel.getTexturedModelData(new Dilation(1), false), 64, 64));
		EntityModelLayerRegistry.registerModelLayer(BloodrushAuraFeatureRenderer.LAYER_SLIM, () -> TexturedModelData.of(PlayerEntityModel.getTexturedModelData(new Dilation(1), true), 64, 64));
		EntityModelLayerRegistry.registerModelLayer(BloodBarrierModel.LAYER, BloodBarrierModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(PlayerCarnageAuraFeatureRenderer.LAYER, () -> TexturedModelData.of(PlayerEntityModel.getTexturedModelData(new Dilation(1), false), 64, 64));
		EntityModelLayerRegistry.registerModelLayer(PlayerCarnageAuraFeatureRenderer.LAYER_SLIM, () -> TexturedModelData.of(PlayerEntityModel.getTexturedModelData(new Dilation(1), true), 64, 64));
		EntityModelLayerRegistry.registerModelLayer(BatCarnageAuraFeatureRenderer.LAYER, BatEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(DarkFormCarnageAuraFeatureRenderer.LAYER, DarkFormEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(VampireCarnageAuraFeatureRenderer.LAYER, VampireEntityModel::getTexturedModelData);

		EntityModelLayerRegistry.registerModelLayer(VampireEntityModel.LAYER, VampireEntityModel::getTexturedModelData);
		EntityRendererFactories.register(ModEntityTypes.VAMPIRE, VampireEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(HunterEntityModel.LAYER, HunterEntityModel::getHunterTexturedModelData);
		EntityRendererFactories.register(ModEntityTypes.HUNTER, HunterEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(DarkFormEntityModel.LAYER, DarkFormEntityModel::getTexturedModelData);
		EntityRendererFactories.register(ModEntityTypes.DARK_FORM, DarkFormEntityRenderer::new);

		EntityModelLayerRegistry.registerModelLayer(VampireArmorModel.MODEL_LAYER, VampireArmorModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(HunterArmorModel.MODEL_LAYER, HunterArmorModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ThralledHorseHornsModel.MODEL_LAYER, () -> ThralledHorseHornsModel.getTexturedModelData().transform(ModelTransformer.scaling(1.1F)));
	}

	private void initParticles() {
		ParticleFactoryRegistry.getInstance().register(ModParticleTypes.BLOOD, BloodParticle.Factory::new);

		ParticleFactoryRegistry.getInstance().register(ModParticleTypes.BAT_SWARM_CENTER, BatSwarmParticle.BatSwarmFactory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticleTypes.BAT_SWARM_LEFT, BatSwarmParticle.BatSwarmFactory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticleTypes.BAT_SWARM_RIGHT, BatSwarmParticle.BatSwarmFactory::new);

		ParticleFactoryRegistry.getInstance().register(ModParticleTypes.BATSTEP_CENTER, BatSwarmParticle.BatstepFactory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticleTypes.BATSTEP_LEFT, BatSwarmParticle.BatstepFactory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticleTypes.BATSTEP_RIGHT, BatSwarmParticle.BatstepFactory::new);

		ParticleFactoryRegistry.getInstance().register(ModParticleTypes.HYPNOSIS_INDICATOR, AnchoredParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticleTypes.HYPNOSIS_SMALL, SmallSpellParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticleTypes.HYPNOSIS_STAR, SpellParticle.DefaultFactory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticleTypes.HYPNOTIZED, HypnotizedParticle.Factory::new);

		ParticleFactoryRegistry.getInstance().register(ModParticleTypes.THRALLED, HypnotizedParticle.Factory::new);
	}

	private void initScreens() {
		HandledScreens.register(ModScreenHandlerTypes.VAMPIRE_ALTAR, VampireAltarScreen::new);
	}

	private void initEvents() {
		// internal
		ClientTickEvents.END_CLIENT_TICK.register(new FormChangeClientEvent());
		ReplaceHeartTexturesEvent.EVENT.register(new HealBlockClientEvent());
		ItemTooltipCallback.EVENT.register(new ItemDescriptionsEvent());
		UseBlockCallback.EVENT.register(new PowerClientEvent.UseBlock());
		UseEntityCallback.EVENT.register(new PowerClientEvent.UseEntity());
		UseItemCallback.EVENT.register(new PowerClientEvent.UseItem());
		ClientTickEvents.END_WORLD_TICK.register(new PowerClientEvent.Tick());
		// vampire
		DisableHudBarEvent.EVENT.register(new VampireClientEvent());
		// power
		ModifyNightVisionStrengthEvent.ADD.register(new NightVisionEvent());
		OutlineEntityEvent.EVENT.register(new BloodFlechettesClientEvent());
		ClientTickEvents.END_WORLD_TICK.register(new KeenSensesClientEvent.Tick());
		OutlineEntityEvent.EVENT.register(new KeenSensesClientEvent.Outline());
		ClientTickEvents.END_WORLD_TICK.register(new HypnotizeClientEvent.Tick());
		OutlineEntityEvent.EVENT.register(new HypnotizeClientEvent.Outline());
		ClientTickEvents.END_WORLD_TICK.register(new VampiricThrallClientEvent.Tick());
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

	private void initPayloads() {
		ClientPlayNetworking.registerGlobalReceiver(SyncTruncatedWorldSeedPayload.ID, new SyncTruncatedWorldSeedPayload.Receiver());
		ClientPlayNetworking.registerGlobalReceiver(ModifyPowerPayload.ID, new ModifyPowerPayload.Receiver());
		ClientPlayNetworking.registerGlobalReceiver(SetTransformationPayload.ID, new SetTransformationPayload.Receiver());
		ClientPlayNetworking.registerGlobalReceiver(SetPowerCooldownPayload.ID, new SetPowerCooldownPayload.Receiver());
		ClientPlayNetworking.registerGlobalReceiver(AddBloodBarrierParticlesPayload.ID, new AddBloodBarrierParticlesPayload.Receiver());
		ClientPlayNetworking.registerGlobalReceiver(PlayBloodrushSoundPayload.ID, new PlayBloodrushSoundPayload.Receiver());
	}
}
