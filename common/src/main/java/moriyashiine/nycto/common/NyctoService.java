package moriyashiine.nycto.common;

import net.minecraft.world.entity.player.Player;

import java.util.NoSuchElementException;
import java.util.ServiceLoader;

public interface NyctoService {
	NyctoService INSTANCE = ServiceLoader.load(NyctoService.class, NyctoService.class.getClassLoader()).findFirst().orElseThrow(() -> new NoSuchElementException("Unable to load %s service!".formatted(NyctoService.class.getName())));

	default void initNeoForge() {
	}

	void applyBatFormAbilities(Player player, boolean grant);
}
