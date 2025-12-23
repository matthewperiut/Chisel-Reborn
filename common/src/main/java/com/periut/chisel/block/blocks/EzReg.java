package com.periut.chisel.block.blocks;

import com.periut.chisel.Chisel;
import com.periut.chisel.block.ChiselGroupLookup;
import com.periut.chisel.platform.ItemGroupHelper;
import com.periut.chisel.platform.RegistryHelper;
import com.periut.chisel.registry.ItemGroupRegistry;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

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
        Identifier baseBlockIdentifier = Identifier.of("minecraft",individual[1]);
        ChiselGroupLookup.addItemToGroup(individual[1], baseBlockIdentifier);
        Block baseBlock = nether_brick ? Blocks.NETHER_BRICKS : (purpur ? Blocks.PURPUR_BLOCK : (quartz ? Blocks.QUARTZ_BLOCK : Registries.BLOCK.get(baseBlockIdentifier)));
        Supplier<Block> blockSupplier;

        Identifier block_id = Identifier.of("chisel", name);
        RegistryKey<Block> block_key = RegistryKey.of(RegistryKeys.BLOCK, block_id);
        if (redstone && pillar)
            blockSupplier = RegistryHelper.register(Registries.BLOCK, block_id, () -> new RedstonePillarBlock(AbstractBlock.Settings.copy(baseBlock).registryKey(block_key)));
        else if (redstone)
            blockSupplier = RegistryHelper.register(Registries.BLOCK, block_id, () -> new RedstoneBlock(AbstractBlock.Settings.copy(baseBlock).registryKey(block_key)));
        else if (pillar)
            blockSupplier = RegistryHelper.register(Registries.BLOCK, block_id, () -> new PillarBlock(AbstractBlock.Settings.copy(baseBlock).registryKey(block_key)));
        else
            blockSupplier = RegistryHelper.register(Registries.BLOCK, block_id, () -> new Block(AbstractBlock.Settings.copy(baseBlock).registryKey(block_key)));

        RegistryKey<Item> item_key = RegistryKey.of(RegistryKeys.ITEM, block_id);
        Supplier<Item> itemSupplier = RegistryHelper.register(Registries.ITEM, block_id, () -> new BlockItem(blockSupplier.get(), ItemGroupHelper.addToItemGroup(new Item.Settings().registryKey(item_key), ItemGroupRegistry.CLAY_GROUP)));

        if (nether_brick)
            ChiselGroupLookup.addItemToGroup("nether_bricks", Identifier.of("chisel", name));
        else
            ChiselGroupLookup.addItemToGroup(group, Identifier.of("chisel", name));
    }
}
