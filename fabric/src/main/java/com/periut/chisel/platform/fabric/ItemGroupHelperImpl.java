package com.periut.chisel.platform.fabric;

import com.periut.chisel.platform.RegistryHelper;
import net.minecraft.item.Item;

public class ItemGroupHelperImpl {
    public static Item.Settings addToItemGroup(Item.Settings settings, RegistryHelper.ItemGroupRegistration group) {
        // Fabric uses ItemGroupEvents to add items to groups
        // The items will be automatically added based on their registry
        return settings;
    }
}
