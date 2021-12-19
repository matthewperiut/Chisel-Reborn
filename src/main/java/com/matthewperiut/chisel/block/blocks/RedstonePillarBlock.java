package com.matthewperiut.chisel.block.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.RedstoneBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class RedstonePillarBlock extends PillarBlock
{
    public RedstonePillarBlock(Settings settings)
    {
        super(settings);
    }
    public boolean emitsRedstonePower(BlockState state)
    {
        return true;
    }

    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction)
    {
        return 15;
    }
}
