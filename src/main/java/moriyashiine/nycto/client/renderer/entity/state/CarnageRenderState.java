/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.state;

import moriyashiine.nycto.common.component.entity.power.vampire.CarnageComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class CarnageRenderState {
	public static final RenderStateDataKey<CarnageRenderState> KEY = RenderStateDataKey.create(() -> "carnage");

	public float carnageOpacity = 0;

	public static <E extends LivingEntity, S extends LivingEntityRenderState> void extractRenderState(E entity, S state) {
		CarnageRenderState carnageRenderState = new CarnageRenderState();
		Entity realEntity = entity;
		if (entity instanceof Player) {
			for (AbstractClientPlayer player : Minecraft.getInstance().level.players()) {
				if (entity == SLibUtils.getModelReplacement(player)) {
					realEntity = player;
				}
			}
		}
		CarnageComponent carnageComponent = ModEntityComponents.CARNAGE.get(realEntity);
		carnageRenderState.carnageOpacity = carnageComponent.isActive() ? carnageComponent.getOverlayOpacity(0.5F) : 0;
		state.setData(KEY, carnageRenderState);
	}
}
