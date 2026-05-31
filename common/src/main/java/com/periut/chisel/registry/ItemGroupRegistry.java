package com.periut.chisel.registry;

import com.periut.chisel.Chisel;
import com.periut.chisel.platform.RegistryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class ItemGroupRegistry {
    public static RegistryHelper.ItemGroupRegistration CLAY_GROUP;

    public static void Register() {
        CLAY_GROUP = RegistryHelper.registerItemGroup(
                Identifier.fromNamespaceAndPath(Chisel.MOD_ID, "chisel"),
                () -> Component.translatable("itemGroup." + Chisel.MOD_ID + ".chisel"),
                () -> new ItemStack(Chisel.chiselSupplier.get())
        );
    }
}
