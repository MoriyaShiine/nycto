/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.MobEffectsPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.Optional;

public class PlayerAppliesEffectsTrigger extends SimpleCriterionTrigger<PlayerAppliesEffectsTrigger.TriggerInstance> {
	@Override
	public Codec<TriggerInstance> codec() {
		return TriggerInstance.CODEC;
	}

	public void trigger(ServerPlayer player, LivingEntity entity) {
		LootContext context = EntityPredicate.createContext(player, entity);
		trigger(player, triggerInstance -> triggerInstance.matches(entity, context));
	}

	public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<MobEffectsPredicate> effects,
								  Optional<ContextAwarePredicate> entity) implements SimpleCriterionTrigger.SimpleInstance {
		public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
								MobEffectsPredicate.CODEC.optionalFieldOf("effects").forGetter(TriggerInstance::effects),
								EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("entity").forGetter(TriggerInstance::entity)
						)
						.apply(instance, TriggerInstance::new));

		public boolean matches(LivingEntity entity, LootContext context) {
			return (effects().isEmpty() || effects().get().matches(entity)) && (entity().isEmpty() || entity().get().matches(context));
		}
	}
}
