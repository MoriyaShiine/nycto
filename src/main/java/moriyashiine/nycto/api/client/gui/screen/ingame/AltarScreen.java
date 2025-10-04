/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.api.client.gui.screen.ingame;

import com.mojang.datafixers.util.Pair;
import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.power.Power;
import moriyashiine.nycto.api.screenhandler.AltarScreenHandler;
import moriyashiine.nycto.common.payload.ApplyPowerFromAltarPayload;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CyclingSlotIcon;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;

public abstract class AltarScreen<T extends AltarScreenHandler> extends HandledScreen<T> {
	private static final int MAX_POSITIVE_POWERS = 6;

	private static final int CHECKMARK_X = 132, CHECKMARK_Y = 113;

	private final CyclingSlotIcon cyclingIcon = new CyclingSlotIcon(1);

	private int selectedIndex = -1, selectedNegativeIndex = -1;

	private List<Text> infoTexts = null;

	public AltarScreen(T handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
		backgroundHeight = 224;
		titleY -= 1;
		playerInventoryTitleY += 58;
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
		super.render(context, mouseX, mouseY, deltaTicks);
		drawMouseoverTooltip(context, mouseX, mouseY);
	}

	@Override
	protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
		int posX = (width - backgroundWidth) / 2;
		int posY = (height - backgroundHeight) / 2;
		context.drawTexture(RenderPipelines.GUI_TEXTURED, getBackgroundTexture(), posX, posY, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
		drawSlots(context, posX, posY, deltaTicks);
		drawCheckmark(context, posX, posY, mouseX, mouseY);
		drawPowers(context, posX + 13, posY + 17, mouseX, mouseY);
		context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, getUnselectedPositivesTexture(), posX + 11, posY + 15, 157, 39);
		context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, getUnselectedNegativesTexture(), posX + 11, posY + 57, 157, 21);
		context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, getCurrentPositivesTexture(), posX + 11, posY + 84, 107, 21);
		context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, getCurrentNegativesTexture(), posX + 11, posY + 107, 107, 21);
	}

	@Override
	protected void handledScreenTick() {
		cyclingIcon.updateTexture(getCyclingIcons());
	}

	@Override
	public boolean mouseClicked(Click click, boolean doubled) {
		int posX = (width - backgroundWidth) / 2;
		int posY = (height - backgroundHeight) / 2;
		if (handler.getPositivePowers() < MAX_POSITIVE_POWERS) {
			boolean needsNegative = needsNegative();
			if (selectedIndex != -1 && (!needsNegative || selectedNegativeIndex != -1) && handler.canUpgrade(client.player) && isCheckmarkInBounds(posX, posY, (int) click.x(), (int) click.y())) {
				int rawId = NyctoRegistries.POWER.getRawId(handler.selectablePowers.get(selectedIndex));
				if (handler.onButtonClick(client.player, rawId)) {
					client.interactionManager.clickButton(handler.syncId, rawId);
					if (selectedNegativeIndex != -1) {
						rawId = NyctoRegistries.POWER.getRawId(handler.selectablePowers.get(selectedNegativeIndex - 1));
						AltarScreenHandler.apply(client.player, rawId);
						ApplyPowerFromAltarPayload.send(rawId);
					}
					client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1));
					selectedIndex = selectedNegativeIndex = -1;
					return true;
				}
			}
			Pair<Integer, Boolean> clicked = clickPower(posX + 13, posY + 17, (int) click.x(), (int) click.y());
			int clickedIndex = clicked.getFirst();
			if (clickedIndex != -1) {
				if (clicked.getSecond()) {
					if (needsNegative) {
						selectedNegativeIndex = clickedIndex;
					}
				} else {
					selectedIndex = clickedIndex;
				}
				if ((selectedIndex != -1 && !clicked.getSecond()) || (selectedNegativeIndex != -1 && clicked.getSecond())) {
					client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1));
					return true;
				}
			}
		}
		return super.mouseClicked(click, doubled);
	}

	private void drawSlots(DrawContext context, int posX, int posY, float delta) {
		if (handler.getPositivePowers() < MAX_POSITIVE_POWERS) {
			context.drawItem(handler.itemCost, posX + 122, posY + 94);
			// rendering the slot texture again after the item mimics transparency
			context.drawTexture(RenderPipelines.GUI_TEXTURED, getBackgroundTexture(), posX + 122, posY + 94, 122, 94, 16, 16, 16, 16, 256, 256, 0x7FFFFFFF);
		}
		cyclingIcon.render(handler, context, delta, posX, posY);
	}

	private void drawCheckmark(DrawContext context, int posX, int posY, int mouseX, int mouseY) {
		if (isCheckmarkInBounds(posX, posY, mouseX, mouseY)) {
			context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, getCheckmarkHighlightedTexture(), posX + CHECKMARK_X, posY + CHECKMARK_Y, 16, 16);
			if (handler.getPositivePowers() < MAX_POSITIVE_POWERS) {
				if (infoTexts == null) {
					MutableText xpCost = Text.translatable("tooltip.nycto.experience_level_cost", handler.getExpCost()).formatted(Formatting.GREEN);
					MutableText materialCost = Text.translatable("tooltip.nycto.material_cost", handler.getMaterialCost(), Text.translatable(handler.itemCost.getItem().getTranslationKey())).formatted(Formatting.GREEN);
					MutableText secondCost = Text.translatable("tooltip.nycto.material_cost", handler.getMaterialCost(), getSecondCostText()).formatted(Formatting.GREEN);
					MutableText powerRequired = Text.translatable("tooltip.nycto.power_required").formatted(Formatting.GREEN);
					if (!client.player.isCreative()) {
						if (client.player.experienceLevel < handler.getExpCost()) {
							xpCost.formatted(Formatting.RED);
						}
						if (handler.getSlot(0).getStack().getCount() < handler.getMaterialCost()) {
							materialCost.formatted(Formatting.RED);
						}
						if (handler.getSlot(1).getStack().getCount() < handler.getMaterialCost()) {
							secondCost.formatted(Formatting.RED);
						}
					}
					if (selectedIndex == -1) {
						powerRequired.formatted(Formatting.RED);
					}
					if (needsNegative()) {
						MutableText negativePowerRequired = Text.translatable("tooltip.nycto.weakness_required").formatted(Formatting.GREEN);
						if (selectedNegativeIndex == -1) {
							negativePowerRequired.formatted(Formatting.RED);
						}
						infoTexts = List.of(xpCost, materialCost, secondCost, ScreenTexts.EMPTY, powerRequired, negativePowerRequired);
					} else {
						infoTexts = List.of(xpCost, materialCost, secondCost, ScreenTexts.EMPTY, powerRequired);
					}
				}
				context.drawTooltip(textRenderer, infoTexts, mouseX, mouseY);
			}
		} else {
			context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, getCheckmarkTexture(), posX + CHECKMARK_X, posY + CHECKMARK_Y, 16, 16);
			infoTexts = null;
		}
	}

	private void drawPowers(DrawContext context, int posX, int posY, int mouseX, int mouseY) {
		boolean needsNegative = needsNegative();
		// selectable powers
		int positives = 0;
		for (int i = 0; i < handler.selectablePowers.size(); i++) {
			Power power = handler.selectablePowers.get(i);
			int row = i > 8 && !power.isNegative() ? 1 : 0;
			if (!power.isNegative()) {
				positives++;
			}
			int offsetX = i * 17 - (row * 9 * 17);
			if (power.isNegative()) {
				offsetX -= positives * 17;
			}
			int offsetY = (row * 17) + (power.isNegative() ? 42 : 0);
			renderPowerInfo(power, context, posX + offsetX, posY + offsetY, mouseX, mouseY);
			boolean transparent = (power.isNegative() && !needsNegative) || handler.getPositivePowers() >= MAX_POSITIVE_POWERS;
			context.drawTexture(RenderPipelines.GUI_TEXTURED, power.getTextureLocation(), posX + offsetX, posY + offsetY, 0, 0, 16, 16, 16, 16, 16, 16, transparent ? 0X7FFFFFFF : -1);
			if (selectedIndex == i || selectedNegativeIndex == i) {
				context.fill(posX + offsetX, posY + offsetY, posX + offsetX + 16, posY + offsetY + 16, Integer.MAX_VALUE);
			}
		}
		// player powers
		positives = 0;
		for (int i = 0; i < handler.playerPowers.size(); i++) {
			Power power = handler.playerPowers.get(i);
			if (!power.isNegative()) {
				positives++;
			}
			int offsetX = i * 17;
			if (power.isNegative()) {
				offsetX -= positives * 17;
			}
			int offsetY = 69 + (power.isNegative() ? 23 : 0);
			renderPowerInfo(power, context, posX + offsetX, posY + offsetY, mouseX, mouseY);
			context.drawTexture(RenderPipelines.GUI_TEXTURED, power.getTextureLocation(), posX + offsetX, posY + offsetY, 0, 0, 16, 16, 16, 16);
		}
	}

	private void renderPowerInfo(Power power, DrawContext context, int posX, int posY, int mouseX, int mouseY) {
		if (isInBounds(posX, posY, mouseX, mouseY, 0, 16, 0, 16)) {
			if (infoTexts == null) {
				infoTexts = List.of(
						Text.translatable(power.getTranslationKey()),
						Text.literal(" - ").formatted(Formatting.GRAY).append(Text.translatable(power.getTranslationKey() + ".desc")));
			}
			context.drawTooltip(textRenderer, infoTexts, mouseX, mouseY);
		} else {
			infoTexts = null;
		}
	}

	private Pair<Integer, Boolean> clickPower(int posX, int posY, int mouseX, int mouseY) {
		int positives = 0;
		for (int i = 0; i < handler.selectablePowers.size(); i++) {
			Power power = handler.selectablePowers.get(i);
			int row = i > 8 && !power.isNegative() ? 1 : 0;
			if (!power.isNegative()) {
				positives++;
			}
			int offsetX = i * 17 - (row * 9 * 17);
			if (power.isNegative()) {
				offsetX -= positives * 17;
			}
			int offsetY = (row * 17) + (power.isNegative() ? 42 : 0);
			if (isInBounds(posX + offsetX, posY + offsetY, mouseX, mouseY, 0, 16, 0, 16)) {
				return Pair.of(i, power.isNegative());
			}
		}
		return Pair.of(-1, false);
	}

	private boolean needsNegative() {
		return handler.getPositivePowers() % 2 == 0;
	}

	private static boolean isInBounds(int posX, int posY, int mouseX, int mouseY, int startX, int endX, int startY, int endY) {
		return mouseX >= posX + startX && mouseX <= posX + endX && mouseY >= posY + startY && mouseY <= posY + endY;
	}

	private static boolean isCheckmarkInBounds(int posX, int posY, int mouseX, int mouseY) {
		return isInBounds(posX, posY, mouseX, mouseY, CHECKMARK_X, CHECKMARK_X + 16, CHECKMARK_Y, CHECKMARK_Y + 16);
	}

	protected abstract Identifier getBackgroundTexture();

	protected abstract Identifier getUnselectedPositivesTexture();

	protected abstract Identifier getUnselectedNegativesTexture();

	protected abstract Identifier getCurrentPositivesTexture();

	protected abstract Identifier getCurrentNegativesTexture();

	protected abstract Identifier getCheckmarkTexture();

	protected abstract Identifier getCheckmarkHighlightedTexture();

	protected abstract List<Identifier> getCyclingIcons();

	protected abstract Text getSecondCostText();
}
