/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.entity.subpredicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.power.Power;
import moriyashiine.nycto.api.power.PowerInstance;
import moriyashiine.nycto.common.component.entity.TransformationComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.Entity;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.EntitySubPredicate;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record VampirePredicate(Optional<Boolean> vampire,
							   Optional<PowerCountPredicate> count) implements EntitySubPredicate {
	public static final MapCodec<VampirePredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.BOOL.optionalFieldOf("vampire").forGetter(VampirePredicate::vampire),
			PowerCountPredicate.CODEC.optionalFieldOf("count").forGetter(VampirePredicate::count)
	).apply(instance, VampirePredicate::new));

	@Override
	public MapCodec<? extends EntitySubPredicate> getCodec() {
		return CODEC;
	}

	@Override
	public boolean test(Entity entity, ServerWorld world, @Nullable Vec3d pos) {
		if (vampire().map(b -> b != NyctoAPI.isVampire(entity)).orElse(false)) {
			return false;
		}
		return count().map(b -> b.test(entity)).orElse(true);
	}

	public record PowerCountPredicate(RegistryEntryList<Power> powers, NumberRange.IntRange range) {
		public static final Codec<PowerCountPredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				RegistryCodecs.entryList(NyctoRegistries.POWER_KEY).fieldOf("powers").forGetter(PowerCountPredicate::powers),
				NumberRange.IntRange.CODEC.optionalFieldOf("range", NumberRange.IntRange.ANY).forGetter(PowerCountPredicate::range)
		).apply(instance, PowerCountPredicate::new));

		public boolean test(Entity entity) {
			@Nullable TransformationComponent transformationComponent = ModEntityComponents.TRANSFORMATION.getNullable(entity);
			if (transformationComponent != null) {
				int count = 0;
				for (PowerInstance instance : transformationComponent.getPowers()) {
					if (!instance.getPower().isNegative() && powers.contains(instance.getPower().getEntry())) {
						count++;
					}
				}
				return range.test(count);
			}
			return false;
		}
	}
}
