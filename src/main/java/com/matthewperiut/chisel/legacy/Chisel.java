package com.matthewperiut.chisel.legacy;

import java.util.ArrayList;
import java.util.List;

import com.matthewperiut.chisel.legacy.config.BlockPack;
import com.matthewperiut.chisel.legacy.data.Recipes;

import com.matthewperiut.chisel.legacy.config.ChiselDecorConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;

public class Chisel {
    //public static final Logger LOGGER = LogManager.getFormatterLogger("Chisel");

    public static final ChiselDecorConfig CONFIG = new ChiselDecorConfig();
    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(com.matthewperiut.chisel.Chisel.MOD_ID + ":blocks");

    public static final List<Block> TRANSPARENT_BLOCKS = new ArrayList<>();

    public static void onInitialize()
    {
        String[] blockPackDirs = ChiselDecorConfig.getBlockPackDirs();
        for (int i = 0; i < blockPackDirs.length; i++)
        {
            if (blockPackDirs[i].equals(""))
            {
                continue;
            }
            new BlockPack(blockPackDirs[i]).register(RESOURCE_PACK);
        }

        RRPCallback.EVENT.register(a -> a.add(RESOURCE_PACK));
    }
}