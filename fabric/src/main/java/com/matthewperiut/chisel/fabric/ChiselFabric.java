package com.matthewperiut.chisel.fabric;

import net.fabricmc.api.ModInitializer;

import static com.matthewperiut.chisel.Chisel.init;

public class ChiselFabric implements ModInitializer
{
    @Override
    public void onInitialize() {
        init();
    }
}
