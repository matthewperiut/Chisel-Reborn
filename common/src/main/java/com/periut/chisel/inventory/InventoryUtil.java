package com.periut.chisel.inventory;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.BundleContents;

public class InventoryUtil
{
    public static BundleContents createBundleComponent(Container inventory)
    {
        ItemStack stack = inventory.getItem(0);
        // MC 26.1: BundleContents stores ItemStackTemplate, not ItemStack.
        List<ItemStackTemplate> items = new ArrayList<>();
        if (!stack.isEmpty()) {
            items.add(ItemStackTemplate.fromNonEmptyStack(stack));
        }
        return new BundleContents(items);
    }

    public static Container createInventory(BundleContents bcc)
    {
        Container result = new ChiselInventory();
        if (!bcc.isEmpty()) {
            result.setItem(0, bcc.itemCopyStream().findFirst().orElse(ItemStack.EMPTY));
        }
        return result;
    }
}
