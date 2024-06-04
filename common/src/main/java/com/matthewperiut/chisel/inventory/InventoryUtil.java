package com.matthewperiut.chisel.inventory;

import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtil
{
    public static BundleContentsComponent createBundleComponent(Inventory inventory)
    {
        ItemStack stack = inventory.getStack(0);
        List<ItemStack> itemStackList = new ArrayList<>();
        itemStackList.add(stack);

        return new BundleContentsComponent(itemStackList);
    }

    public static Inventory createInventory(BundleContentsComponent bcc)
    {
        Inventory result = new ChiselInventory();
        if (!bcc.isEmpty()) {
            result.setStack(0, bcc.get(0));
        }
        return result;
    }
}
