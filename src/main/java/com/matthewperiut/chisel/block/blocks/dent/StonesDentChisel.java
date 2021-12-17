package com.matthewperiut.chisel.block.blocks.dent;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class StonesDentChisel
{
    public static final Block DENT_GRANITE = new Block(FabricBlockSettings.copyOf(Blocks.GRANITE));
    public static final Block DENT_DIORITE = new Block(FabricBlockSettings.copyOf(Blocks.DIORITE));
    public static final Block DENT_ANDESITE = new Block(FabricBlockSettings.copyOf(Blocks.ANDESITE));

    public static void Register()
    {
        Reg("granite", "dent/granite", DENT_GRANITE);
        Reg("diorite", "dent/diorite", DENT_DIORITE);
        Reg("andesite", "dent/andesite", DENT_ANDESITE);
    }
}
