package com.matthewperiut.chisel.block.blocks.array;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class StonesArrayChisel
{
    public static final Block ARRAY_GRANITE = new Block(FabricBlockSettings.copyOf(Blocks.GRANITE));
    public static final Block ARRAY_DIORITE = new Block(FabricBlockSettings.copyOf(Blocks.DIORITE));
    public static final Block ARRAY_ANDESITE = new Block(FabricBlockSettings.copyOf(Blocks.ANDESITE));

    public static void Register()
    {
        Reg("granite", "array/granite", ARRAY_GRANITE);
        Reg("diorite", "array/diorite", ARRAY_DIORITE);
        Reg("andesite", "array/andesite", ARRAY_ANDESITE);
    }
}
