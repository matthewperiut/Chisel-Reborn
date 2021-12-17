package com.matthewperiut.chisel.block.blocks.cut;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class StonesCutChisel
{
    public static final Block CUT_GRANITE = new Block(FabricBlockSettings.copyOf(Blocks.GRANITE));
    public static final Block CUT_DIORITE = new Block(FabricBlockSettings.copyOf(Blocks.DIORITE));
    public static final Block CUT_ANDESITE = new Block(FabricBlockSettings.copyOf(Blocks.ANDESITE));

    public static void Register()
    {
        Reg("granite", "cut/granite", CUT_GRANITE);
        Reg("diorite", "cut/diorite", CUT_DIORITE);
        Reg("andesite", "cut/andesite", CUT_ANDESITE);
    }
}
