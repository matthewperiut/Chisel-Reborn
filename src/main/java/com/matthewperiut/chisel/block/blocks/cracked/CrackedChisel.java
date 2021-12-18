package com.matthewperiut.chisel.block.blocks.cracked;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class CrackedChisel
{
    public static final Block CRACKED_SANDSTONE = new Block(FabricBlockSettings.copyOf(Blocks.SANDSTONE));
    public static final Block CRACKED_RED_SANDSTONE = new Block(FabricBlockSettings.copyOf(Blocks.RED_SANDSTONE));
    public static final Block CRACKED_COBBLESTONE = new Block(FabricBlockSettings.copyOf(Blocks.COBBLESTONE));
    public static final Block CRACKED_STONE = new Block(FabricBlockSettings.copyOf(Blocks.STONE));
    //public static final Block CRACKED_NETHER_BRICK = new Block(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS));
    //public static final Block CRACKED_DEEPSLATE = new Block(FabricBlockSettings.copyOf(Blocks.DEEPSLATE));
    public static final Block CRACKED_QUARTZ = new Block(FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK));
    public static final Block CRACKED_END_STONE = new Block(FabricBlockSettings.copyOf(Blocks.END_STONE));
    public static final Block CRACKED_PURPUR = new Block(FabricBlockSettings.copyOf(Blocks.PURPUR_BLOCK));
    public static final Block CRACKED_BRICKS = new Block(FabricBlockSettings.copyOf(Blocks.BRICKS));

    public static void Register()
    {
        Reg("sandstone", "cracked/sandstone", CRACKED_SANDSTONE);
        Reg("red_sandstone", "cracked/red_sandstone", CRACKED_RED_SANDSTONE);
        Reg("cobblestone", "cracked/cobblestone", CRACKED_COBBLESTONE);
        Reg("stone", "cracked/stone", CRACKED_STONE);
        //Reg("nether_bricks", "cracked/nether_brick", CRACKED_NETHER_BRICK);
        //Reg("deepslate", "cracked/deepslate", CRACKED_DEEPSLATE);
        Reg("quartz_block", "cracked/quartz", CRACKED_QUARTZ);
        Reg("end_stone", "cracked/end_stone", CRACKED_END_STONE);
        Reg("purpur_block", "cracked/purpur", CRACKED_PURPUR);
        Reg("bricks", "cracked/bricks", CRACKED_BRICKS);
        StonesCrackedChisel.Register();
    }
}
