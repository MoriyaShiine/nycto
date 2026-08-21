package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.common.component.level.AuraComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.init.NyctoEntityTypes;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.nycto.common.world.entity.monster.Hunter;
import moriyashiine.strawberrylib.api.event.AfterDamageIncludingDeathEvent;
import moriyashiine.strawberrylib.api.event.ModifyCriticalStatusEvent;
import moriyashiine.strawberrylib.api.event.PreventHostileTargetingEvent;
import moriyashiine.strawberrylib.api.event.TickEntityEvent;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class HunterEvent {
	public static void init() {
		AfterDamageIncludingDeathEvent.EVENT.register(new Heat());
		TickEntityEvent.EVENT.register(new Aura());
		ModifyCriticalStatusEvent.EVENT.register(new CriticalImmunity());
		PreventHostileTargetingEvent.EVENT.register(new PreventTargeting());
	}

	private static class Heat implements AfterDamageIncludingDeathEvent {
		@Override
		public void afterDamage(LivingEntity victim, DamageSource source, float originalDamage, float modifiedDamage, boolean blocked) {
			if (source.getEntity() instanceof Player player) {
				if (victim.getType() == NyctoEntityTypes.HUNTER) {
					if (victim.isDeadOrDying()) {
						NyctoUtil.notifyNearbyVillagers(victim, player, GossipType.MAJOR_NEGATIVE, 25);
					} else {
						NyctoUtil.notifyNearbyVillagers(victim, player, GossipType.MINOR_NEGATIVE, 5);
					}
				}
				if (NyctoRegistries.HUNTER_TYPE.stream().anyMatch(type -> type.shouldTarget(player))) {
					if (originalDamage > 1 || victim.getRandom().nextBoolean() || victim.isDeadOrDying()) {
						NyctoEntityComponents.HUNTER_HEAT.get(player).maybeIncreaseHeat(victim, victim.isDeadOrDying());
					}
				}
			}
		}
	}

	private static class Aura implements TickEntityEvent {
		@Override
		public void tick(Level level, Entity entity) {
			if (!level.isClientSide() && entity.tickCount % 10 == 0 && entity instanceof LivingEntity living) {
				if (NyctoUtil.hasGarlicAura(living)) {
					AuraComponent.applyGarlicAura(level, living.blockPosition(), 2);
				}
			}
		}
	}

	private static class CriticalImmunity implements ModifyCriticalStatusEvent {
		@Override
		public TriState isCritical(Player attacker, Entity target, float attackCooldownProgress) {
			if (target instanceof LivingEntity living) {
				if (NyctoUtil.hasVampireCriticalHitImmunity(living) && NyctoAPI.isVampire(attacker)) {
					return TriState.FALSE;
				}
			}
			return TriState.DEFAULT;
		}

		@Override
		public int getPriority() {
			return 900;
		}
	}

	private static class PreventTargeting implements PreventHostileTargetingEvent {
		@Override
		public TriState preventsTargeting(LivingEntity attacker, LivingEntity target) {
			if (!(attacker instanceof Enemy) && target instanceof Hunter) {
				return TriState.TRUE;
			}
			return TriState.DEFAULT;
		}
	}
}
