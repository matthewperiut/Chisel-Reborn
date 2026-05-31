package com.periut.chisel.block.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

public class RedstonePillarBlock extends RotatedPillarBlock
{
    public RedstonePillarBlock(Properties settings)
    {
        super(settings);
    }
    public boolean isSignalSource(BlockState state)
    {
        return true;
    }

    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction)
    {
        return 15;
    }
}
