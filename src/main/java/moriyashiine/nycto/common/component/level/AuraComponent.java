/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.level;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.NyctoLevelComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class AuraComponent implements AutoSyncedComponent, ServerTickingComponent {
	public static final int RADIUS = 12;

	private final Level obj;
	private final Set<BlockPos> garlicWreaths = new HashSet<>(), aconiteGarlands = new HashSet<>();

	public AuraComponent(Level obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		garlicWreaths.clear();
		garlicWreaths.addAll(input.read("GarlicWreaths", BlockPos.CODEC.listOf()).orElse(List.of()));
		aconiteGarlands.clear();
		aconiteGarlands.addAll(input.read("AconiteGarlands", BlockPos.CODEC.listOf()).orElse(List.of()));
	}

	@Override
	public void writeData(ValueOutput output) {
		output.store("GarlicWreaths", BlockPos.CODEC.listOf(), new ArrayList<>(garlicWreaths));
		output.store("AconiteGarlands", BlockPos.CODEC.listOf(), new ArrayList<>(aconiteGarlands));
	}

	@Override
	public void serverTick() {
		if (obj.getGameTime() % 10 == 0) {
			garlicWreaths.forEach(pos -> applyGarlicAura(obj, pos, RADIUS));
		}
	}

	public void sync() {
		NyctoLevelComponents.AURA.sync(obj);
	}

	public Set<BlockPos> getGarlicWreaths() {
		return garlicWreaths;
	}

	public Set<BlockPos> getAconiteGarlands() {
		return aconiteGarlands;
	}

	public static void applyGarlicAura(Level level, BlockPos pos, int radius) {
		applyAura(level, pos, radius, NyctoAPI::isVampire, entity -> {
			entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 30, 1, true, false));
			entity.addEffect(new MobEffectInstance(MobEffects.MINING_FATIGUE, 30, 1, true, false));
			NyctoAPI.applyHealBlock(entity, 30);
		});
	}

	private static void applyAura(Level level, BlockPos pos, int radius, Predicate<LivingEntity> predicate, AuraEffects effects) {
		Vec3 center = Vec3.atCenterOf(pos);
		level.getEntitiesOfClass(LivingEntity.class, new AABB(center.add(-radius, -radius, -radius), center.add(radius, radius, radius))).forEach(foundEntity -> {
			if (foundEntity.slib$isSurvival() && predicate.test(foundEntity) && level.clip(new ClipContext(center, foundEntity.getEyePosition(), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, foundEntity)).getType() == HitResult.Type.MISS) {
				effects.apply(foundEntity);
			}
		});
	}

	private interface AuraEffects {
		void apply(LivingEntity entity);
	}
}
