package com.periut.chisel.platform.fabric;

import com.periut.chisel.platform.RegistryHelper;
import com.periut.chisel.platform.services.RegistryHelperService;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RegistryHelperImpl implements RegistryHelperService {
    private static final List<Item> REGISTERED_ITEMS = new ArrayList<>();

    @Override
    public <T> Supplier<T> register(Registry<? super T> registry, Identifier id, Supplier<T> supplier) {
        T registered = Registry.register(registry, id, supplier.get());

        // Track items in registration order
        if (registry == BuiltInRegistries.ITEM && registered instanceof Item item) {
            REGISTERED_ITEMS.add(item);
        }

        return () -> registered;
    }

    @Override
    public RegistryHelper.ItemGroupRegistration registerItemGroup(Identifier id, Supplier<Component> displayName, Supplier<ItemStack> icon) {
        ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id);
        CreativeModeTab[] groupHolder = new CreativeModeTab[1];
        CreativeModeTab group = FabricCreativeModeTab.builder()
                .icon(icon)
                .title(displayName.get())
                .displayItems((context, entries) -> {
                    // Add items in registration order
                    for (Item item : REGISTERED_ITEMS) {
                        entries.accept(item);
                    }
                })
                .build();
        groupHolder[0] = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key, group);
        return new RegistryHelper.ItemGroupRegistration(() -> groupHolder[0], key);
    }
}
