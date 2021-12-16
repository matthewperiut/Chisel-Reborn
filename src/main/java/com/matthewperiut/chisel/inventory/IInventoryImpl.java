package com.matthewperiut.chisel.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class IInventoryImpl implements IInventory
{
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    public IInventoryImpl()
    {
        //
    }

    @Override
    public DefaultedList<ItemStack> getItems()
    {
        return inventory;
    }

}