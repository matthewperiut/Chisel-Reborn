package com.periut.chisel.neoforge;

import com.periut.chisel.Chisel;
import com.periut.chisel.platform.neoforge.RegistryHelperImpl;
import com.periut.chisel.registry.ItemGroupRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(Chisel.MOD_ID)
public class ChiselNeoforge
{

    public ChiselNeoforge(IEventBus modEventBus)
    {
        // Initialize registries before registering anything
        RegistryHelperImpl.init(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);

        Chisel.init();
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(ItemGroupRegistry.CLAY_GROUP.getKey())) {
            // Add items in registration order
            RegistryHelperImpl.getRegisteredItems().forEach(item -> {
                event.add(item.getDefaultStack());
            });
        }
    }
}