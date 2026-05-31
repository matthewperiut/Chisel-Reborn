package com.periut.chisel.inventory;

import com.periut.chisel.block.ChiselGroupLookup;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ChiselInventory implements IInventory {
    private final NonNullList<ItemStack> inventory;

    public ChiselInventory() {
        this.inventory = NonNullList.withSize(61, ItemStack.EMPTY);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        validateAndSetStack(slot, stack);
    }

    private void validateAndSetStack(int slot, ItemStack stack) {
        // Ensure stack is either empty or has a valid count
        if (stack == null) {
            getItems().set(slot, ItemStack.EMPTY);
            return;
        }

        // If stack is empty or count is invalid, set to empty
        if (stack.isEmpty() || stack.getCount() <= 0) {
            getItems().set(slot, ItemStack.EMPTY);
        } else {
            // Clamp count between 1 and 99
            ItemStack sanitizedStack = stack.copyWithCount(Math.max(1, Math.min(stack.getCount(), 99)));
            getItems().set(slot, sanitizedStack);
        }

        // Refresh if it's the input slot
        if (slot == 0) {
            refresh(getItem(slot).getItem());
        }
    }

    @Override
    public ItemStack removeItem(int slot, int count) {
        ItemStack result = ContainerHelper.removeItem(getItems(), slot, count);

        // Ensure the result has a valid count
        if (!result.isEmpty()) {
            // If the input slot, respect the current input stack count
            if (slot != 0 && !inventory.get(0).isEmpty()) {
                int inputCount = inventory.get(0).getCount();
                result.setCount(Math.max(1, Math.min(result.getCount(), inputCount)));
            }

            // Ensure the result always has at least 1 item
            if (result.getCount() <= 0) {
                result = ItemStack.EMPTY;
            }

            setChanged();
        }

        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = getItems().get(slot);

        // If the stack is empty or invalid, return empty stack
        if (stack.isEmpty() || stack.getCount() <= 0) {
            return ItemStack.EMPTY;
        }

        getItems().set(slot, ItemStack.EMPTY);
        return stack;
    }

    public void refresh(Item item) {
        if (!(item instanceof BlockItem)) {
            clearInv();
            return;
        }
        List<Item> chiselBlocks = ChiselGroupLookup.getBlocksInGroup(item);
        populate(chiselBlocks);
    }

    public void populate(List<Item> chiselBlocks) {
        clearInv();
        int baseCount = getItem(0).getCount();
        baseCount = Math.max(1, Math.min(baseCount, 99)); // Clamp stack size

        for (int i = 0; i < 60 && i < chiselBlocks.size(); i++) {
            this.setItem(i + 1, new ItemStack(chiselBlocks.get(i), baseCount));
        }
    }

    public void clearInv() {
        for (int i = 1; i < inventory.size(); i++) {
            this.setItem(i, ItemStack.EMPTY);
        }
    }

    // Additional method to ensure all stacks are valid before saving
    public void sanitizeInventory() {
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.get(i);
            validateAndSetStack(i, stack);
        }
    }
}