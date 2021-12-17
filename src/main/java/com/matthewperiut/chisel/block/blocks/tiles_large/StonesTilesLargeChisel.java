package com.matthewperiut.chisel.block.blocks.tiles_large;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class StonesTilesLargeChisel
{
    public static final Block LARGE_TILE_GRANITE = new Block(FabricBlockSettings.copyOf(Blocks.GRANITE));
    public static final Block LARGE_TILE_DIORITE = new Block(FabricBlockSettings.copyOf(Blocks.DIORITE));
    public static final Block LARGE_TILE_ANDESITE = new Block(FabricBlockSettings.copyOf(Blocks.ANDESITE));

    public static void Register()
    {
        Reg("granite", "large_tile/granite", LARGE_TILE_GRANITE);
        Reg("diorite", "large_tile/diorite", LARGE_TILE_DIORITE);
        Reg("andesite", "large_tile/andesite", LARGE_TILE_ANDESITE);
    }
}
