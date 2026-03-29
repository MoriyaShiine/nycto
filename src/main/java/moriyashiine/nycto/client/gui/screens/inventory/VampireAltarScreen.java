/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.gui.screens.inventory;

import moriyashiine.nycto.api.client.gui.screens.inventory.AltarScreen;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.world.inventory.VampireAltarMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class VampireAltarScreen extends AltarScreen<VampireAltarMenu> {
	private static final Identifier BACKGROUND_TEXTURE = Nycto.id("textures/gui/container/vampire_altar.png");
	private static final Identifier UNSELECTED_POWERS_TEXTURE = Nycto.id("container/vampire_altar/unselected_powers_frame");
	private static final Identifier UNSELECTED_WEAKNESSES_TEXTURE = Nycto.id("container/vampire_altar/unselected_weaknesses_frame");
	private static final Identifier CURRENT_POWERS_TEXTURE = Nycto.id("container/vampire_altar/current_powers_frame");
	private static final Identifier CURRENT_WEAKNESSES_TEXTURE = Nycto.id("container/vampire_altar/current_weaknesses_frame");
	private static final Identifier CHECKMARK_TEXTURE = Nycto.id("container/vampire_altar/checkmark");
	private static final Identifier CHECKMARK_HIGHLIGHTED_TEXTURE = Nycto.id("container/vampire_altar/checkmark_highlighted");
	private static final List<Identifier> CYCLING_ICONS = List.of(Nycto.id("container/slot/blood_bottle"), Nycto.id("container/slot/vampire_blood_bottle"));

	public VampireAltarScreen(VampireAltarMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
	}

	@Override
	protected Identifier getBackgroundTexture() {
		return BACKGROUND_TEXTURE;
	}

	@Override
	protected Identifier getUnselectedPowersTexture() {
		return UNSELECTED_POWERS_TEXTURE;
	}

	@Override
	protected Identifier getUnselectedWeaknessesTexture() {
		return UNSELECTED_WEAKNESSES_TEXTURE;
	}

	@Override
	protected Identifier getCurrentPowersTexture() {
		return CURRENT_POWERS_TEXTURE;
	}

	@Override
	protected Identifier getCurrentWeaknessesTexture() {
		return CURRENT_WEAKNESSES_TEXTURE;
	}

	@Override
	protected Identifier getCheckmarkTexture() {
		return CHECKMARK_TEXTURE;
	}

	@Override
	protected Identifier getCheckmarkHighlightedTexture() {
		return CHECKMARK_HIGHLIGHTED_TEXTURE;
	}

	@Override
	protected List<Identifier> getCyclingIcons() {
		return CYCLING_ICONS;
	}

	@Override
	protected Component getSecondCostText() {
		return Component.translatable(ModItems.BLOOD_BOTTLE.getDescriptionId());
	}
}
