package com.periut.chisel.neoforge;

import com.periut.chisel.Chisel;
import com.periut.chisel.gui.ChiselScreen;
import net.minecraft.block.Block;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import static com.periut.chisel.Chisel.CHISEL_SCREEN_HANDLER;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = Chisel.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = Chisel.MOD_ID, value = Dist.CLIENT)
public class ChiselClientNeoForge {
    public ChiselClientNeoForge(IEventBus modEventBus, ModContainer container) {
        // Register the screen handler on the mod event bus
        modEventBus.addListener(this::onRegisterScreens);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        for (String name : Chisel.translucentBlocks) {
            Block block = Registries.BLOCK.get(Identifier.of(Chisel.MOD_ID, name));
            RenderLayers.setRenderLayer(block, BlockRenderLayer.TRANSLUCENT);
        }
        for (String name : Chisel.transparentBlocks) {
            Block block = Registries.BLOCK.get(Identifier.of(Chisel.MOD_ID, name));
            RenderLayers.setRenderLayer(block, BlockRenderLayer.CUTOUT);
        }
    }

    private void onRegisterScreens(RegisterMenuScreensEvent event) {
        event.register(CHISEL_SCREEN_HANDLER.get(), ChiselScreen::new);
    }
}
