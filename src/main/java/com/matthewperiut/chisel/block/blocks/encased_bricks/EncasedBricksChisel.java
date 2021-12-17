package com.matthewperiut.chisel.block.blocks.encased_bricks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class EncasedBricksChisel
{
    public static final Block ENCASED_BRICKS_SANDSTONE = new Block(FabricBlockSettings.copyOf(Blocks.SANDSTONE));
    public static final Block ENCASED_BRICKS_RED_SANDSTONE = new Block(FabricBlockSettings.copyOf(Blocks.RED_SANDSTONE));
    public static final Block ENCASED_BRICKS_COBBLESTONE = new Block(FabricBlockSettings.copyOf(Blocks.COBBLESTONE));
    public static final Block ENCASED_BRICKS_STONE = new Block(FabricBlockSettings.copyOf(Blocks.STONE));
    //public static final Block ENCASED_BRICKS_NETHER_BRICK = new Block(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS));
    //public static final Block ENCASED_BRICKS_DEEPSLATE = new Block(FabricBlockSettings.copyOf(Blocks.DEEPSLATE));
    public static final Block ENCASED_BRICKS_QUARTZ = new Block(FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK));
    public static final Block ENCASED_BRICKS_END_STONE = new Block(FabricBlockSettings.copyOf(Blocks.END_STONE));
    public static final Block ENCASED_BRICKS_PURPUR = new Block(FabricBlockSettings.copyOf(Blocks.PURPUR_BLOCK));

    public static void Register()
    {
        Reg("sandstone", "encased_bricks/sandstone", ENCASED_BRICKS_SANDSTONE);
        Reg("red_sandstone", "encased_bricks/red_sandstone", ENCASED_BRICKS_RED_SANDSTONE);
        Reg("cobblestone", "encased_bricks/cobblestone", ENCASED_BRICKS_COBBLESTONE);
        Reg("stone", "encased_bricks/stone", ENCASED_BRICKS_STONE);
        //Reg("nether_bricks", "encased_bricks/nether_brick", ENCASED_BRICKS_NETHER_BRICK);
        //Reg("deepslate", "encased_bricks/deepslate", ENCASED_BRICKS_DEEPSLATE);
        Reg("quartz_block", "encased_bricks/quartz", ENCASED_BRICKS_QUARTZ);
        Reg("end_stone", "encased_bricks/end_stone", ENCASED_BRICKS_END_STONE);
        Reg("purpur_block", "encased_bricks/purpur", ENCASED_BRICKS_PURPUR);
    }
}
