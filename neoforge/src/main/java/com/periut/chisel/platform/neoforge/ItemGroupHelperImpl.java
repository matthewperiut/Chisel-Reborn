package com.periut.chisel.platform.neoforge;

import com.periut.chisel.platform.RegistryHelper;
import net.minecraft.item.Item;

public class ItemGroupHelperImpl {
    public static Item.Settings addToItemGroup(Item.Settings settings, RegistryHelper.ItemGroupRegistration group) {
        // NeoForge doesn't need special handling - items are added to groups via the ItemGroup builder
        return settings;
    }
}
