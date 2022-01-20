package io.github.darealturtywurty.ancientology.core.util.builder;

import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag.Named;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;

import io.github.darealturtywurty.ancientology.core.data.BlockTagsGenerator;
import net.minecraftforge.common.Tags;

public class BlockBuilder<B extends Block> implements Builder<B> {

	private final Function<BlockBehaviour.Properties, B> factory;
	private Material material = Material.HEAVY_METAL;
	private HarvestLevel harvestLevel;
	private HarvestTool harvestTool;

	private BlockBuilder(Function<Properties, B> factory) {
		this.factory = factory;
	}

	public static <B extends Block> BlockBuilder<B> of(Function<Properties, B> factory) {
		return new BlockBuilder<>(factory);
	}

	public BlockBuilder<B> material(@Nonnull Material material) {
		this.material = material;
		return this;
	}

	public BlockBuilder<B> harvestLevel(@Nonnull HarvestLevel harvestLevel) {
		this.harvestLevel = harvestLevel;
		return this;
	}

	public BlockBuilder<B> harvestTool(@Nonnull HarvestTool harvestTool) {
		this.harvestTool = harvestTool;
		return this;
	}

	@Override
	public B build() {
		final B block = factory.apply(createProperties());
		if (harvestTool != null) {
			BlockTagsGenerator.addHarvestTool(block, harvestTool);
		}
		if (harvestLevel != null) {
			BlockTagsGenerator.addHarvestLevel(block, harvestLevel);
		}
		return block;
	}

	public Properties createProperties() {
		return Properties.of(material);
	}

	public enum HarvestLevel {

		WOOD(() -> Tags.Blocks.NEEDS_WOOD_TOOL), STONE(() -> BlockTags.NEEDS_STONE_TOOL),
		GOLD(() -> Tags.Blocks.NEEDS_GOLD_TOOL), IRON(() -> BlockTags.NEEDS_IRON_TOOL),
		DIAMOND(() -> BlockTags.NEEDS_DIAMOND_TOOL), NETHERITE(() -> Tags.Blocks.NEEDS_NETHERITE_TOOL);

		private final Supplier<Named<Block>> tagSupplier;

		private HarvestLevel(Supplier<Named<Block>> tagSupplier) {
			this.tagSupplier = tagSupplier;
		}

		public Named<Block> getTag() {
			return tagSupplier.get();
		}
	}

	public enum HarvestTool {

		PICKAXE(() -> BlockTags.MINEABLE_WITH_PICKAXE), AXE(() -> BlockTags.MINEABLE_WITH_AXE),
		SHOVEL(() -> BlockTags.MINEABLE_WITH_SHOVEL), HOE(() -> BlockTags.MINEABLE_WITH_HOE);

		private final Supplier<Named<Block>> tagSupplier;

		private HarvestTool(Supplier<Named<Block>> tagSupplier) {
			this.tagSupplier = tagSupplier;
		}

		public Named<Block> getTag() {
			return tagSupplier.get();
		}
	}

}
