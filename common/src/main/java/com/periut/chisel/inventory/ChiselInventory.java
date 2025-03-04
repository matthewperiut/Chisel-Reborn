package com.periut.chisel.inventory;

import com.periut.chisel.block.ChiselGroupLookup;
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
        if (stack.getCount() <= 0 || stack.getCount() > 99) {
            stack = ItemStack.EMPTY; // Ensure invalid stacks are cleared
        }
        getItems().set(slot, stack);
        if (slot == 0) {
            refresh(stack.getItem());
        }
    }


    @Override
    public ItemStack removeStack(int slot, int count) {
        ItemStack result = Inventories.splitStack(getItems(), slot, count);
        if (!result.isEmpty()) {
            markDirty();
            if (slot != 0 && !inventory.get(0).isEmpty()) {
                int c = inventory.get(0).getCount();
                result.setCount(Math.max(1, Math.min(c, 99))); // Clamp between 1 and 99
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
        int baseCount = getStack(0).getCount();
        baseCount = Math.max(1, Math.min(baseCount, 99)); // Clamp stack size
        for (int i = 0; i < 60 && i < chiselBlocks.size(); i++) {
            this.setStack(i + 1, new ItemStack(chiselBlocks.get(i), baseCount));
        }
    }

    public void clearInv() {
        for (int i = 1; i < inventory.size(); i++) {
            this.setStack(i, ItemStack.EMPTY);
        }
    }
}