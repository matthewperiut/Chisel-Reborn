package com.matthewperiut.chisel.block.blocks.array;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class ArrayChisel
{
    public static final Block ARRAY_SANDSTONE = new Block(FabricBlockSettings.copyOf(Blocks.SANDSTONE));
    public static final Block ARRAY_RED_SANDSTONE = new Block(FabricBlockSettings.copyOf(Blocks.RED_SANDSTONE));
    public static final Block ARRAY_COBBLESTONE = new Block(FabricBlockSettings.copyOf(Blocks.COBBLESTONE));
    public static final Block ARRAY_STONE = new Block(FabricBlockSettings.copyOf(Blocks.STONE));
    //public static final Block ARRAY_NETHER_BRICK = new Block(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS));
    //public static final Block ARRAY_DEEPSLATE = new Block(FabricBlockSettings.copyOf(Blocks.DEEPSLATE));
    public static final Block ARRAY_QUARTZ = new Block(FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK));
    public static final Block ARRAY_END_STONE = new Block(FabricBlockSettings.copyOf(Blocks.END_STONE));
    public static final Block ARRAY_PURPUR = new Block(FabricBlockSettings.copyOf(Blocks.PURPUR_BLOCK));
    public static final Block ARRAY_BRICKS = new Block(FabricBlockSettings.copyOf(Blocks.BRICKS));

    public static void Register()
    {
        Reg("sandstone", "array/sandstone", ARRAY_SANDSTONE);
        Reg("red_sandstone", "array/red_sandstone", ARRAY_RED_SANDSTONE);
        Reg("cobblestone", "array/cobblestone", ARRAY_COBBLESTONE);
        Reg("stone", "array/stone", ARRAY_STONE);
        //Reg("nether_bricks", "array/nether_brick", ARRAY_NETHER_BRICK);
        //Reg("deepslate", "array/deepslate", ARRAY_DEEPSLATE);
        Reg("quartz_block", "array/quartz", ARRAY_QUARTZ);
        Reg("end_stone", "array/end_stone", ARRAY_END_STONE);
        Reg("purpur_block", "array/purpur", ARRAY_PURPUR);
        Reg("bricks", "array/bricks", ARRAY_BRICKS);
        StonesArrayChisel.Register();
    }
}
