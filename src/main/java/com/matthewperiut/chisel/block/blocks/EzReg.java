package com.matthewperiut.chisel.block.blocks;

import com.matthewperiut.chisel.Chisel;
import com.matthewperiut.chisel.block.ChiselGroupLookup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import static com.matthewperiut.chisel.Chisel.CHISEL_GROUP;

public class EzReg
{
    public static void Reg(String group, String name, Block block)
    {
        String[] individual = name.split("/", 2);
        ChiselGroupLookup.addItemToGroup(individual[1], new Identifier("minecraft",individual[1]));

        Registry.register(Registries.BLOCK, new Identifier(Chisel.MOD_ID, name), block);
	BlockItem item = new BlockItem(block, new Item.Settings());
        Registry.register(Registries.ITEM, new Identifier(Chisel.MOD_ID, name), item);

	ItemGroupEvents.modifyEntriesEvent(CHISEL_GROUP).register(entries -> entries.add(item));

        ChiselGroupLookup.addItemToGroup(group, new Identifier(Chisel.MOD_ID, name));
    }
}
