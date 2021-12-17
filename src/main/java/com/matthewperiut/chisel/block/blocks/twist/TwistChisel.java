package com.matthewperiut.chisel.block.blocks.twist;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class TwistChisel
{
    public static final PillarBlock TWIST_SANDSTONE = new PillarBlock(FabricBlockSettings.copyOf(Blocks.SANDSTONE));
    public static final PillarBlock TWIST_RED_SANDSTONE = new PillarBlock(FabricBlockSettings.copyOf(Blocks.RED_SANDSTONE));
    public static final PillarBlock TWIST_COBBLESTONE = new PillarBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE));
    public static final PillarBlock TWIST_STONE = new PillarBlock(FabricBlockSettings.copyOf(Blocks.STONE));
    //public static final PillarBlock TWIST_NETHER_BRICK = new PillarBlock(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS));
    //public static final PillarBlock TWIST_BLACKSTONE = new PillarBlock(FabricBlockSettings.copyOf(Blocks.BLACKSTONE));
    //public static final PillarBlock TWIST_DEEPSLATE = new PillarBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE));
    public static final PillarBlock TWIST_QUARTZ = new PillarBlock(FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK));
    public static final PillarBlock TWIST_END_STONE = new PillarBlock(FabricBlockSettings.copyOf(Blocks.END_STONE));
    public static final PillarBlock TWIST_PURPUR = new PillarBlock(FabricBlockSettings.copyOf(Blocks.END_STONE));

    public static void Register()
    {
        Reg("sandstone", "twist/sandstone", TWIST_SANDSTONE);
        Reg("red_sandstone", "twist/red_sandstone", TWIST_RED_SANDSTONE);
        Reg("cobblestone", "twist/cobblestone", TWIST_COBBLESTONE);
        Reg("stone", "twist/stone", TWIST_STONE);
        //Reg("nether_bricks", "twist/nether_brick", TWIST_NETHER_BRICK);
        //Reg("blackstone", "twist/blackstone", TWIST_BLACKSTONE);
        //Reg("deepslate", "twist/deepslate", TWIST_DEEPSLATE);
        Reg("quartz_block", "twist/quartz", TWIST_QUARTZ);
        Reg("end_stone", "twist/end_stone", TWIST_END_STONE);
        Reg("purpur_block", "twist/purpur", TWIST_PURPUR);
        StonesTwistChisel.Register();
    }
}
