package com.matthewperiut.chisel;

import com.matthewperiut.chisel.block.GeneratedClientRegister;
import com.matthewperiut.chisel.gui.ChiselScreen;
import com.matthewperiut.chisel.gui.ChiselScreenHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ChiselClient implements ClientModInitializer
{
    public static final ScreenHandlerType<ChiselScreenHandler> CHISEL_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(Chisel.MOD_ID, "chisel_screen_handler"), ChiselScreenHandler::new);

    @Override
    public void onInitializeClient()
    {
        ScreenRegistry.register(CHISEL_SCREEN_HANDLER, ChiselScreen::new);
        GeneratedClientRegister.Register();
    }
}
