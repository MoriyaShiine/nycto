/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.gui.screen.ingame;

import moriyashiine.nycto.api.client.gui.screen.ingame.AltarScreen;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.screenhandler.VampireAltarScreenHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class VampireAltarScreen extends AltarScreen<VampireAltarScreenHandler> {
	private static final Identifier BACKGROUND_TEXTURE = Nycto.id("textures/gui/container/vampire_altar.png");
	private static final Identifier UNSELECTED_POSITIVES_TEXTURE = Nycto.id("container/vampire_altar/unselected_positives_frame");
	private static final Identifier UNSELECTED_NEGATIVES_TEXTURE = Nycto.id("container/vampire_altar/unselected_negatives_frame");
	private static final Identifier CURRENT_POSITIVES_TEXTURE = Nycto.id("container/vampire_altar/current_positives_frame");
	private static final Identifier CURRENT_NEGATIVES_TEXTURE = Nycto.id("container/vampire_altar/current_negatives_frame");
	private static final Identifier CHECKMARK_TEXTURE = Nycto.id("container/vampire_altar/checkmark");
	private static final Identifier CHECKMARK_HIGHLIGHTED_TEXTURE = Nycto.id("container/vampire_altar/checkmark_highlighted");
	private static final List<Identifier> CYCLING_ICONS = List.of(Nycto.id("container/slot/blood_bottle"), Nycto.id("container/slot/vampire_blood_bottle"));

	public VampireAltarScreen(VampireAltarScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Override
	protected Identifier getBackgroundTexture() {
		return BACKGROUND_TEXTURE;
	}

	@Override
	protected Identifier getUnselectedPositivesTexture() {
		return UNSELECTED_POSITIVES_TEXTURE;
	}

	@Override
	protected Identifier getUnselectedNegativesTexture() {
		return UNSELECTED_NEGATIVES_TEXTURE;
	}

	@Override
	protected Identifier getCurrentPositivesTexture() {
		return CURRENT_POSITIVES_TEXTURE;
	}

	@Override
	protected Identifier getCurrentNegativesTexture() {
		return CURRENT_NEGATIVES_TEXTURE;
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
	protected Text getSecondCostText() {
		return Text.translatable(ModItems.BLOOD_BOTTLE.getTranslationKey());
	}
}
