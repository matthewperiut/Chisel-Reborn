package com.matthewperiut.chisel.block.blocks.mosaic;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class MosaicChisel
{
    public static final Block MOSAIC_SANDSTONE = new Block(FabricBlockSettings.copyOf(Blocks.SANDSTONE));
    public static final Block MOSAIC_RED_SANDSTONE = new Block(FabricBlockSettings.copyOf(Blocks.RED_SANDSTONE));
    public static final Block MOSAIC_COBBLESTONE = new Block(FabricBlockSettings.copyOf(Blocks.COBBLESTONE));
    public static final Block MOSAIC_STONE = new Block(FabricBlockSettings.copyOf(Blocks.STONE));
    public static final Block MOSAIC_NETHER_BRICK = new Block(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS));
    public static final Block MOSAIC_DEEPSLATE = new Block(FabricBlockSettings.copyOf(Blocks.DEEPSLATE));
    public static final Block MOSAIC_QUARTZ = new Block(FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK));
    public static final Block MOSAIC_END_STONE = new Block(FabricBlockSettings.copyOf(Blocks.END_STONE));
    public static final Block MOSAIC_PURPUR = new Block(FabricBlockSettings.copyOf(Blocks.PURPUR_BLOCK));

    public static void Register()
    {
        Reg("sandstone", "mosaic/sandstone", MOSAIC_SANDSTONE); //
        Reg("red_sandstone", "mosaic/red_sandstone", MOSAIC_RED_SANDSTONE); //
        Reg("cobblestone", "mosaic/cobblestone", MOSAIC_COBBLESTONE); //
        Reg("stone", "mosaic/stone", MOSAIC_STONE); //
        Reg("nether_bricks", "mosaic/nether_brick", MOSAIC_NETHER_BRICK);
        Reg("deepslate", "mosaic/deepslate", MOSAIC_DEEPSLATE);
        Reg("quartz_block", "mosaic/quartz", MOSAIC_QUARTZ); //
        Reg("end_stone", "mosaic/end_stone", MOSAIC_END_STONE);
        Reg("purpur_block", "mosaic/purpur", MOSAIC_PURPUR);
    }
}
