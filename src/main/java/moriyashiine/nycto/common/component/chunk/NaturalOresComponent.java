/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.chunk;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import moriyashiine.nycto.common.init.NyctoChunkComponents;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.ArrayList;
import java.util.List;

public class NaturalOresComponent implements ServerTickingComponent {
	private final ChunkAccess obj;
	private final Long2ObjectMap<ResourceKey<Block>> naturalOres = new Long2ObjectOpenHashMap<>();

	private boolean collected = false;

	public NaturalOresComponent(ChunkAccess obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		naturalOres.clear();
		for (NaturalOre ores : input.read("NaturalOres", NaturalOre.CODEC.listOf()).orElse(List.of())) {
			naturalOres.put(ores.pos(), ores.blockId());
		}
		collected = input.getBooleanOr("Collected", false);
	}

	@Override
	public void writeData(ValueOutput output) {
		List<NaturalOre> ores = new ArrayList<>();
		naturalOres.forEach((pos, block) -> ores.add(new NaturalOre(pos, block)));
		output.store("NaturalOres", NaturalOre.CODEC.listOf(), ores);
		output.putBoolean("Collected", collected);
	}

	@Override
	public void serverTick() {
		if (!collected) {
			collected = true;
			obj.findBlocks(state -> state.is(ConventionalBlockTags.ORES), (pos, state) -> naturalOres.put(pos.asLong(), state.getBlock().builtInRegistryHolder().key()));
		}
	}

	public static @Nullable Block getNaturalOre(LevelAccessor level, BlockPos pos) {
		return NyctoChunkComponents.NATURAL_ORES.get(level.getChunk(pos)).findNaturalOre(level, pos);
	}

	public @Nullable Block findNaturalOre(LevelAccessor level, BlockPos pos) {
		ResourceKey<Block> key = naturalOres.get(pos.asLong());
		if (key == null) {
			return null;
		}
		return level.registryAccess().lookupOrThrow(Registries.BLOCK).get(key).map(Holder.Reference::value).orElse(null);
	}

	private record NaturalOre(long pos, ResourceKey<Block> blockId) {
		public static final Codec<NaturalOre> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				Codec.LONG.fieldOf("pos").forGetter(NaturalOre::pos),
				ResourceKey.codec(Registries.BLOCK).fieldOf("block").forGetter(NaturalOre::blockId)
		).apply(instance, NaturalOre::new));
	}
}
