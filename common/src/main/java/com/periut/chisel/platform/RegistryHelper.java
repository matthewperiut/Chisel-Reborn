package com.periut.chisel.platform;

import com.periut.chisel.platform.services.RegistryHelperService;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class RegistryHelper {
    private static final RegistryHelperService SERVICE = Services.load(RegistryHelperService.class);

    public static <T> Supplier<T> register(Registry<? super T> registry, Identifier id, Supplier<T> supplier) {
        return SERVICE.register(registry, id, supplier);
    }

    public static ItemGroupRegistration registerItemGroup(Identifier id, Supplier<Component> displayName, Supplier<ItemStack> icon) {
        return SERVICE.registerItemGroup(id, displayName, icon);
    }

    public static class ItemGroupRegistration {
        private final Supplier<CreativeModeTab> itemGroup;
        private final ResourceKey<CreativeModeTab> registryKey;

        public ItemGroupRegistration(Supplier<CreativeModeTab> itemGroup, ResourceKey<CreativeModeTab> registryKey) {
            this.itemGroup = itemGroup;
            this.registryKey = registryKey;
        }

        public Supplier<CreativeModeTab> getSupplier() {
            return itemGroup;
        }

        public ResourceKey<CreativeModeTab> getKey() {
            return registryKey;
        }
    }
}
