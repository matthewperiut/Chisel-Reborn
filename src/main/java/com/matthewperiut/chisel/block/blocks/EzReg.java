package com.matthewperiut.chisel.block.blocks;

import com.matthewperiut.chisel.Chisel;
import com.matthewperiut.chisel.block.ChiselGroupLookup;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EzReg
{
    public static void Reg(String group, String name, Block block)
    {
        Registry.register(Registry.BLOCK, new Identifier(Chisel.MOD_ID, name), block);
        Registry.register(Registry.ITEM, new Identifier(Chisel.MOD_ID, name), new BlockItem(block, new Item.Settings().group(Chisel.CHISEL_GROUP)));

        ChiselGroupLookup.addItemToGroup(group, new Identifier(Chisel.MOD_ID, name));
    }
}
