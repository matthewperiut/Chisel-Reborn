package com.periut.chisel.platform.neoforge;

import com.periut.chisel.Chisel;
import com.periut.chisel.platform.RegistryHelper;
import com.periut.chisel.platform.services.RegistryHelperService;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class RegistryHelperImpl implements RegistryHelperService {
    private static final List<Supplier<Item>> REGISTERED_ITEM_SUPPLIERS = new ArrayList<>();
    private static final Map<ResourceKey<?>, DeferredRegister<?>> DEFERRED_REGISTERS = new HashMap<>();
    private static IEventBus eventBus;

    @SuppressWarnings("unchecked")
    private static <T> DeferredRegister<T> getOrCreateRegister(Registry<? super T> registry) {
        ResourceKey<? extends Registry<? super T>> registryKey = registry.key();
        DeferredRegister<T> deferredRegister = (DeferredRegister<T>) DEFERRED_REGISTERS.get(registryKey);

        if (deferredRegister == null) {
            deferredRegister = DeferredRegister.create((ResourceKey<Registry<T>>) registryKey, Chisel.MOD_ID);
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

    @Override
    public <T> Supplier<T> register(Registry<? super T> registry, Identifier id, Supplier<T> supplier) {
        DeferredRegister<T> deferredRegister = getOrCreateRegister(registry);
        DeferredHolder<T, T> holder = deferredRegister.register(id.getPath(), supplier);

        // Track item suppliers in registration order (don't resolve yet!)
        if (registry == BuiltInRegistries.ITEM) {
            REGISTERED_ITEM_SUPPLIERS.add((Supplier<Item>) holder);
        }

        return holder;
    }

    @Override
    public RegistryHelper.ItemGroupRegistration registerItemGroup(Identifier id, Supplier<Component> displayName, Supplier<ItemStack> icon) {
        ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id);
        DeferredRegister<CreativeModeTab> deferredRegister = getOrCreateRegister(BuiltInRegistries.CREATIVE_MODE_TAB);
        DeferredHolder<CreativeModeTab, CreativeModeTab> holder = deferredRegister.register(id.getPath(), () -> {
            return CreativeModeTab.builder()
                    .icon(icon)
                    .title(displayName.get())
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
