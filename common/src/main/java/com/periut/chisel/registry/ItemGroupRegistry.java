package com.periut.chisel.registry;

import com.periut.chisel.Chisel;
import com.periut.chisel.platform.RegistryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemGroupRegistry {
    public static RegistryHelper.ItemGroupRegistration CLAY_GROUP;

    public static void Register() {
        CLAY_GROUP = RegistryHelper.registerItemGroup(
                Identifier.of(Chisel.MOD_ID, "chisel"),
                () -> Text.translatable("itemGroup." + Chisel.MOD_ID + ".chisel"),
                () -> new ItemStack(Chisel.chiselSupplier.get())
        );
    }
}
