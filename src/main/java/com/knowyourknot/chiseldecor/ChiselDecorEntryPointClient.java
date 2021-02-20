package com.knowyourknot.chiseldecor;

import com.knowyourknot.chiseldecor.gui.ChiselScreen;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class ChiselDecorEntryPointClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(Ref.CHISEL_SCREEN_HANDLER, ChiselScreen::new);
    }
    
}
