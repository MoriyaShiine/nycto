package moriyashiine.nycto.api.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public abstract class UpdatableMobEffect extends MobEffect {
	protected UpdatableMobEffect(MobEffectCategory category, int color) {
		super(category, color);
	}
}
