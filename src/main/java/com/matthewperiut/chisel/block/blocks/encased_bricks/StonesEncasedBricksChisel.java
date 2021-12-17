package com.matthewperiut.chisel.block.blocks.encased_bricks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class StonesEncasedBricksChisel
{
    public static final Block ENCASED_BRICKS_GRANITE = new Block(FabricBlockSettings.copyOf(Blocks.GRANITE));
    public static final Block ENCASED_BRICKS_DIORITE = new Block(FabricBlockSettings.copyOf(Blocks.DIORITE));
    public static final Block ENCASED_BRICKS_ANDESITE = new Block(FabricBlockSettings.copyOf(Blocks.ANDESITE));

    public static void Register()
    {
        Reg("granite", "encased_bricks/granite", ENCASED_BRICKS_GRANITE);
        Reg("diorite", "encased_bricks/diorite", ENCASED_BRICKS_DIORITE);
        Reg("andesite", "encased_bricks/andesite", ENCASED_BRICKS_ANDESITE);
    }
}
