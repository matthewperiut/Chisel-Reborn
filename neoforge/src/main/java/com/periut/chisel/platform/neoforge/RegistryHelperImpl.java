package com.periut.chisel.platform.neoforge;

import com.periut.chisel.Chisel;
import com.periut.chisel.platform.RegistryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class RegistryHelperImpl {
    private static final List<Supplier<Item>> REGISTERED_ITEM_SUPPLIERS = new ArrayList<>();
    private static final Map<RegistryKey<?>, DeferredRegister<?>> DEFERRED_REGISTERS = new HashMap<>();
    private static IEventBus eventBus;

    @SuppressWarnings("unchecked")
    private static <T> DeferredRegister<T> getOrCreateRegister(Registry<? super T> registry) {
        RegistryKey<? extends Registry<? super T>> registryKey = registry.getKey();
        DeferredRegister<T> deferredRegister = (DeferredRegister<T>) DEFERRED_REGISTERS.get(registryKey);

        if (deferredRegister == null) {
            deferredRegister = DeferredRegister.create((RegistryKey<Registry<T>>) registryKey, Chisel.MOD_ID);
            DEFERRED_REGISTERS.put(registryKey, deferredRegister);

            // Immediately register with event bus if it's been initialized
            if (eventBus != null) {
                deferredRegister.register(eventBus);
            }
        }

        return deferredRegister;
    }

    public static void init(IEventBus modEventBus) {
        eventBus = modEventBus;
        // Register all existing deferred registers with the mod event bus
        DEFERRED_REGISTERS.values().forEach(register -> register.register(modEventBus));
    }

    public static <T> Supplier<T> register(Registry<? super T> registry, Identifier id, Supplier<T> supplier) {
        DeferredRegister<T> deferredRegister = getOrCreateRegister(registry);
        DeferredHolder<T, T> holder = deferredRegister.register(id.getPath(), supplier);

        // Track item suppliers in registration order (don't resolve yet!)
        if (registry == Registries.ITEM) {
            REGISTERED_ITEM_SUPPLIERS.add((Supplier<Item>) holder);
        }

        return holder;
    }

    public static RegistryHelper.ItemGroupRegistration registerItemGroup(Identifier id, Supplier<Text> displayName, Supplier<ItemStack> icon) {
        RegistryKey<ItemGroup> key = RegistryKey.of(RegistryKeys.ITEM_GROUP, id);
        DeferredRegister<ItemGroup> deferredRegister = getOrCreateRegister(Registries.ITEM_GROUP);
        DeferredHolder<ItemGroup, ItemGroup> holder = deferredRegister.register(id.getPath(), () -> {
            return ItemGroup.builder()
                    .icon(icon)
                    .displayName(displayName.get())
                    .build();
        });
        return new RegistryHelper.ItemGroupRegistration(holder, key);
    }

    public static List<Item> getRegisteredItems() {
        // Resolve the suppliers to actual items when requested
        List<Item> items = new ArrayList<>();
        for (Supplier<Item> supplier : REGISTERED_ITEM_SUPPLIERS) {
            items.add(supplier.get());
        }
        return items;
    }
}
