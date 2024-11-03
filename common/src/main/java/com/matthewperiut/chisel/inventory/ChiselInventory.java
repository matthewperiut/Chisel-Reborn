package com.matthewperiut.chisel.inventory;

import com.matthewperiut.chisel.block.ChiselGroupLookup;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;

public class ChiselInventory implements IInventory {
    private final DefaultedList<ItemStack> inventory;

    public ChiselInventory()
    {
        this.inventory = DefaultedList.ofSize(61, ItemStack.EMPTY);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        getItems().set(slot, stack);
        if (slot == 0)
        {
            refresh(stack.getItem());
        }

    }

    @Override
    public ItemStack removeStack(int slot, int count) {
        ItemStack result = Inventories.splitStack(getItems(), slot, count);
        if (!result.isEmpty()) {
            markDirty();
            if (slot != 0) {
                result.setCount(inventory.get(0).getCount());
            }
        }
        return result;
    }

    public void refresh(Item item)
    {
        if (!(item instanceof BlockItem))
        {
            clearInv();
            return;
        }
        List<Item> chiselBlocks = ChiselGroupLookup.getBlocksInGroup(item);
        populate(chiselBlocks);
    }

    public void populate(List<Item> chiselBlocks)
    {
        clearInv();
        for (int i = 0; i < 60 && i < chiselBlocks.size(); i++)
        {
            this.setStack(i + 1, new ItemStack(chiselBlocks.get(i), getStack(0).getCount()));
        }
    }

    public void clearInv() {
        for (int i = 1; i < inventory.size(); i++) {
            this.setStack(i, ItemStack.EMPTY);
        }
    }
}