package com.matthewperiut.chisel.block.blocks.cracked;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class StonesCrackedChisel
{
    public static final Block CRACKED_GRANITE = new Block(FabricBlockSettings.copyOf(Blocks.GRANITE));
    public static final Block CRACKED_DIORITE = new Block(FabricBlockSettings.copyOf(Blocks.DIORITE));
    public static final Block CRACKED_ANDESITE = new Block(FabricBlockSettings.copyOf(Blocks.ANDESITE));

    public static void Register()
    {
        Reg("granite", "cracked/granite", CRACKED_GRANITE);
        Reg("diorite", "cracked/diorite", CRACKED_DIORITE);
        Reg("andesite", "cracked/andesite", CRACKED_ANDESITE);
    }
}
