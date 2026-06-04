/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.gui;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.NyctoItems;
import moriyashiine.nycto.neoforge.menu.VampireAltarMenu;
import moriyashiine.nycto.neoforge.network.SwapAltarPowersPayload;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class VampireAltarScreen extends AbstractContainerScreen<VampireAltarMenu> {
	private static final ResourceLocation BACKGROUND = NyctoNeoForge.id("textures/gui/container/vampire_altar.png");
	private static final ResourceLocation UNSELECTED_POWERS = NyctoNeoForge.id("container/vampire_altar/unselected_powers_frame");
	private static final ResourceLocation UNSELECTED_WEAKNESSES = NyctoNeoForge.id("container/vampire_altar/unselected_weaknesses_frame");
	private static final ResourceLocation CURRENT_POWERS = NyctoNeoForge.id("container/vampire_altar/current_powers_frame");
	private static final ResourceLocation CURRENT_WEAKNESSES = NyctoNeoForge.id("container/vampire_altar/current_weaknesses_frame");
	private static final ResourceLocation CHECKMARK = NyctoNeoForge.id("container/vampire_altar/checkmark");
	private static final ResourceLocation CHECKMARK_HIGHLIGHTED = NyctoNeoForge.id("container/vampire_altar/checkmark_highlighted");
	private static final List<String> LEGACY_WEAKNESSES = List.of("humanity", "hydrophobia", "pyrophobia", "rich_tastes", "thin_blood", "vile_presence");
	private static final int COST_SLOT_Y = 94;
	private static final int PRIMARY_COST_SLOT_X = 122;
	private static final int ALTERNATE_COST_SLOT_X = 142;
	private static final int VISIBLE_PLAYER_POWERS = 6;

	private int selectedPowerIndex = -1;
	private int selectedPlayerPowerIndex = -1;
	private int playerPowerScrollOffset = 0;

	public VampireAltarScreen(VampireAltarMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
		imageHeight = 224;
		inventoryLabelY = 131;
		titleLabelY = 5;
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
		super.render(graphics, mouseX, mouseY, partialTick);
		renderPowerTooltip(graphics, mouseX, mouseY);
		renderCostTooltip(graphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
		graphics.blit(BACKGROUND, leftPos, topPos, 0, 0, imageWidth, imageHeight, 256, 256);
		graphics.blitSprite(UNSELECTED_POWERS, leftPos + 11, topPos + 15, 157, 39);
		graphics.blitSprite(UNSELECTED_WEAKNESSES, leftPos + 11, topPos + 57, 157, 21);
		graphics.blitSprite(CURRENT_POWERS, leftPos + 11, topPos + 84, 107, 21);
		graphics.blitSprite(CURRENT_WEAKNESSES, leftPos + 11, topPos + 107, 107, 21);
		graphics.blitSprite(isCheckmarkHovered(mouseX, mouseY) ? CHECKMARK_HIGHLIGHTED : CHECKMARK, leftPos + 132, topPos + 113, 16, 16);
		if (menu.getSlot(0).getItem().isEmpty() && !menu.itemCost.isEmpty()) {
			graphics.renderFakeItem(menu.itemCost, leftPos + PRIMARY_COST_SLOT_X, topPos + COST_SLOT_Y);
		}
		if (menu.getSlot(1).getItem().isEmpty()) {
			graphics.renderFakeItem(cyclingAlternateCost(), leftPos + ALTERNATE_COST_SLOT_X, topPos + COST_SLOT_Y);
		}
		renderPowerIcons(graphics, mouseX, mouseY);
		renderLegacyWeaknessIcons(graphics);
	}

	private ItemStack cyclingAlternateCost() {
		long time = minecraft == null || minecraft.level == null ? 0 : minecraft.level.getGameTime();
		return new ItemStack((time / 30) % 2 == 0 ? NyctoItems.BLOOD_BOTTLE.get() : NyctoItems.VAMPIRE_BLOOD_BOTTLE.get());
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (button == 0) {
			if (selectedPowerIndex >= 0 && menu.canUpgrade(minecraft.player) && isCheckmarkHovered((int) mouseX, (int) mouseY)) {
				int powerIndex = selectedPowerIndex;
				if (minecraft.gameMode != null) {
					minecraft.gameMode.handleInventoryButtonClick(menu.containerId, powerIndex);
				}
				menu.clickMenuButton(minecraft.player, powerIndex);
				selectedPowerIndex = -1;
				playClick();
				return true;
			}
			int selectable = powerAt((int) mouseX, (int) mouseY, true);
			if (selectable >= 0) {
				selectedPowerIndex = selectable;
				selectedPlayerPowerIndex = -1;
				playClick();
				return true;
			}
			int owned = powerAt((int) mouseX, (int) mouseY, false);
			if (owned >= 0) {
				if (selectedPlayerPowerIndex >= 0 && selectedPlayerPowerIndex != owned) {
					String first = menu.playerPowers.get(selectedPlayerPowerIndex);
					String second = menu.playerPowers.get(owned);
					menu.swapPowers(first, second);
					SwapAltarPowersPayload.send(first, second);
					selectedPlayerPowerIndex = -1;
				} else {
					selectedPlayerPowerIndex = owned;
					selectedPowerIndex = -1;
				}
				playClick();
				return true;
			}
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
		if (scrollY != 0 && isCurrentPowersHovered((int) mouseX, (int) mouseY) && getMaxPlayerPowerScrollOffset() > 0) {
			playerPowerScrollOffset = clampPlayerPowerScrollOffset(playerPowerScrollOffset - (int) Math.signum(scrollY));
			playClick();
			return true;
		}
		return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
	}

	private void renderPowerIcons(GuiGraphics graphics, int mouseX, int mouseY) {
		playerPowerScrollOffset = clampPlayerPowerScrollOffset(playerPowerScrollOffset);
		for (int i = 0; i < menu.selectablePowers.size(); i++) {
			int x = selectablePowerX(i);
			int y = selectablePowerY(i);
			graphics.blit(powerTexture(menu.selectablePowers.get(i)), x, y, 0, 0, 16, 16, 16, 16);
			if (selectedPowerIndex == i) {
				graphics.fill(x, y, x + 16, y + 16, 0x7FFFFFFF);
			}
		}
		int end = Math.min(menu.playerPowers.size(), playerPowerScrollOffset + VISIBLE_PLAYER_POWERS);
		for (int i = playerPowerScrollOffset; i < end; i++) {
			int x = playerPowerX(i - playerPowerScrollOffset);
			int y = playerPowerY();
			graphics.blit(powerTexture(menu.playerPowers.get(i)), x, y, 0, 0, 16, 16, 16, 16);
			if (selectedPlayerPowerIndex == i) {
				graphics.fill(x, y, x + 16, y + 16, 0x7FFFFFFF);
			}
		}
		renderPlayerPowerScrollbar(graphics);
	}

	private void renderPowerTooltip(GuiGraphics graphics, int mouseX, int mouseY) {
		int legacyWeakness = legacyWeaknessAt(mouseX, mouseY);
		if (legacyWeakness >= 0) {
			graphics.renderComponentTooltip(font, legacyWeaknessTooltip(LEGACY_WEAKNESSES.get(legacyWeakness)), mouseX, mouseY);
			return;
		}
		int selectable = powerAt(mouseX, mouseY, true);
		if (selectable >= 0) {
			graphics.renderComponentTooltip(font, powerTooltip(menu.selectablePowers.get(selectable)), mouseX, mouseY);
			return;
		}
		int owned = powerAt(mouseX, mouseY, false);
		if (owned >= 0) {
			graphics.renderComponentTooltip(font, powerTooltip(menu.playerPowers.get(owned)), mouseX, mouseY);
		}
	}

	private void renderCostTooltip(GuiGraphics graphics, int mouseX, int mouseY) {
		if (!isCheckmarkHovered(mouseX, mouseY)) {
			return;
		}
		List<Component> tooltip = new ArrayList<>();
		tooltip.add(costText(Component.translatable("tooltip.nycto.experience_level_cost", menu.getExpCost()), minecraft.player.experienceLevel >= menu.getExpCost()));
		tooltip.add(costText(Component.translatable("tooltip.nycto.material_cost", menu.getPrimaryMaterialCost(), Component.translatable(menu.itemCost.getDescriptionId())), menu.getSlot(0).getItem().getCount() >= menu.getPrimaryMaterialCost()));
		tooltip.add(costText(Component.translatable("tooltip.nycto.material_cost", menu.getAlternateMaterialCost(), Component.translatable(NyctoItems.BLOOD_BOTTLE.get().getDescriptionId())), menu.getSlot(1).getItem().getCount() >= menu.getAlternateMaterialCost()));
		tooltip.add(CommonComponents.EMPTY);
		tooltip.add(costText(Component.translatable("tooltip.nycto.power_required"), selectedPowerIndex >= 0));
		graphics.renderComponentTooltip(font, tooltip, mouseX, mouseY);
	}

	private Component costText(Component text, boolean met) {
		return text.copy().withStyle(met || minecraft.player.isCreative() ? ChatFormatting.GREEN : ChatFormatting.RED);
	}

	private List<Component> powerTooltip(String power) {
		return List.of(Component.translatable("power.nycto." + power), Component.translatable("power.nycto." + power + ".desc").withStyle(ChatFormatting.GRAY));
	}

	private List<Component> legacyWeaknessTooltip(String weakness) {
		return List.of(
				Component.translatable("power.nycto." + weakness),
				Component.translatable("power.nycto." + weakness + ".desc").withStyle(ChatFormatting.GRAY),
				Component.translatable("tooltip.nycto.weakness_required").withStyle(ChatFormatting.DARK_GRAY));
	}

	private void renderLegacyWeaknessIcons(GuiGraphics graphics) {
		for (int i = 0; i < LEGACY_WEAKNESSES.size(); i++) {
			int x = legacyWeaknessX(i);
			int y = legacyWeaknessY();
			graphics.blit(powerTexture(LEGACY_WEAKNESSES.get(i)), x, y, 0, 0, 16, 16, 16, 16);
			graphics.fill(x, y, x + 16, y + 16, 0x66000000);
		}
	}

	private int powerAt(int mouseX, int mouseY, boolean selectable) {
		List<String> powers = selectable ? menu.selectablePowers : menu.playerPowers;
		int start = selectable ? 0 : playerPowerScrollOffset;
		int end = selectable ? powers.size() : Math.min(powers.size(), playerPowerScrollOffset + VISIBLE_PLAYER_POWERS);
		for (int i = start; i < end; i++) {
			int x = selectable ? selectablePowerX(i) : playerPowerX(i - playerPowerScrollOffset);
			int y = selectable ? selectablePowerY(i) : playerPowerY();
			if (mouseX >= x && mouseX < x + 16 && mouseY >= y && mouseY < y + 16) {
				return i;
			}
		}
		return -1;
	}

	private int selectablePowerX(int index) {
		return leftPos + 13 + (index % 9) * 17;
	}

	private int selectablePowerY(int index) {
		return topPos + 17 + (index / 9) * 17;
	}

	private int playerPowerX(int index) {
		return leftPos + 13 + index * 17;
	}

	private int playerPowerY() {
		return topPos + 86;
	}

	private int legacyWeaknessAt(int mouseX, int mouseY) {
		for (int i = 0; i < LEGACY_WEAKNESSES.size(); i++) {
			int x = legacyWeaknessX(i);
			int y = legacyWeaknessY();
			if (mouseX >= x && mouseX < x + 16 && mouseY >= y && mouseY < y + 16) {
				return i;
			}
		}
		return -1;
	}

	private int legacyWeaknessX(int index) {
		return leftPos + 13 + index * 17;
	}

	private int legacyWeaknessY() {
		return topPos + 59;
	}

	private boolean isCheckmarkHovered(int mouseX, int mouseY) {
		return mouseX >= leftPos + 132 && mouseX < leftPos + 148 && mouseY >= topPos + 113 && mouseY < topPos + 129;
	}

	private boolean isCurrentPowersHovered(int mouseX, int mouseY) {
		return mouseX >= leftPos + 11 && mouseX < leftPos + 118 && mouseY >= topPos + 84 && mouseY < topPos + 105;
	}

	private int getMaxPlayerPowerScrollOffset() {
		return Math.max(0, menu.playerPowers.size() - VISIBLE_PLAYER_POWERS);
	}

	private int clampPlayerPowerScrollOffset(int offset) {
		return Math.max(0, Math.min(offset, getMaxPlayerPowerScrollOffset()));
	}

	private void renderPlayerPowerScrollbar(GuiGraphics graphics) {
		int maxOffset = getMaxPlayerPowerScrollOffset();
		if (maxOffset <= 0) {
			return;
		}
		int trackX = leftPos + 114;
		int trackY = topPos + 87;
		int trackHeight = 15;
		graphics.fill(trackX, trackY, trackX + 2, trackY + trackHeight, 0x66000000);
		int thumbHeight = Math.max(3, trackHeight * VISIBLE_PLAYER_POWERS / menu.playerPowers.size());
		int thumbY = trackY + (trackHeight - thumbHeight) * playerPowerScrollOffset / maxOffset;
		graphics.fill(trackX, thumbY, trackX + 2, thumbY + thumbHeight, 0xCCB9A0A0);
	}

	private ResourceLocation powerTexture(String power) {
		return NyctoNeoForge.id("textures/power/" + power + ".png");
	}

	private void playClick() {
		minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
	}
}
