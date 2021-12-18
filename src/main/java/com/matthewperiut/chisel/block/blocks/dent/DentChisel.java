package com.matthewperiut.chisel.block.blocks.dent;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class DentChisel
{
    public static final Block DENT_SANDSTONE = new Block(FabricBlockSettings.copyOf(Blocks.SANDSTONE));
    public static final Block DENT_RED_SANDSTONE = new Block(FabricBlockSettings.copyOf(Blocks.RED_SANDSTONE));
    public static final Block DENT_COBBLESTONE = new Block(FabricBlockSettings.copyOf(Blocks.COBBLESTONE));
    public static final Block DENT_STONE = new Block(FabricBlockSettings.copyOf(Blocks.STONE));
    //public static final Block DENT_NETHER_BRICK = new Block(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS));
    //public static final Block DENT_DEEPSLATE = new Block(FabricBlockSettings.copyOf(Blocks.DEEPSLATE));
    public static final Block DENT_QUARTZ = new Block(FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK));
    public static final Block DENT_END_STONE = new Block(FabricBlockSettings.copyOf(Blocks.END_STONE));
    public static final Block DENT_PURPUR = new Block(FabricBlockSettings.copyOf(Blocks.PURPUR_BLOCK));
    public static final Block DENT_BRICKS = new Block(FabricBlockSettings.copyOf(Blocks.PURPUR_BLOCK));

    public static void Register()
    {
        Reg("sandstone", "dent/sandstone", DENT_SANDSTONE);
        Reg("red_sandstone", "dent/red_sandstone", DENT_RED_SANDSTONE);
        Reg("cobblestone", "dent/cobblestone", DENT_COBBLESTONE);
        Reg("stone", "dent/stone", DENT_STONE);
        //Reg("nether_bricks", "dent/nether_brick", DENT_NETHER_BRICK);
        //Reg("deepslate", "dent/deepslate", DENT_DEEPSLATE);
        Reg("quartz_block", "dent/quartz", DENT_QUARTZ);
        Reg("end_stone", "dent/end_stone", DENT_END_STONE);
        Reg("purpur_block", "dent/purpur", DENT_PURPUR);
        Reg("bricks", "dent/bricks", DENT_BRICKS);
        StonesDentChisel.Register();
    }
}
