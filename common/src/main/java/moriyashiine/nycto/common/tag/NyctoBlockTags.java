package moriyashiine.nycto.common.tag;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class NyctoBlockTags {
	public static final TagKey<Block> BEAST_MINEABLE = TagKey.create(Registries.BLOCK, Nycto.id("beast_mineable"));
	public static final TagKey<Block> COFFINS = TagKey.create(Registries.BLOCK, Nycto.id("coffins"));
	public static final TagKey<Block> HURTS_VAMPIRES = TagKey.create(Registries.BLOCK, Nycto.id("hurts_vampires"));
	public static final TagKey<Block> MIST_FORM_UNPASSABLE = TagKey.create(Registries.BLOCK, Nycto.id("mist_form_unpassable"));

	public static final TagKey<Block> THRALL_DOES_NOT_TELEPORT_TO = TagKey.create(Registries.BLOCK, Nycto.id("thrall_does_not_teleport_to"));
	public static final TagKey<Block> HUNTER_DOES_NOT_TELEPORT_TO = TagKey.create(Registries.BLOCK, Nycto.id("hunter_does_not_teleport_to"));
}
