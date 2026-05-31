package com.periut.chisel.platform.services;

import com.periut.chisel.platform.RegistryHelper;
import net.minecraft.world.item.Item;

public interface ItemGroupHelperService {
    Item.Properties addToItemGroup(Item.Properties settings, RegistryHelper.ItemGroupRegistration group);
}
