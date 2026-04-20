/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.api.client.gui.screens.inventory;

import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.world.inventory.AltarMenu;
import moriyashiine.nycto.api.world.power.Power;
import moriyashiine.nycto.common.payload.ApplyPowerFromAltarPayload;
import moriyashiine.nycto.common.payload.SwapPowersFromAltarPayload;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CyclingSlotBackground;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AltarScreen<T extends AltarMenu> extends AbstractContainerScreen<T> {
	private static final int MAX_PLAYER_POWERS = 6;

	private static final int CHECKMARK_X = 132, CHECKMARK_Y = 113;

	private final CyclingSlotBackground itemCostBackground = new CyclingSlotBackground(1);

	private int selectedPowerIndex = -1, selectedWeaknessIndex = -1, selectedPlayerPowerIndex = -1;

	private List<Component> infoTexts = null;

	public AltarScreen(T menu, Inventory inventory, Component title) {
		super(menu, inventory, title, 176, 224);
		titleLabelY -= 1;
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		super.extractRenderState(graphics, mouseX, mouseY, a);
		extractTooltip(graphics, mouseX, mouseY);
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		super.extractBackground(graphics, mouseX, mouseY, a);
		int posX = (width - imageWidth) / 2;
		int posY = (height - imageHeight) / 2;
		graphics.blit(RenderPipelines.GUI_TEXTURED, getBackgroundTexture(), posX, posY, 0, 0, imageWidth, imageHeight, 256, 256);
		extractSlots(graphics, posX, posY, a);
		extractCheckmark(graphics, posX, posY, mouseX, mouseY);
		extractPowers(graphics, posX + 13, posY + 17, mouseX, mouseY);
		graphics.blitSprite(RenderPipelines.GUI_TEXTURED, getUnselectedPowersTexture(), posX + 11, posY + 15, 157, 39);
		graphics.blitSprite(RenderPipelines.GUI_TEXTURED, getUnselectedWeaknessesTexture(), posX + 11, posY + 57, 157, 21);
		graphics.blitSprite(RenderPipelines.GUI_TEXTURED, getCurrentPowersTexture(), posX + 11, posY + 84, 107, 21);
		graphics.blitSprite(RenderPipelines.GUI_TEXTURED, getCurrentWeaknessesTexture(), posX + 11, posY + 107, 107, 21);
	}

	@Override
	protected void containerTick() {
		itemCostBackground.tick(getCyclingIcons());
	}

	@Override
	public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
		int posX = (width - imageWidth) / 2;
		int posY = (height - imageHeight) / 2;
		// selectable powers
		if (menu.getPlayerPowers() < MAX_PLAYER_POWERS) {
			boolean needsWeakness = needsWeakness();
			if (selectedPowerIndex != -1 && (!needsWeakness || selectedWeaknessIndex != -1) && menu.canUpgrade(minecraft.player) && isCheckmarkInBounds(posX, posY, (int) event.x(), (int) event.y())) {
				int id = NyctoRegistries.POWER.getId(menu.selectablePowers.get(selectedPowerIndex));
				if (menu.clickMenuButton(minecraft.player, id)) {
					minecraft.gameMode.handleInventoryButtonClick(menu.containerId, id);
					if (selectedWeaknessIndex != -1) {
						id = NyctoRegistries.POWER.getId(menu.selectablePowers.get(selectedWeaknessIndex - 1));
						AltarMenu.apply(minecraft.player, id);
						ApplyPowerFromAltarPayload.send(id);
					}
					minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
					selectedPowerIndex = selectedWeaknessIndex = -1;
					return true;
				}
			}
			Tuple<Integer, Boolean> clicked = clickPower(posX + 13, posY + 17, (int) event.x(), (int) event.y(), true);
			int clickedIndex = clicked.getA();
			if (clickedIndex != -1) {
				if (clicked.getB()) {
					if (needsWeakness) {
						selectedWeaknessIndex = clickedIndex;
					}
				} else {
					selectedPowerIndex = clickedIndex;
				}
				if ((selectedPowerIndex != -1 && !clicked.getB()) || (selectedWeaknessIndex != -1 && clicked.getB())) {
					minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
					return true;
				}
			}
		}
		// player powers
		if (menu.getPlayerPowers() >= 2) {
			Tuple<Integer, Boolean> clicked = clickPower(posX + 13, posY + 17, (int) event.x(), (int) event.y(), false);
			int clickedIndex = clicked.getA();
			if (clickedIndex != -1) {
				if (!clicked.getB() || menu.playerPowers.stream().filter(Power::isWeakness).collect(Collectors.toSet()).size() >= 2) {
					minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
					Power first = null;
					if (selectedPlayerPowerIndex != -1) {
						first = menu.playerPowers.get(selectedPlayerPowerIndex);
					}
					if (clickedIndex != selectedPlayerPowerIndex) {
						selectedPlayerPowerIndex = clickedIndex;
						Power second = menu.playerPowers.get(selectedPlayerPowerIndex);
						if (first != null && first.isWeakness() == second.isWeakness() && menu.clickMenuButton(minecraft.player, Short.MAX_VALUE)) {
							minecraft.gameMode.handleInventoryButtonClick(menu.containerId, Short.MAX_VALUE);
							AltarMenu.swapPowers(minecraft.player, first, second);
							SwapPowersFromAltarPayload.send(first, second);
							selectedPlayerPowerIndex = -1;
							return true;
						}
					}
				}
			}
		}
		return super.mouseClicked(event, doubleClick);
	}

	private void extractSlots(GuiGraphicsExtractor graphics, int posX, int posY, float a) {
		if (menu.getPlayerPowers() < MAX_PLAYER_POWERS) {
			graphics.item(menu.itemCost, posX + 122, posY + 94);
			// rendering the slot texture again after the item mimics transparency
			graphics.blit(RenderPipelines.GUI_TEXTURED, getBackgroundTexture(), posX + 122, posY + 94, 122, 94, 16, 16, 16, 16, 256, 256, 0x7FFFFFFF);
		}
		itemCostBackground.extractRenderState(menu, graphics, a, posX, posY);
	}

	private void extractCheckmark(GuiGraphicsExtractor graphics, int posX, int posY, int mouseX, int mouseY) {
		if (isCheckmarkInBounds(posX, posY, mouseX, mouseY)) {
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, getCheckmarkHighlightedTexture(), posX + CHECKMARK_X, posY + CHECKMARK_Y, 16, 16);
			if (menu.getPlayerPowers() < MAX_PLAYER_POWERS) {
				if (infoTexts == null) {
					MutableComponent expCost = Component.translatable("tooltip.nycto.experience_level_cost", menu.getExpCost()).withStyle(ChatFormatting.GREEN);
					MutableComponent materialCost = Component.translatable("tooltip.nycto.material_cost", menu.getPrimaryMaterialCost(), Component.translatable(menu.itemCost.getItem().getDescriptionId())).withStyle(ChatFormatting.GREEN);
					MutableComponent secondCost = Component.translatable("tooltip.nycto.material_cost", menu.getAlternateMaterialCost(), getSecondCostText()).withStyle(ChatFormatting.GREEN);
					MutableComponent powerRequired = Component.translatable("tooltip.nycto.power_required").withStyle(ChatFormatting.GREEN);
					if (!minecraft.player.isCreative()) {
						if (minecraft.player.experienceLevel < menu.getExpCost()) {
							expCost.withStyle(ChatFormatting.RED);
						}
						if (menu.getSlot(0).getItem().getCount() < menu.getPrimaryMaterialCost()) {
							materialCost.withStyle(ChatFormatting.RED);
						}
						if (menu.getSlot(1).getItem().getCount() < menu.getAlternateMaterialCost()) {
							secondCost.withStyle(ChatFormatting.RED);
						}
					}
					if (selectedPowerIndex == -1) {
						powerRequired.withStyle(ChatFormatting.RED);
					}
					if (needsWeakness()) {
						MutableComponent weaknessRequired = Component.translatable("tooltip.nycto.weakness_required").withStyle(ChatFormatting.GREEN);
						if (selectedWeaknessIndex == -1) {
							weaknessRequired.withStyle(ChatFormatting.RED);
						}
						infoTexts = List.of(expCost, materialCost, secondCost, CommonComponents.EMPTY, powerRequired, weaknessRequired);
					} else {
						infoTexts = List.of(expCost, materialCost, secondCost, CommonComponents.EMPTY, powerRequired);
					}
				}
				graphics.setComponentTooltipForNextFrame(font, infoTexts, mouseX, mouseY);
			}
		} else {
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, getCheckmarkTexture(), posX + CHECKMARK_X, posY + CHECKMARK_Y, 16, 16);
			infoTexts = null;
		}
	}

	private void extractPowers(GuiGraphicsExtractor graphics, int posX, int posY, int mouseX, int mouseY) {
		boolean needsWeakness = needsWeakness();
		// selectable powers
		int powers = 0;
		for (int i = 0; i < menu.selectablePowers.size(); i++) {
			Power power = menu.selectablePowers.get(i);
			int row = i > 8 && !power.isWeakness() ? 1 : 0;
			if (!power.isWeakness()) {
				powers++;
			}
			int offsetX = i * 17 - (row * 9 * 17);
			if (power.isWeakness()) {
				offsetX -= powers * 17;
			}
			int offsetY = (row * 17) + (power.isWeakness() ? 42 : 0);
			extractPowerInfo(power, graphics, posX + offsetX, posY + offsetY, mouseX, mouseY);
			boolean transparent = (power.isWeakness() && !needsWeakness) || menu.getPlayerPowers() >= MAX_PLAYER_POWERS;
			graphics.blit(RenderPipelines.GUI_TEXTURED, power.getOrCreateTextureLocation(), posX + offsetX, posY + offsetY, 0, 0, 16, 16, 16, 16, 16, 16, transparent ? 0X7FFFFFFF : -1);
			if (selectedPowerIndex == i || selectedWeaknessIndex == i) {
				graphics.fill(posX + offsetX, posY + offsetY, posX + offsetX + 16, posY + offsetY + 16, Integer.MAX_VALUE);
			}
		}
		// player powers
		powers = 0;
		for (int i = 0; i < menu.playerPowers.size(); i++) {
			Power power = menu.playerPowers.get(i);
			if (!power.isWeakness()) {
				powers++;
			}
			int offsetX = i * 17;
			if (power.isWeakness()) {
				offsetX -= powers * 17;
			}
			int offsetY = 69 + (power.isWeakness() ? 23 : 0);
			extractPowerInfo(power, graphics, posX + offsetX, posY + offsetY, mouseX, mouseY);
			graphics.blit(RenderPipelines.GUI_TEXTURED, power.getOrCreateTextureLocation(), posX + offsetX, posY + offsetY, 0, 0, 16, 16, 16, 16);
			if (selectedPlayerPowerIndex == i) {
				graphics.fill(posX + offsetX, posY + offsetY, posX + offsetX + 16, posY + offsetY + 16, Integer.MAX_VALUE);
			}
		}
	}

	private void extractPowerInfo(Power power, GuiGraphicsExtractor graphics, int posX, int posY, int mouseX, int mouseY) {
		if (isInBounds(posX, posY, mouseX, mouseY, 0, 16, 0, 16)) {
			if (infoTexts == null) {
				infoTexts = new ArrayList<>();
				infoTexts.add(Component.translatable(power.getOrCreateDescriptionId()));
				infoTexts.addAll(SLibClientUtils.wrapText(Component.literal(" - ").withStyle(ChatFormatting.GRAY).append(Component.translatable(power.getOrCreateDescriptionId() + ".desc"))));
			}
			graphics.setComponentTooltipForNextFrame(font, infoTexts, mouseX, mouseY);
		} else {
			infoTexts = null;
		}
	}

	private Tuple<Integer, Boolean> clickPower(int posX, int posY, int mouseX, int mouseY, boolean selectable) {
		int powers = 0;
		if (selectable) {
			for (int i = 0; i < menu.selectablePowers.size(); i++) {
				Power power = menu.selectablePowers.get(i);
				int row = i > 8 && !power.isWeakness() ? 1 : 0;
				if (!power.isWeakness()) {
					powers++;
				}
				int offsetX = i * 17 - (row * 9 * 17);
				if (power.isWeakness()) {
					offsetX -= powers * 17;
				}
				int offsetY = (row * 17) + (power.isWeakness() ? 42 : 0);
				if (isInBounds(posX + offsetX, posY + offsetY, mouseX, mouseY, 0, 16, 0, 16)) {
					return new Tuple<>(i, power.isWeakness());
				}
			}
		} else {
			for (int i = 0; i < menu.playerPowers.size(); i++) {
				Power power = menu.playerPowers.get(i);
				if (!power.isWeakness()) {
					powers++;
				}
				int offsetX = i * 17;
				if (power.isWeakness()) {
					offsetX -= powers * 17;
				}
				int offsetY = 69 + (power.isWeakness() ? 23 : 0);
				if (isInBounds(posX + offsetX, posY + offsetY, mouseX, mouseY, 0, 16, 0, 16)) {
					return new Tuple<>(i, power.isWeakness());
				}
			}
		}
		return new Tuple<>(-1, false);
	}

	private boolean needsWeakness() {
		return menu.getPlayerPowers() % 2 == 0;
	}

	private static boolean isInBounds(int posX, int posY, int mouseX, int mouseY, int startX, int endX, int startY, int endY) {
		return mouseX >= posX + startX && mouseX <= posX + endX && mouseY >= posY + startY && mouseY <= posY + endY;
	}

	private static boolean isCheckmarkInBounds(int posX, int posY, int mouseX, int mouseY) {
		return isInBounds(posX, posY, mouseX, mouseY, CHECKMARK_X, CHECKMARK_X + 16, CHECKMARK_Y, CHECKMARK_Y + 16);
	}

	protected abstract Identifier getBackgroundTexture();

	protected abstract Identifier getUnselectedPowersTexture();

	protected abstract Identifier getUnselectedWeaknessesTexture();

	protected abstract Identifier getCurrentPowersTexture();

	protected abstract Identifier getCurrentWeaknessesTexture();

	protected abstract Identifier getCheckmarkTexture();

	protected abstract Identifier getCheckmarkHighlightedTexture();

	protected abstract List<Identifier> getCyclingIcons();

	protected abstract Component getSecondCostText();
}
