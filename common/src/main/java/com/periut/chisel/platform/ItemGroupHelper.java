package com.periut.chisel.platform;

import com.periut.chisel.platform.services.ItemGroupHelperService;
import net.minecraft.world.item.Item;

public class ItemGroupHelper {
    private static final ItemGroupHelperService SERVICE = Services.load(ItemGroupHelperService.class);

    public static Item.Properties addToItemGroup(Item.Properties settings, RegistryHelper.ItemGroupRegistration group) {
        return SERVICE.addToItemGroup(settings, group);
    }
}
