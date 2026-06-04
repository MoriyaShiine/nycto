/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.EnumMap;
import java.util.Map;

public final class NyctoArmorClientExtensions {
	private static final IClientItemExtensions VAMPIRE = new Extension(ArmorStyle.VAMPIRE);
	private static final IClientItemExtensions HUNTER = new Extension(ArmorStyle.HUNTER);

	private NyctoArmorClientExtensions() {
	}

	public static IClientItemExtensions vampire() {
		return VAMPIRE;
	}

	public static IClientItemExtensions hunter() {
		return HUNTER;
	}

	private enum ArmorStyle {
		VAMPIRE,
		HUNTER
	}

	private static final class Extension implements IClientItemExtensions {
		private final ArmorStyle style;
		private final Map<EquipmentSlot, NyctoHumanoidArmorModel> models = new EnumMap<>(EquipmentSlot.class);

		private Extension(ArmorStyle style) {
			this.style = style;
		}

		@Override
		public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
			if (equipmentSlot != EquipmentSlot.HEAD && equipmentSlot != EquipmentSlot.CHEST && equipmentSlot != EquipmentSlot.LEGS && equipmentSlot != EquipmentSlot.FEET) {
				return original;
			}
			return models.computeIfAbsent(equipmentSlot, this::bakeModel);
		}

		@Override
		public void setupModelAnimations(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, Model model, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
			if (model instanceof NyctoHumanoidArmorModel armorModel) {
				armorModel.animateLooseCoat();
			}
		}

		private NyctoHumanoidArmorModel bakeModel(EquipmentSlot slot) {
			return new NyctoHumanoidArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(layer(slot)));
		}

		private ModelLayerLocation layer(EquipmentSlot slot) {
			return switch (style) {
				case VAMPIRE -> switch (slot) {
					case HEAD -> NyctoHumanoidArmorModel.VAMPIRE_HELMET;
					case CHEST -> NyctoHumanoidArmorModel.VAMPIRE_CHESTPLATE;
					case LEGS -> NyctoHumanoidArmorModel.VAMPIRE_LEGGINGS;
					case FEET -> NyctoHumanoidArmorModel.VAMPIRE_BOOTS;
					default -> NyctoHumanoidArmorModel.VAMPIRE_CHESTPLATE;
				};
				case HUNTER -> switch (slot) {
					case HEAD -> NyctoHumanoidArmorModel.HUNTER_HELMET;
					case CHEST -> NyctoHumanoidArmorModel.HUNTER_CHESTPLATE;
					case LEGS -> NyctoHumanoidArmorModel.HUNTER_LEGGINGS;
					case FEET -> NyctoHumanoidArmorModel.HUNTER_BOOTS;
					default -> NyctoHumanoidArmorModel.HUNTER_CHESTPLATE;
				};
			};
		}
	}
}
