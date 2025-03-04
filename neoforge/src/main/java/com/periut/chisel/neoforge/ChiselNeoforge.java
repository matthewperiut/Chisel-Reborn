package com.periut.chisel.neoforge;

import com.periut.chisel.Chisel;
import com.periut.chisel.gui.ChiselScreen;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import static com.periut.chisel.Chisel.CHISEL_SCREEN_HANDLER;

@Mod(Chisel.MOD_ID)
public class ChiselNeoforge
{

    public ChiselNeoforge(IEventBus modEventBus)
    {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerScreens);

        Chisel.init();
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    private void registerScreens(RegisterMenuScreensEvent event) {
        event.register(CHISEL_SCREEN_HANDLER.get(), ChiselScreen::new);
    }

    @EventBusSubscriber(modid = Chisel.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            for (String name : Chisel.translucentBlocks) {
                Block block = Registries.BLOCK.get(Identifier.of(Chisel.MOD_ID, name));
                RenderLayers.setRenderLayer(block, RenderLayer.getTranslucent());
            }
            for (String name : Chisel.transparentBlocks) {
                Block block = Registries.BLOCK.get(Identifier.of(Chisel.MOD_ID, name));
                RenderLayers.setRenderLayer(block, RenderLayer.getCutout());
            }
        }
    }
}