package com.matthewperiut.chisel.block.blocks.circular;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class StonesCircularChisel
{
    public static final Block CIRCULAR_GRANITE = new Block(FabricBlockSettings.copyOf(Blocks.GRANITE));
    public static final Block CIRCULAR_DIORITE = new Block(FabricBlockSettings.copyOf(Blocks.DIORITE));
    public static final Block CIRCULAR_ANDESITE = new Block(FabricBlockSettings.copyOf(Blocks.ANDESITE));

    public static void Register()
    {
        Reg("granite", "circular/granite", CIRCULAR_GRANITE);
        Reg("diorite", "circular/diorite", CIRCULAR_DIORITE);
        Reg("andesite", "circular/andesite", CIRCULAR_ANDESITE);
    }
}
