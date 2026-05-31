package com.periut.chisel.fabric;

public class ClientRegister
{
    public static void Register()
    {
        // In Minecraft 26.1 a block's render layer is data-driven via the block model's
        // "render_type" field, and Fabric API's old code-based BlockRenderLayerMap was removed.
        // (The transparent/translucent block lists were never populated, so this was already a no-op.)
    }
}
