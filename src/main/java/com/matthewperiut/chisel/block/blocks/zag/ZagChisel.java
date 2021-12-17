package com.matthewperiut.chisel.block.blocks.zag;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class ZagChisel
{
    public static final Block ZAG_SANDSTONE = new Block(FabricBlockSettings.copyOf(Blocks.SANDSTONE));
    public static final Block ZAG_RED_SANDSTONE = new Block(FabricBlockSettings.copyOf(Blocks.RED_SANDSTONE));
    public static final Block ZAG_COBBLESTONE = new Block(FabricBlockSettings.copyOf(Blocks.COBBLESTONE));
    public static final Block ZAG_STONE = new Block(FabricBlockSettings.copyOf(Blocks.STONE));
    //public static final Block ZAG_NETHER_BRICK = new Block(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS));
    //public static final Block ZAG_DEEPSLATE = new Block(FabricBlockSettings.copyOf(Blocks.DEEPSLATE));
    public static final Block ZAG_QUARTZ = new Block(FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK));
    public static final Block ZAG_END_STONE = new Block(FabricBlockSettings.copyOf(Blocks.END_STONE));
    public static final Block ZAG_PURPUR = new Block(FabricBlockSettings.copyOf(Blocks.PURPUR_BLOCK));

    public static void Register()
    {
        Reg("sandstone", "zag/sandstone", ZAG_SANDSTONE);
        Reg("red_sandstone", "zag/red_sandstone", ZAG_RED_SANDSTONE);
        Reg("cobblestone", "zag/cobblestone", ZAG_COBBLESTONE);
        Reg("stone", "zag/stone", ZAG_STONE);
        //Reg("nether_bricks", "zag/nether_brick", ZAG_NETHER_BRICK);
        //Reg("deepslate", "zag/deepslate", ZAG_DEEPSLATE);
        Reg("quartz_block", "zag/quartz", ZAG_QUARTZ);
        Reg("end_stone", "zag/end_stone", ZAG_END_STONE);
        Reg("purpur_block", "zag/purpur", ZAG_PURPUR);
        StonesZagChisel.Register();
    }
}
