/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.event;

import moriyashiine.nycto.client.render.entity.model.DarkFormEntityModel;
import moriyashiine.nycto.common.entity.mob.DarkFormEntity;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.model.BatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BatEntity;
import org.jetbrains.annotations.Nullable;

public class FormChangeClientEvent implements ClientTickEvents.EndTick {
	public static BatEntityModel batModel = null;
	public static DarkFormEntityModel darkFormModel = null;

	@Override
	public void onEndTick(MinecraftClient client) {
		if (client.player != null) {
			@Nullable LivingEntity replacement = SLibUtils.getModelReplacement(client.player);
			if (replacement instanceof DarkFormEntity) {
				darkFormModel = new DarkFormEntityModel(client.getLoadedEntityModels().getModelPart(DarkFormEntityModel.LAYER));
			} else {
				darkFormModel = null;
			}
			if (replacement instanceof BatEntity) {
				batModel = new BatEntityModel(client.getLoadedEntityModels().getModelPart(EntityModelLayers.BAT));
			} else {
				batModel = null;
			}
		} else {
			batModel = null;
			darkFormModel = null;
		}
	}
}
