package com.matthewperiut.chisel.block.blocks.pillar;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class StonesPillarChisel
{
    public static final PillarBlock GRANITE_PILLAR = new PillarBlock(FabricBlockSettings.copyOf(Blocks.GRANITE));
    public static final PillarBlock DIORITE_PILLAR = new PillarBlock(FabricBlockSettings.copyOf(Blocks.DIORITE));
    public static final PillarBlock ANDESITE_PILLAR = new PillarBlock(FabricBlockSettings.copyOf(Blocks.ANDESITE));

    public static void Register()
    {
        Reg("granite", "pillar/granite", GRANITE_PILLAR);
        Reg("diorite", "pillar/diorite", DIORITE_PILLAR);
        Reg("andesite", "pillar/andesite", ANDESITE_PILLAR);
    }
}
