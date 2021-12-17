package com.matthewperiut.chisel.block.blocks.slant;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class StonesSlantChisel
{
    public static final Block SLANTED_GRANITE = new Block(FabricBlockSettings.copyOf(Blocks.GRANITE));
    public static final Block SLANTED_DIORITE = new Block(FabricBlockSettings.copyOf(Blocks.DIORITE));
    public static final Block SLANTED_ANDESITE = new Block(FabricBlockSettings.copyOf(Blocks.ANDESITE));

    public static void Register()
    {
        Reg("granite", "slant/granite", SLANTED_GRANITE);
        Reg("diorite", "slant/diorite", SLANTED_DIORITE);
        Reg("andesite", "slant/andesite", SLANTED_ANDESITE);
    }
}
