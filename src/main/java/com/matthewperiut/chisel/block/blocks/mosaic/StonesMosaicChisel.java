package com.matthewperiut.chisel.block.blocks.mosaic;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class StonesMosaicChisel
{
    public static final Block MOSAIC_GRANITE = new Block(FabricBlockSettings.copyOf(Blocks.GRANITE));
    public static final Block MOSAIC_DIORITE = new Block(FabricBlockSettings.copyOf(Blocks.DIORITE));
    public static final Block MOSAIC_ANDESITE = new Block(FabricBlockSettings.copyOf(Blocks.ANDESITE));

    public static void Register()
    {
        Reg("granite", "mosaic/granite", MOSAIC_GRANITE);
        Reg("diorite", "mosaic/diorite", MOSAIC_DIORITE);
        Reg("andesite", "mosaic/andesite", MOSAIC_ANDESITE);
    }
}
