package com.periut.chisel.fabric;

import net.fabricmc.api.ModInitializer;

import static com.periut.chisel.Chisel.init;

public class ChiselFabric implements ModInitializer
{
    @Override
    public void onInitialize() {
        init();
        // Items are now added to the creative tab in registration order via the ItemGroup.entries() builder
    }
}
