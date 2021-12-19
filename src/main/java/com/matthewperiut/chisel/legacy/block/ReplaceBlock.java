package com.matthewperiut.chisel.legacy.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Random;

public class ReplaceBlock extends Block
{
    String block_name;
    public ReplaceBlock(Settings settings, String block_name, String type)
    {
        super(settings);
        this.block_name = block_name;
    }
}
