package com.matthewperiut.chisel.block.blocks.cut;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class CutChisel
{
    public static final Block CUT_SANDSTONE = new Block(FabricBlockSettings.copyOf(Blocks.SANDSTONE));
    public static final Block CUT_RED_SANDSTONE = new Block(FabricBlockSettings.copyOf(Blocks.RED_SANDSTONE));
    public static final Block CUT_COBBLESTONE = new Block(FabricBlockSettings.copyOf(Blocks.COBBLESTONE));
    public static final Block CUT_STONE = new Block(FabricBlockSettings.copyOf(Blocks.STONE));
    //public static final Block CUT_NETHER_BRICK = new Block(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS));
    //public static final Block CUT_DEEPSLATE = new Block(FabricBlockSettings.copyOf(Blocks.DEEPSLATE));
    public static final Block CUT_QUARTZ = new Block(FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK));
    public static final Block CUT_END_STONE = new Block(FabricBlockSettings.copyOf(Blocks.END_STONE));
    public static final Block CUT_PURPUR = new Block(FabricBlockSettings.copyOf(Blocks.PURPUR_BLOCK));
    public static final Block CUT_BRICKS = new Block(FabricBlockSettings.copyOf(Blocks.PURPUR_BLOCK));

    public static void Register()
    {
        Reg("sandstone", "cut/sandstone", CUT_SANDSTONE);
        Reg("red_sandstone", "cut/red_sandstone", CUT_RED_SANDSTONE);
        Reg("cobblestone", "cut/cobblestone", CUT_COBBLESTONE);
        Reg("stone", "cut/stone", CUT_STONE);
        //Reg("nether_bricks", "cut/nether_brick", CUT_NETHER_BRICK);
        //Reg("deepslate", "cut/deepslate", CUT_DEEPSLATE);
        Reg("quartz_block", "cut/quartz", CUT_QUARTZ);
        Reg("end_stone", "cut/end_stone", CUT_END_STONE);
        Reg("purpur_block", "cut/purpur", CUT_PURPUR);
        Reg("bricks", "cut/bricks", CUT_BRICKS);
        StonesCutChisel.Register();
    }
}
