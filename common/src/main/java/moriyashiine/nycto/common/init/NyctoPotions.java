package moriyashiine.nycto.common.init;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerPotion;

public class NyctoPotions {
	public static final Holder<Potion> WITHER = registerPotion("wither", new Potion("nycto.wither", new MobEffectInstance(MobEffects.WITHER, 400)));
	public static final Holder<Potion> LONG_WITHER = registerPotion("long_wither", new Potion("nycto.wither", new MobEffectInstance(MobEffects.WITHER, 800)));
	public static final Holder<Potion> STRONG_WITHER = registerPotion("strong_wither", new Potion("nycto.wither", new MobEffectInstance(MobEffects.WITHER, 200, 1)));

	public static final Holder<Potion> GARLIC = registerPotion("garlic", new Potion("nycto.garlic", new MobEffectInstance(MobEffects.INSTANT_HEALTH, 1), new MobEffectInstance(NyctoMobEffects.VAMPIRE_WARD, 400)));
	public static final Holder<Potion> LONG_GARLIC = registerPotion("long_garlic", new Potion("nycto.garlic", new MobEffectInstance(MobEffects.INSTANT_HEALTH, 1), new MobEffectInstance(NyctoMobEffects.VAMPIRE_WARD, 800)));
	public static final Holder<Potion> STRONG_GARLIC = registerPotion("strong_garlic", new Potion("nycto.garlic", new MobEffectInstance(MobEffects.INSTANT_HEALTH, 1, 1), new MobEffectInstance(NyctoMobEffects.VAMPIRE_WARD, 400)));

	public static void init() {
	}
}
