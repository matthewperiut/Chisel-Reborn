package com.matthewperiut.chisel.resource;

import com.matthewperiut.chisel.Chisel;
import com.matthewperiut.chisel.Ref;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DefaultResources
{
    static public void ExtractFromJar()
    {
        Path path = Paths.get(FabricLoader.getInstance().getConfigDir().toString() + "/" + Ref.MOD_ID + "/default");
        if(!Files.exists(path))
        {
            try
            {
                URL input = DefaultResources.class.getResource("/default");
                Path config = Paths.get(FabricLoader.getInstance().getConfigDir().toString() + "/" + Ref.MOD_ID);

                FileUtils.copyDirectoryToDirectory(new File(input.getFile()),config.toFile());
            } catch (Exception e)
            {
                Chisel.LOGGER.info(e.getMessage());
            }
        }
    }
}
