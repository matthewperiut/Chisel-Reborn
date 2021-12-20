package com.matthewperiut.chisel.legacy.block;

import com.matthewperiut.chisel.Chisel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

// I can't wait to remove this terribleness

public class ReplaceBlock extends Block
{
    Block baseBlock;
    public ReplaceBlock(Settings settings, String block_name, String type)
    {
        super(settings);

        String vanilla_name = block_name;

        type = type.replace("_planks","");

        block_name = type + "/" + block_name;
        String original = block_name;
        block_name = block_name.replace("cuts","cut");
        block_name = block_name.replace("-top","");
        block_name = block_name.replace("-side","");
        block_name = block_name.replace("twisted","twist");
        block_name = block_name.replace("slanted","slant");
        block_name = block_name.replace("_1","");
        block_name = block_name.replace("_2","");
        block_name = block_name.replace("purpur_block","purpur");
        block_name = block_name.replace("quartz_block","quartz");
        block_name = block_name.replace("tiles_large","large_tile");
        block_name = block_name.replace("stone_bricks","stone");
        block_name = block_name.replace("layers","layer");
        block_name = block_name.replace("braced","log_bordered");
        block_name = block_name.replace("vertical-uneven_log","log_bordered");
        if (!block_name.contains("bricks") && !block_name.contains("planks"))
        {
            block_name = block_name.replace("soft","soft_bricks");
            block_name = block_name.replace("encased","encased_bricks");
            block_name = block_name.replace("solid","solid_bricks");
            block_name = block_name.replace("chaotic/stone","chaotic_bricks/stone");
            block_name = block_name.replace("chaotic/red_sandstone","chaotic_bricks/red_sandstone");
        }

        baseBlock = Registry.BLOCK.get(new Identifier(Chisel.MOD_ID, block_name));

        if (baseBlock.toString().equals("Block{minecraft:air}"))
        {
            baseBlock = Registry.BLOCK.get(new Identifier("minecraft", vanilla_name));
        }
    }

    public void replaceToBase(World world, BlockPos pos)
    {
        world.setBlockState(pos, baseBlock.getDefaultState());
    }

    public boolean hasRandomTicks(BlockState state)
    {
        return true;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack)
    {
        replaceToBase(world, pos);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        this.scheduledTick(state, world, pos, random);
        replaceToBase(world, pos);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos)
    {
        return baseBlock.getDefaultState();
    }
}
