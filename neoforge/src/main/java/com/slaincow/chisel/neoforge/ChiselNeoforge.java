package com.slaincow.chisel.neoforge;

import com.matthewperiut.chisel.Chisel;
import com.matthewperiut.chisel.gui.ChiselScreen;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.matthewperiut.chisel.Chisel.CHISEL_SCREEN_HANDLER;

@Mod(Chisel.MOD_ID)
public class ChiselNeoforge
{

    public ChiselNeoforge(IEventBus modEventBus)
    {
        modEventBus.addListener(this::commonSetup);

        Chisel.init();
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = Chisel.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(
                    // Assume RegistryObject<MenuType<MyMenu>> MY_MENU
                    // Assume MyContainerScreen<MyMenu> which takes in three parameters
                    () -> HandledScreens.register(CHISEL_SCREEN_HANDLER, ChiselScreen::new)
            );
            for (String name : Chisel.translucentBlocks) {
                Block block = Registries.BLOCK.get(new Identifier(Chisel.MOD_ID, name));
                RenderLayers.setRenderLayer(block, RenderLayer.getTranslucent());
            }
            for (String name : Chisel.transparentBlocks) {
                Block block = Registries.BLOCK.get(new Identifier(Chisel.MOD_ID, name));
                RenderLayers.setRenderLayer(block, RenderLayer.getCutout());
            }
        }
    }
}