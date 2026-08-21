package moriyashiine.nycto.mixin.vampire;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.world.power.vampire.weakness.VilePresenceWeakness;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEntityTypeTags;
import net.minecraft.core.Holder;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	@Shadow
	public abstract double getAttributeBaseValue(Holder<Attribute> attribute);

	public LivingEntityMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@SuppressWarnings("ConstantValue")
	@ModifyReturnValue(method = "getVisibilityPercent", at = @At("RETURN"))
	private double nycto$vampire(double original, Entity targetingEntity) {
		if (targetingEntity != null && !targetingEntity.is(ConventionalEntityTypeTags.BOSSES) && targetingEntity.is(EntityTypeTags.UNDEAD) && NyctoAPI.isVampire(this)) {
			if ((Object) this instanceof Player player && VilePresenceWeakness.shouldApply(player)) {
				return original;
			}
			return original / 4;
		}
		return original;
	}

	@ModifyReturnValue(method = "getAttributeValue", at = @At("RETURN"))
	private double nycto$vampire(double original, Holder<Attribute> attribute) {
		return attribute == Attributes.BURNING_TIME && NyctoAPI.isVampire(this) ? getAttributeBaseValue(attribute) : original;
	}
}
