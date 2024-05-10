package com.matthewperiut.chisel.fabric;

import com.matthewperiut.chisel.Chisel;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ClientRegister
{
    public static void Register()
    {
        for (String name : Chisel.translucentBlocks) {
            Block block = Registries.BLOCK.get(new Identifier(Chisel.MOD_ID, name));
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
        }
        for (String name : Chisel.transparentBlocks) {
            Block block = Registries.BLOCK.get(new Identifier(Chisel.MOD_ID, name));
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
        }
    }
}