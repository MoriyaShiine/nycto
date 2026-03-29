/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.subpredicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.world.power.Power;
import moriyashiine.nycto.api.world.power.PowerInstance;
import moriyashiine.nycto.common.component.entity.TransformationComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.advancements.criterion.EntitySubPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public record VampirePredicate(Optional<Boolean> vampire,
							   Optional<PowerCountPredicate> count) implements EntitySubPredicate {
	public static final MapCodec<VampirePredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.BOOL.optionalFieldOf("vampire").forGetter(VampirePredicate::vampire),
			PowerCountPredicate.CODEC.optionalFieldOf("count").forGetter(VampirePredicate::count)
	).apply(instance, VampirePredicate::new));

	@Override
	public MapCodec<VampirePredicate> codec() {
		return CODEC;
	}

	@Override
	public boolean matches(Entity entity, ServerLevel level, @Nullable Vec3 position) {
		if (vampire().map(b -> b != NyctoAPI.isVampire(entity)).orElse(false)) {
			return false;
		}
		return count().map(b -> b.test(entity)).orElse(true);
	}

	public record PowerCountPredicate(HolderSet<Power> powers, MinMaxBounds.Ints range) {
		public static final Codec<PowerCountPredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				RegistryCodecs.homogeneousList(NyctoRegistries.POWER_KEY).fieldOf("powers").forGetter(PowerCountPredicate::powers),
				MinMaxBounds.Ints.CODEC.optionalFieldOf("range", MinMaxBounds.Ints.ANY).forGetter(PowerCountPredicate::range)
		).apply(instance, PowerCountPredicate::new));

		public boolean test(Entity entity) {
			@Nullable TransformationComponent transformationComponent = ModEntityComponents.TRANSFORMATION.getNullable(entity);
			if (transformationComponent != null) {
				int count = 0;
				for (PowerInstance instance : transformationComponent.getPowers()) {
					if (!instance.getPower().isWeakness() && powers.contains(instance.getPower().getHolder())) {
						count++;
					}
				}
				return range.matches(count);
			}
			return false;
		}
	}
}
