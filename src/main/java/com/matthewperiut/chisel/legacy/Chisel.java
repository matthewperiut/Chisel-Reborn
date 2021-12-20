package com.matthewperiut.chisel.legacy;

import com.matthewperiut.chisel.legacy.config.BlockPack;

import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;

public class Chisel
{
    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(com.matthewperiut.chisel.Chisel.MOD_ID + ":blocks");

    public static void onInitialize()
    {
        new BlockPack("default").register(RESOURCE_PACK);
        RRPCallback.EVENT.register(a -> a.add(RESOURCE_PACK));
    }
}