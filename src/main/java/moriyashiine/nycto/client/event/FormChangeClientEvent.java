/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.event;

import moriyashiine.nycto.client.renderer.entity.model.DarkFormModel;
import moriyashiine.nycto.common.world.entity.monster.DarkForm;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ambient.BatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;

public class FormChangeClientEvent implements ClientTickEvents.EndTick {
	public static BatModel batModel = null;
	public static DarkFormModel darkFormModel = null;

	@Override
	public void onEndTick(Minecraft client) {
		if (client.player != null) {
			LivingEntity replacement = SLibUtils.getModelReplacement(client.player);
			if (replacement instanceof DarkForm) {
				darkFormModel = new DarkFormModel(client.getEntityModels().bakeLayer(DarkFormModel.LAYER));
			} else {
				darkFormModel = null;
			}
			if (replacement instanceof Bat) {
				batModel = new BatModel(client.getEntityModels().bakeLayer(ModelLayers.BAT));
			} else {
				batModel = null;
			}
		} else {
			batModel = null;
			darkFormModel = null;
		}
	}
}
