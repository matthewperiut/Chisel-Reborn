package com.periut.chisel.platform.fabric;

import com.periut.chisel.platform.RegistryHelper;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RegistryHelperImpl {
    private static final List<Item> REGISTERED_ITEMS = new ArrayList<>();

    public static <T> Supplier<T> register(Registry<? super T> registry, Identifier id, Supplier<T> supplier) {
        T registered = Registry.register(registry, id, supplier.get());

        // Track items in registration order
        if (registry == Registries.ITEM && registered instanceof Item item) {
            REGISTERED_ITEMS.add(item);
        }

        return () -> registered;
    }

    public static RegistryHelper.ItemGroupRegistration registerItemGroup(Identifier id, Supplier<Text> displayName, Supplier<ItemStack> icon) {
        RegistryKey<ItemGroup> key = RegistryKey.of(RegistryKeys.ITEM_GROUP, id);
        ItemGroup[] groupHolder = new ItemGroup[1];
        ItemGroup group = FabricItemGroup.builder()
                .icon(icon)
                .displayName(displayName.get())
                .entries((context, entries) -> {
                    // Add items in registration order
                    for (Item item : REGISTERED_ITEMS) {
                        entries.add(item);
                    }
                })
                .build();
        groupHolder[0] = Registry.register(Registries.ITEM_GROUP, key, group);
        return new RegistryHelper.ItemGroupRegistration(() -> groupHolder[0], key);
    }
}
