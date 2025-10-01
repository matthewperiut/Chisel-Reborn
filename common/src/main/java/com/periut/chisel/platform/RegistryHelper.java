package com.periut.chisel.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class RegistryHelper {
    @ExpectPlatform
    public static <T> Supplier<T> register(Registry<? super T> registry, Identifier id, Supplier<T> supplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemGroupRegistration registerItemGroup(Identifier id, Supplier<Text> displayName, Supplier<ItemStack> icon) {
        throw new AssertionError();
    }

    public static class ItemGroupRegistration {
        private final Supplier<ItemGroup> itemGroup;
        private final RegistryKey<ItemGroup> registryKey;

        public ItemGroupRegistration(Supplier<ItemGroup> itemGroup, RegistryKey<ItemGroup> registryKey) {
            this.itemGroup = itemGroup;
            this.registryKey = registryKey;
        }

        public Supplier<ItemGroup> getSupplier() {
            return itemGroup;
        }

        public RegistryKey<ItemGroup> getKey() {
            return registryKey;
        }
    }
}
