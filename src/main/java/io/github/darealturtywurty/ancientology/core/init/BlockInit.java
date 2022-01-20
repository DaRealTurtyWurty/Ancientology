package io.github.darealturtywurty.ancientology.core.init;

import net.minecraft.world.level.block.Block;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.util.builder.BlockBuilder;
import io.github.darealturtywurty.ancientology.core.util.builder.BlockBuilder.HarvestLevel;
import io.github.darealturtywurty.ancientology.core.util.builder.BlockBuilder.HarvestTool;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class BlockInit {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			Ancientology.MODID);

	public static final RegistryObject<Block> TEST_BLOCK = BLOCKS.register("test",
			() -> BlockBuilder.of(Block::new).harvestTool(HarvestTool.AXE).harvestLevel(HarvestLevel.DIAMOND).build());

	private BlockInit() {
		throw new IllegalAccessError("Illegal access to hidden initialization class!");
	}
}
