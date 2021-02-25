package com.knowyourknot.chiseldecor;

import java.util.Iterator;

import com.knowyourknot.chiseldecor.gui.ChiselScreen;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

public class ChiselDecorEntryPointClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(Ref.CHISEL_SCREEN_HANDLER, ChiselScreen::new);

        // make transparent/translucent blocks appear as such without CTM
        Iterator<Block> transparentBlocks = ChiselDecorEntryPoint.TRANSPARENT_BLOCKS.iterator();
        while (transparentBlocks.hasNext()) {
            BlockRenderLayerMap.INSTANCE.putBlock(transparentBlocks.next(), RenderLayer.getTranslucent());
        }
    }
    
}
