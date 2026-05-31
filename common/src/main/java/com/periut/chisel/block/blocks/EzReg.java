package com.periut.chisel.block.blocks;

import com.periut.chisel.Chisel;
import com.periut.chisel.block.ChiselGroupLookup;
import com.periut.chisel.platform.ItemGroupHelper;
import com.periut.chisel.platform.RegistryHelper;
import com.periut.chisel.registry.ItemGroupRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PoweredBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import java.util.function.Supplier;

public class EzReg
{
    public static void Reg(String group, String name)
    {
        boolean redstone = false;
        boolean pillar = false;
        boolean purpur = false;
        boolean nether_brick = false;
        boolean quartz = false;

        if (name.toLowerCase().contains("redstone"))
            redstone = true;

        if (name.toLowerCase().contains("pillar") || name.toLowerCase().contains("twist"))
            pillar = true;

        if (name.toLowerCase().contains("purpur"))
            purpur = true;

        if (name.toLowerCase().contains("nether_brick"))
            nether_brick = true;

        if (name.toLowerCase().contains("quartz"))
            quartz = true;

        String[] individual = name.split("/", 2);
        Identifier baseBlockIdentifier = Identifier.fromNamespaceAndPath("minecraft",individual[1]);
        ChiselGroupLookup.addItemToGroup(individual[1], baseBlockIdentifier);
        Block baseBlock = nether_brick ? Blocks.NETHER_BRICKS : (purpur ? Blocks.PURPUR_BLOCK : (quartz ? Blocks.QUARTZ_BLOCK : BuiltInRegistries.BLOCK.getValue(baseBlockIdentifier)));
        Supplier<Block> blockSupplier;

        Identifier block_id = Identifier.fromNamespaceAndPath("chisel", name);
        ResourceKey<Block> block_key = ResourceKey.create(Registries.BLOCK, block_id);
        if (redstone && pillar)
            blockSupplier = RegistryHelper.register(BuiltInRegistries.BLOCK, block_id, () -> new RedstonePillarBlock(BlockBehaviour.Properties.ofFullCopy(baseBlock).setId(block_key)));
        else if (redstone)
            blockSupplier = RegistryHelper.register(BuiltInRegistries.BLOCK, block_id, () -> new PoweredBlock(BlockBehaviour.Properties.ofFullCopy(baseBlock).setId(block_key)));
        else if (pillar)
            blockSupplier = RegistryHelper.register(BuiltInRegistries.BLOCK, block_id, () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(baseBlock).setId(block_key)));
        else
            blockSupplier = RegistryHelper.register(BuiltInRegistries.BLOCK, block_id, () -> new Block(BlockBehaviour.Properties.ofFullCopy(baseBlock).setId(block_key)));

        ResourceKey<Item> item_key = ResourceKey.create(Registries.ITEM, block_id);
        Supplier<Item> itemSupplier = RegistryHelper.register(BuiltInRegistries.ITEM, block_id, () -> new BlockItem(blockSupplier.get(), ItemGroupHelper.addToItemGroup(new Item.Properties().setId(item_key), ItemGroupRegistry.CLAY_GROUP)));

        if (nether_brick)
            ChiselGroupLookup.addItemToGroup("nether_bricks", Identifier.fromNamespaceAndPath("chisel", name));
        else
            ChiselGroupLookup.addItemToGroup(group, Identifier.fromNamespaceAndPath("chisel", name));
    }
}
