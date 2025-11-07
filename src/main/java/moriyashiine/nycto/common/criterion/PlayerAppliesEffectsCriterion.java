/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.entity.LivingEntity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.EntityEffectPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Optional;

public class PlayerAppliesEffectsCriterion extends AbstractCriterion<PlayerAppliesEffectsCriterion.Conditions> {
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}

	public void trigger(ServerPlayerEntity player, LivingEntity entity) {
		LootContext lootContext = EntityPredicate.createAdvancementEntityLootContext(player, entity);
		trigger(player, conditions -> conditions.matches(entity, lootContext));
	}

	public record Conditions(Optional<LootContextPredicate> player, Optional<EntityEffectPredicate> effects,
							 Optional<LootContextPredicate> entity) implements AbstractCriterion.Conditions {
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player").forGetter(Conditions::player),
								EntityEffectPredicate.CODEC.optionalFieldOf("effects").forGetter(Conditions::effects),
								EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("entity").forGetter(Conditions::entity)
						)
						.apply(instance, Conditions::new));

		public boolean matches(LivingEntity entity, LootContext context) {
			return (effects().isEmpty() || effects().get().test(entity)) && (entity().isEmpty() || entity().get().test(context));
		}
	}
}
