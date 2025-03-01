package com.matthewperiut.chisel.block.blocks;

import com.matthewperiut.chisel.Chisel;
import com.matthewperiut.chisel.block.ChiselGroupLookup;
import com.matthewperiut.chisel.item.ChiselItem;
import com.matthewperiut.chisel.registry.ItemGroupRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import static com.matthewperiut.chisel.Chisel.BLOCKS;
import static com.matthewperiut.chisel.Chisel.ITEMS;

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

        if (group.equals("glass")) {
            Chisel.transparentBlocks.add(name);
        }
        if (group.equals("ice")) {
            Chisel.translucentBlocks.add(name);
        }

        String[] individual = name.split("/", 2);
        Identifier baseBlockIdentifier = Identifier.of("minecraft",individual[1]);
        ChiselGroupLookup.addItemToGroup(individual[1], baseBlockIdentifier);
        Block baseBlock = nether_brick ? Blocks.NETHER_BRICKS : (purpur ? Blocks.PURPUR_BLOCK : (quartz ? Blocks.QUARTZ_BLOCK : Registries.BLOCK.get(baseBlockIdentifier)));
        RegistrySupplier<Block> blockSupplier;

        Identifier block_id = Identifier.of("chisel", name);
        RegistryKey<Block> block_key = RegistryKey.of(RegistryKeys.BLOCK, block_id);
        if (redstone && pillar)
            blockSupplier = BLOCKS.register(block_id, () -> new RedstonePillarBlock(AbstractBlock.Settings.copy(baseBlock).registryKey(block_key)));
        else if (redstone)
            blockSupplier = BLOCKS.register(block_id, () -> new RedstoneBlock(AbstractBlock.Settings.copy(baseBlock).registryKey(block_key)));
        else if (pillar)
            blockSupplier = BLOCKS.register(block_id, () -> new PillarBlock(AbstractBlock.Settings.copy(baseBlock).registryKey(block_key)));
        else
            blockSupplier = BLOCKS.register(block_id, () -> new Block(AbstractBlock.Settings.copy(baseBlock).registryKey(block_key)));

        RegistryKey<Item> item_key = RegistryKey.of(RegistryKeys.ITEM, block_id);
        RegistrySupplier<Item> itemSupplier = ITEMS.register(block_id, () -> new BlockItem(blockSupplier.get(), new Item.Settings().arch$tab(ItemGroupRegistry.CLAY_GROUP).registryKey(item_key)));

        if (nether_brick)
            ChiselGroupLookup.addItemToGroup("nether_bricks", Identifier.of("chisel", name));
        else
            ChiselGroupLookup.addItemToGroup(group, Identifier.of("chisel", name));
    }
}
