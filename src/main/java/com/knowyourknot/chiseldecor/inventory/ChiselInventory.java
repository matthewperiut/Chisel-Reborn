package com.knowyourknot.chiseldecor.inventory;

import java.util.List;

import com.knowyourknot.chiseldecor.block.ChiselGroupLookup;

import net.minecraft.inventory.Inventories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.ServerTagManagerHolder;
import net.minecraft.tag.TagGroup;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class ChiselInventory implements IInventory {
    private DefaultedList<ItemStack> inventory;
    // private World world;

    public ChiselInventory(World world) {
        this.inventory = DefaultedList.ofSize(61, ItemStack.EMPTY);
        // this.world = world;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        getItems().set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
        if (slot == 0) {
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

    public void refresh(Item item) {
        if (!(item instanceof BlockItem)) {
            clearInv();
            return;
        }
        TagGroup<Item> itemTags = ServerTagManagerHolder.getTagManager().getItems();    
        List<Item> chiselBlocks = ChiselGroupLookup.getBlocksInGroup(item, itemTags);
        populate(chiselBlocks);
    }

    public void populate(List<Item> chiselBlocks) {
        clearInv();
        for (int i = 0; i < 60 && i < chiselBlocks.size(); i++) {
            this.setStack(i + 1, new ItemStack(chiselBlocks.get(i), 1));
        }
    }

    public void clearInv() {
        for (int i = 1; i < inventory.size(); i++) {
            this.setStack(i, ItemStack.EMPTY);
        }
    }

}
