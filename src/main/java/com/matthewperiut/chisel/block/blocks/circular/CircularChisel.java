package com.matthewperiut.chisel.block.blocks.circular;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class CircularChisel
{
    public static final Block CIRCULAR_SANDSTONE = new Block(FabricBlockSettings.copyOf(Blocks.SANDSTONE));
    public static final Block CIRCULAR_RED_SANDSTONE = new Block(FabricBlockSettings.copyOf(Blocks.RED_SANDSTONE));
    public static final Block CIRCULAR_COBBLESTONE = new Block(FabricBlockSettings.copyOf(Blocks.COBBLESTONE));
    public static final Block CIRCULAR_STONE = new Block(FabricBlockSettings.copyOf(Blocks.STONE));
    //public static final Block CIRCULAR_NETHER_BRICK = new Block(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS));
    //public static final Block CIRCULAR_DEEPSLATE = new Block(FabricBlockSettings.copyOf(Blocks.DEEPSLATE));
    public static final Block CIRCULAR_QUARTZ = new Block(FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK));
    public static final Block CIRCULAR_END_STONE = new Block(FabricBlockSettings.copyOf(Blocks.END_STONE));
    public static final Block CIRCULAR_PURPUR = new Block(FabricBlockSettings.copyOf(Blocks.PURPUR_BLOCK));

    public static void Register()
    {
        Reg("sandstone", "circular/sandstone", CIRCULAR_SANDSTONE);
        Reg("red_sandstone", "circular/red_sandstone", CIRCULAR_RED_SANDSTONE);
        Reg("cobblestone", "circular/cobblestone", CIRCULAR_COBBLESTONE);
        Reg("stone", "circular/stone", CIRCULAR_STONE);
        //Reg("nether_bricks", "circular/nether_brick", CIRCULAR_NETHER_BRICK);
        //Reg("deepslate", "circular/deepslate", CIRCULAR_DEEPSLATE);
        Reg("quartz_block", "circular/quartz", CIRCULAR_QUARTZ);
        Reg("end_stone", "circular/end_stone", CIRCULAR_END_STONE);
        Reg("purpur_block", "circular/purpur", CIRCULAR_PURPUR);
        StonesCircularChisel.Register();
    }
}
