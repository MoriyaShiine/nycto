/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.datagen.provider;

import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.common.Nycto;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class NyctoEquipmentAssetProvider implements DataProvider {
	private final PackOutput.PathProvider pathProvider;

	public NyctoEquipmentAssetProvider(FabricPackOutput output) {
		pathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "equipment");
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> equipmentAssets = new HashMap<>();
		NyctoRegistries.HUNTER_TYPE.forEach(type -> equipmentAssets.put(type.assetKey, EquipmentClientInfo.builder()
				.addLayers(EquipmentClientInfo.LayerType.WOLF_BODY, EquipmentClientInfo.Layer.leatherDyeable(type.assetKey.identifier(), false))
				.build()));
		return DataProvider.saveAll(cache, EquipmentClientInfo.CODEC, pathProvider::json, equipmentAssets);
	}

	@Override
	public String getName() {
		return Nycto.MOD_ID + "_equipment_assets";
	}
}
