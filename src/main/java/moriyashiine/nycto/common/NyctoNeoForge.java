/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common;

import moriyashiine.nycto.neoforge.NyctoGameplay;
import moriyashiine.nycto.neoforge.NyctoItems;
import moriyashiine.nycto.neoforge.event.VampireBloodEvents;
import moriyashiine.nycto.neoforge.event.VampireLifecycleEvents;
import moriyashiine.nycto.neoforge.network.NyctoPayloads;
import moriyashiine.nycto.neoforge.power.NyctoPowers;
import moriyashiine.nycto.neoforge.registry.NyctoBlocks;
import moriyashiine.nycto.neoforge.registry.NyctoArmorMaterials;
import moriyashiine.nycto.neoforge.registry.NyctoEntityTypes;
import moriyashiine.nycto.neoforge.registry.NyctoHunterContent;
import moriyashiine.nycto.neoforge.registry.NyctoMenuTypes;
import moriyashiine.nycto.neoforge.registry.NyctoMobEffects;
import moriyashiine.nycto.neoforge.registry.NyctoParticleTypes;
import moriyashiine.nycto.neoforge.registry.NyctoPotions;
import moriyashiine.nycto.neoforge.registry.NyctoRecipeSerializers;
import moriyashiine.nycto.neoforge.registry.NyctoSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(NyctoNeoForge.MOD_ID)
public final class NyctoNeoForge {
    public static final String MOD_ID = "nycto";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public NyctoNeoForge(IEventBus modEventBus) {
        NyctoArmorMaterials.register(modEventBus);
        NyctoBlocks.register(modEventBus);
        NyctoEntityTypes.register(modEventBus);
        NyctoHunterContent.register(modEventBus);
        NyctoItems.register(modEventBus);
        NyctoMenuTypes.register(modEventBus);
        NyctoMobEffects.register(modEventBus);
        NyctoParticleTypes.register(modEventBus);
        NyctoPotions.register(modEventBus);
        NyctoRecipeSerializers.register(modEventBus);
        NyctoSoundEvents.register(modEventBus);

        modEventBus.addListener(NyctoItems::addCreativeTabContents);
        modEventBus.addListener(NyctoHunterContent::addCreativeTabContents);
        modEventBus.addListener(NyctoEntityTypes::registerAttributes);
        modEventBus.addListener(NyctoEntityTypes::registerSpawnPlacements);
        modEventBus.addListener(NyctoHunterContent::registerEntityAttributes);
        modEventBus.addListener(NyctoPayloads::register);

        NyctoPowers.all();
        NyctoHunterContent.registerEvents();

        NeoForge.EVENT_BUS.addListener(NyctoGameplay::registerCommands);
        NeoForge.EVENT_BUS.addListener(EventPriority.NORMAL, NyctoGameplay::onPlayerTick);
        NeoForge.EVENT_BUS.addListener(NyctoGameplay::clonePlayer);
        NeoForge.EVENT_BUS.addListener(NyctoGameplay::onPlayerLoggedIn);
        NeoForge.EVENT_BUS.addListener(NyctoGameplay::onPlayerChangedDimension);
        NeoForge.EVENT_BUS.addListener(NyctoGameplay::onPlayerRespawn);
        NeoForge.EVENT_BUS.addListener(VampireBloodEvents::onLivingDeath);
        NeoForge.EVENT_BUS.addListener(VampireBloodEvents::onEntityTick);
        NeoForge.EVENT_BUS.addListener(VampireLifecycleEvents::onPlayerTick);
        NeoForge.EVENT_BUS.addListener(VampireLifecycleEvents::onEntityInteract);
        NeoForge.EVENT_BUS.addListener(VampireLifecycleEvents::onEntityInteractSpecific);
        LOGGER.info("Loaded Nycto NeoForge");
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
