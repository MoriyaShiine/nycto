package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.api.NyctoAPI;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.level.ServerPlayer;

public class TransformationEvent implements ServerPlayerEvents.CopyFrom {
	public static void init() {
		ServerPlayerEvents.COPY_FROM.register(new TransformationEvent());
	}

	@Override
	public void copyFromPlayer(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean alive) {
		NyctoAPI.getTransformation(oldPlayer).getAttributeModifiers(oldPlayer).attributeModifiers().forEach(pair -> newPlayer.getAttribute(pair.getFirst()).addPermanentModifier(pair.getSecond()));
	}
}
