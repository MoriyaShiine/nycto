package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageType;

public class NyctoDamageTypes {
	public static final ResourceKey<DamageType> BLEED = ResourceKey.create(Registries.DAMAGE_TYPE, Nycto.id("bleed"));
	public static final ResourceKey<DamageType> SUN = ResourceKey.create(Registries.DAMAGE_TYPE, Nycto.id("sun"));
	public static final ResourceKey<DamageType> TOXIC_TOUCH = ResourceKey.create(Registries.DAMAGE_TYPE, Nycto.id("toxic_touch"));
	public static final ResourceKey<DamageType> WOODEN_STAKE_FALL = ResourceKey.create(Registries.DAMAGE_TYPE, Nycto.id("wooden_stake"));

	public static void bootstrap(BootstrapContext<DamageType> registry) {
		registry.register(BLEED, new DamageType("nycto.bleed", 0));
		registry.register(SUN, new DamageType("onFire", 0, DamageEffects.BURNING));
		registry.register(TOXIC_TOUCH, new DamageType("nycto.toxic_touch", 0));
		registry.register(WOODEN_STAKE_FALL, new DamageType("nycto.wooden_stake_fall", 0));
	}
}
