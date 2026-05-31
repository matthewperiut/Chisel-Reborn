package com.periut.chisel.platform.fabric;

import com.periut.chisel.platform.RegistryHelper;
import com.periut.chisel.platform.services.ItemGroupHelperService;
import net.minecraft.world.item.Item;

public class ItemGroupHelperImpl implements ItemGroupHelperService {
    @Override
    public Item.Properties addToItemGroup(Item.Properties settings, RegistryHelper.ItemGroupRegistration group) {
        // Fabric uses the FabricItemGroup displayItems builder (see RegistryHelperImpl) to add items
        // to groups, so nothing extra is needed on the item Properties here.
        return settings;
    }
}
