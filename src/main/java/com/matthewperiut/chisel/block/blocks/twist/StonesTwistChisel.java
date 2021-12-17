package com.matthewperiut.chisel.block.blocks.twist;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class StonesTwistChisel
{
    public static final PillarBlock TWIST_GRANITE_PILLAR = new PillarBlock(FabricBlockSettings.copyOf(Blocks.GRANITE));
    public static final PillarBlock TWIST_DIORITE_PILLAR = new PillarBlock(FabricBlockSettings.copyOf(Blocks.DIORITE));
    public static final PillarBlock TWIST_ANDESITE_PILLAR = new PillarBlock(FabricBlockSettings.copyOf(Blocks.ANDESITE));

    public static void Register()
    {
        Reg("granite", "twist/granite", TWIST_GRANITE_PILLAR);
        Reg("diorite", "twist/diorite", TWIST_DIORITE_PILLAR);
        Reg("andesite", "twist/andesite", TWIST_ANDESITE_PILLAR);
    }
}
