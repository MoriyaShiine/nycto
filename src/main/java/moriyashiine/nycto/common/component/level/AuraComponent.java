/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.level;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModLevelComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
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
	private final Set<BlockPos> garlicWreaths = new HashSet<>();

	public AuraComponent(Level obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		garlicWreaths.clear();
		garlicWreaths.addAll(input.read("GarlicWreaths", BlockPos.CODEC.listOf()).orElse(List.of()));
	}

	@Override
	public void writeData(ValueOutput output) {
		output.store("GarlicWreaths", BlockPos.CODEC.listOf(), new ArrayList<>(garlicWreaths));
	}

	@Override
	public void serverTick() {
		if (obj.getGameTime() % 10 == 0) {
			garlicWreaths.forEach(pos -> applyAura(obj, pos, RADIUS, true, NyctoAPI::isVampire));
		}
	}

	public void sync() {
		ModLevelComponents.AURA.sync(obj);
	}

	public Set<BlockPos> getGarlicWreaths() {
		return garlicWreaths;
	}

	public static void applyAura(Level level, BlockPos pos, int radius, boolean healBlock, Predicate<LivingEntity> predicate) {
		level.getEntitiesOfClass(LivingEntity.class, new AABB(pos.getCenter().add(-radius, -radius, -radius), pos.getCenter().add(radius, radius, radius))).forEach(foundEntity -> {
			if (predicate.test(foundEntity)) {
				foundEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 30, 1, true, false));
				foundEntity.addEffect(new MobEffectInstance(MobEffects.MINING_FATIGUE, 30, 1, true, false));
				if (healBlock) {
					NyctoAPI.applyHealBlock(foundEntity, 30);
				}
			}
		});
	}
}
