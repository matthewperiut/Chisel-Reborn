package com.matthewperiut.chisel;

import com.matthewperiut.chisel.block.GeneratedClientRegister;
import com.matthewperiut.chisel.gui.ChiselScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

import static com.matthewperiut.chisel.Chisel.CHISEL_SCREEN_HANDLER;

public class ChiselClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        ScreenRegistry.register(CHISEL_SCREEN_HANDLER, ChiselScreen::new);
        GeneratedClientRegister.Register();
    }
}
