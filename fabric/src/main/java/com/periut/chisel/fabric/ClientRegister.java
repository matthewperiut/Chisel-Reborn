package com.periut.chisel.fabric;

import com.periut.chisel.Chisel;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ClientRegister
{
    public static void Register()
    {
        for (String name : Chisel.translucentBlocks) {
            Block block = Registries.BLOCK.get(Identifier.of(Chisel.MOD_ID, name));
            BlockRenderLayerMap.putBlock(block, BlockRenderLayer.TRANSLUCENT);
        }
        for (String name : Chisel.transparentBlocks) {
            Block block = Registries.BLOCK.get(Identifier.of(Chisel.MOD_ID, name));
            BlockRenderLayerMap.putBlock(block, BlockRenderLayer.CUTOUT);
        }
    }
}