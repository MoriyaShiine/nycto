package moriyashiine.nycto.common.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.predicates.MobEffectsPredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

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

	public record TriggerInstance(Optional<Holder<LootItemCondition>> player, Optional<MobEffectsPredicate> effects,
	                              Optional<Holder<LootItemCondition>> entity) implements SimpleCriterionTrigger.SimpleInstance {
		public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootItemCondition.CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
				MobEffectsPredicate.CODEC.optionalFieldOf("effects").forGetter(TriggerInstance::effects),
				LootItemCondition.CODEC.optionalFieldOf("entity").forGetter(TriggerInstance::entity)
		).apply(instance, TriggerInstance::new));

		public boolean matches(LivingEntity entity, LootContext context) {
			return (effects().isEmpty() || effects().get().matches(entity)) && (entity().isEmpty() || entity().get().value().test(context));
		}
	}
}
