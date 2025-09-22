/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.component.world;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModWorldComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class AuraComponent implements AutoSyncedComponent, ServerTickingComponent {
	public static final int RADIUS = 12;

	private final World obj;
	private final Set<BlockPos> garlicWreaths = new HashSet<>();

	public AuraComponent(World obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ReadView readView) {
		garlicWreaths.clear();
		garlicWreaths.addAll(readView.read("GarlicWreaths", BlockPos.CODEC.listOf()).orElse(List.of()));
	}

	@Override
	public void writeData(WriteView writeView) {
		writeView.put("GarlicWreaths", BlockPos.CODEC.listOf(), new ArrayList<>(garlicWreaths));
	}

	@Override
	public void serverTick() {
		if (obj.getTime() % 10 == 0) {
			garlicWreaths.forEach(pos -> applyAura(obj, pos, RADIUS, NyctoAPI::isVampire));
		}
	}

	public void sync() {
		ModWorldComponents.AURA.sync(obj);
	}

	public Set<BlockPos> getGarlicWreaths() {
		return garlicWreaths;
	}

	public static void applyAura(World world, BlockPos pos, int radius, Predicate<LivingEntity> predicate) {
		world.getNonSpectatingEntities(LivingEntity.class, new Box(pos.toCenterPos().add(-radius, -radius, -radius), pos.toCenterPos().add(radius, radius, radius))).forEach(foundEntity -> {
			if (predicate.test(foundEntity)) {
				NyctoAPI.applyHealBlock(foundEntity, 30);
				foundEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 30, 1, true, false));
				foundEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 30, 1, true, false));
			}
		});
	}
}
