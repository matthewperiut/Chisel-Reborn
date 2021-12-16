package com.matthewperiut.chisel.block.blocks.pillar;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;

import static com.matthewperiut.chisel.block.blocks.EzReg.Reg;

public class PillarChisel
{
    public static final PillarBlock SANDSTONE_PILLAR = new PillarBlock(FabricBlockSettings.copyOf(Blocks.SANDSTONE));
    public static final PillarBlock RED_SANDSTONE_PILLAR = new PillarBlock(FabricBlockSettings.copyOf(Blocks.RED_SANDSTONE));
    public static final PillarBlock COBBLESTONE_PILLAR = new PillarBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE));
    public static final PillarBlock STONE_PILLAR = new PillarBlock(FabricBlockSettings.copyOf(Blocks.STONE));
    public static final PillarBlock NETHER_BRICK_PILLAR = new PillarBlock(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS));
    public static final PillarBlock BLACKSTONE_PILLAR = new PillarBlock(FabricBlockSettings.copyOf(Blocks.BLACKSTONE));
    public static final PillarBlock DEEPSLATE_PILLAR = new PillarBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE));
    public static final PillarBlock END_STONE_PILLAR = new PillarBlock(FabricBlockSettings.copyOf(Blocks.END_STONE));

    public static void Register()
    {
        Reg("sandstone", "pillar/sandstone_pillar", SANDSTONE_PILLAR);
        Reg("red_sandstone", "pillar/red_sandstone_pillar", RED_SANDSTONE_PILLAR);
        Reg("cobblestone", "pillar/cobblestone_pillar", COBBLESTONE_PILLAR);
        Reg("stone", "pillar/stone_pillar", STONE_PILLAR);
        Reg("nether_bricks", "pillar/nether_brick_pillar", NETHER_BRICK_PILLAR);
        Reg("blackstone", "pillar/blackstone_pillar", BLACKSTONE_PILLAR);
        Reg("deepslate", "pillar/deepslate_pillar", DEEPSLATE_PILLAR);
        Reg("end_stone", "pillar/end_stone_pillar", END_STONE_PILLAR);
    }
}
