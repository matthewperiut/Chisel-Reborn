package com.periut.chisel.platform.services;

import com.periut.chisel.platform.RegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public interface RegistryHelperService {
    <T> Supplier<T> register(Registry<? super T> registry, Identifier id, Supplier<T> supplier);

    RegistryHelper.ItemGroupRegistration registerItemGroup(Identifier id, Supplier<Component> displayName, Supplier<ItemStack> icon);
}
