package com.periut.chisel.fabric;

import com.periut.chisel.gui.ChiselScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

import static com.periut.chisel.Chisel.CHISEL_SCREEN_HANDLER;

public class ChiselClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        ClientRegister.Register();
        MenuScreens.register(CHISEL_SCREEN_HANDLER.get(), ChiselScreen::new);
    }
}
