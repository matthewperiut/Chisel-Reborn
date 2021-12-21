package com.matthewperiut.chisel.inventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

public class InventoryNbtUtil
{
    // Uses NBT system made by bundle

    public static NbtCompound createCompound(Inventory inventory)
    {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.put("Items", new NbtList());
        NbtList nbtList = nbtCompound.getList("Items", 10);

        ItemStack nbtCompound2 = inventory.getStack(0);
        NbtCompound itemStack = new NbtCompound();
        nbtCompound2.writeNbt(itemStack);
        nbtList.add(0, itemStack);

        return nbtCompound;
    }

    public static Inventory createInventory(NbtCompound nbtCompound)
    {
        Inventory result = new ChiselInventory();
        if (!nbtCompound.contains("Items"))
        {
            return result;
        }
        else
        {
            NbtList nbtList = nbtCompound.getList("Items", 10);
            if (nbtList.isEmpty())
            {
                return result;
            }
            else
            {
                NbtCompound nbtCompound2 = nbtList.getCompound(0);
                ItemStack itemStack = ItemStack.fromNbt(nbtCompound2);

                result.setStack(0, ItemStack.fromNbt(nbtCompound2));

                return result;
            }
        }
    }
}
