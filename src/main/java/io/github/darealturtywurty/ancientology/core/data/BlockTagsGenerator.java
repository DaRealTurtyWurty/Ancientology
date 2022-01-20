package io.github.darealturtywurty.ancientology.core.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.apache.commons.compress.utils.Lists;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.world.level.block.Block;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.util.builder.BlockBuilder.HarvestLevel;
import io.github.darealturtywurty.ancientology.core.util.builder.BlockBuilder.HarvestTool;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockTagsGenerator extends BlockTagsProvider {

	private static final Map<HarvestLevel, List<Block>> HARVEST_LEVELS = new HashMap<>();

	public static void addHarvestLevel(@Nonnull final Block block, @Nonnull final HarvestLevel harvestLevel) {
		HARVEST_LEVELS.computeIfAbsent(harvestLevel, k -> Lists.newArrayList()).add(block);
	}

	private static final Map<HarvestTool, List<Block>> HARVEST_TOOLS = new HashMap<>();

	public static void addHarvestTool(@Nonnull final Block block, @Nonnull final HarvestTool harvestTool) {
		HARVEST_TOOLS.computeIfAbsent(harvestTool, k -> Lists.newArrayList()).add(block);
	}

	public BlockTagsGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Ancientology.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		HARVEST_TOOLS.forEach((tool, blocks) -> tag(tool.getTag()).add(blocks.toArray(Block[]::new)));
		HARVEST_LEVELS.forEach((level, blocks) -> tag(level.getTag()).add(blocks.toArray(Block[]::new)));
	}
}
