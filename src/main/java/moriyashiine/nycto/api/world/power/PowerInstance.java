/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.api.world.power;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.nycto.api.init.NyctoRegistries;
import net.minecraft.tags.TagKey;

public class PowerInstance {
	public static final Codec<PowerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			NyctoRegistries.POWER.byNameCodec().fieldOf("power").forGetter(PowerInstance::getPower),
			Codec.INT.fieldOf("cooldown").forGetter(PowerInstance::getCooldown)
	).apply(instance, PowerInstance::new));

	private final Power power;
	private int cooldown;

	public PowerInstance(Power power, int cooldown) {
		this.power = power;
		this.cooldown = cooldown;
	}

	public PowerInstance(Power power) {
		this(power, 0);
	}

	public Power getPower() {
		return power;
	}

	public int getCooldown() {
		return cooldown;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	public final boolean is(TagKey<Power> tagKey) {
		return getPower().is(tagKey);
	}
}
