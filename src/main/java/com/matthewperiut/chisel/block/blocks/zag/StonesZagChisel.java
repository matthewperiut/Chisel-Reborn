package com.matthewperiut.chisel.block.blocks.zag;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class StonesZagChisel
{
    public static final Block ZAG_GRANITE = new Block(FabricBlockSettings.copyOf(Blocks.GRANITE));
    public static final Block ZAG_DIORITE = new Block(FabricBlockSettings.copyOf(Blocks.DIORITE));
    public static final Block ZAG_ANDESITE = new Block(FabricBlockSettings.copyOf(Blocks.ANDESITE));

    public static void Register()
    {
        Reg("granite", "zag/granite", ZAG_GRANITE);
        Reg("diorite", "zag/diorite", ZAG_DIORITE);
        Reg("andesite", "zag/andesite", ZAG_ANDESITE);
    }
}
