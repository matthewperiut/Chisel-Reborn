package com.periut.chisel.platform.neoforge;

import com.periut.chisel.platform.RegistryHelper;
import com.periut.chisel.platform.services.ItemGroupHelperService;
import net.minecraft.world.item.Item;

public class ItemGroupHelperImpl implements ItemGroupHelperService {
    @Override
    public Item.Properties addToItemGroup(Item.Properties settings, RegistryHelper.ItemGroupRegistration group) {
        // NeoForge adds items to groups via BuildCreativeModeTabContentsEvent (see ChiselNeoforge),
        // so nothing extra is needed on the item Properties here.
        return settings;
    }
}
