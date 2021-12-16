package com.matthewperiut.chisel.block.blocks.slant;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class SlantChisel
{
    public static final Block SLANTED_SANDSTONE = new Block(FabricBlockSettings.copyOf(Blocks.SANDSTONE));
    public static final Block SLANTED_RED_SANDSTONE = new Block(FabricBlockSettings.copyOf(Blocks.RED_SANDSTONE));
    public static final Block SLANTED_COBBLESTONE = new Block(FabricBlockSettings.copyOf(Blocks.COBBLESTONE));
    public static final Block SLANTED_STONE = new Block(FabricBlockSettings.copyOf(Blocks.STONE));
    public static final Block SLANTED_NETHER_BRICK = new Block(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS));
    public static final Block SLANTED_DEEPSLATE = new Block(FabricBlockSettings.copyOf(Blocks.DEEPSLATE));
    public static final Block SLANTED_QUARTZ = new Block(FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK));
    public static final Block SLANTED_END_STONE = new Block(FabricBlockSettings.copyOf(Blocks.END_STONE));
    public static final Block SLANTED_PURPUR = new Block(FabricBlockSettings.copyOf(Blocks.PURPUR_BLOCK));

    public static void Register()
    {
        Reg("sandstone", "slant/sandstone", SLANTED_SANDSTONE);
        Reg("red_sandstone", "slant/red_sandstone", SLANTED_RED_SANDSTONE);
        Reg("cobblestone", "slant/cobblestone", SLANTED_COBBLESTONE);
        Reg("stone", "slant/stone", SLANTED_STONE);
        Reg("nether_bricks", "slant/nether_brick", SLANTED_NETHER_BRICK);
        Reg("deepslate", "slant/deepslate", SLANTED_DEEPSLATE);
        Reg("quartz_block", "slant/quartz", SLANTED_QUARTZ);
        Reg("quartz_block", "slant/end_stone", SLANTED_END_STONE);
        Reg("quartz_block", "slant/purpur", SLANTED_PURPUR);
    }
}
