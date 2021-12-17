package com.matthewperiut.chisel.block.blocks.tiles_large;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class TilesLargeChisel
{
    public static final Block LARGE_TILE_SANDSTONE = new Block(FabricBlockSettings.copyOf(Blocks.SANDSTONE));
    public static final Block LARGE_TILE_RED_SANDSTONE = new Block(FabricBlockSettings.copyOf(Blocks.RED_SANDSTONE));
    public static final Block LARGE_TILE_COBBLESTONE = new Block(FabricBlockSettings.copyOf(Blocks.COBBLESTONE));
    public static final Block LARGE_TILE_STONE = new Block(FabricBlockSettings.copyOf(Blocks.STONE));
    //public static final Block LARGE_TILE_NETHER_BRICK = new Block(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS));
    //public static final Block LARGE_TILE_DEEPSLATE = new Block(FabricBlockSettings.copyOf(Blocks.DEEPSLATE));
    public static final Block LARGE_TILE_QUARTZ = new Block(FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK));
    public static final Block LARGE_TILE_END_STONE = new Block(FabricBlockSettings.copyOf(Blocks.END_STONE));
    public static final Block LARGE_TILE_PURPUR = new Block(FabricBlockSettings.copyOf(Blocks.PURPUR_BLOCK));

    public static void Register()
    {
        Reg("sandstone", "large_tile/sandstone", LARGE_TILE_SANDSTONE);
        Reg("red_sandstone", "large_tile/red_sandstone", LARGE_TILE_RED_SANDSTONE);
        Reg("cobblestone", "large_tile/cobblestone", LARGE_TILE_COBBLESTONE);
        Reg("stone", "large_tile/stone", LARGE_TILE_STONE);
        //Reg("nether_bricks", "large_tile/nether_brick", LARGE_TILE_NETHER_BRICK);
        //Reg("deepslate", "large_tile/deepslate", LARGE_TILE_DEEPSLATE);
        Reg("quartz_block", "large_tile/quartz", LARGE_TILE_QUARTZ);
        Reg("end_stone", "large_tile/end_stone", LARGE_TILE_END_STONE);
        Reg("purpur_block", "large_tile/purpur", LARGE_TILE_PURPUR);
        StonesTilesLargeChisel.Register();
    }
}
