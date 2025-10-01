package com.periut.chisel.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.item.Item;

public class ItemGroupHelper {
    @ExpectPlatform
    public static Item.Settings addToItemGroup(Item.Settings settings, RegistryHelper.ItemGroupRegistration group) {
        throw new AssertionError();
    }
}
