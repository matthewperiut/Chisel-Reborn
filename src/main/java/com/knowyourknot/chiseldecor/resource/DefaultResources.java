package com.knowyourknot.chiseldecor.resource;

import com.knowyourknot.chiseldecor.ChiselDecorEntryPoint;
import com.knowyourknot.chiseldecor.Ref;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class DefaultResources
{
    static public void Download(String url_string)
    {
        Path path = Paths.get(FabricLoader.getInstance().getConfigDir().toString() + "/" + Ref.MOD_ID + "/malg");
        if(!Files.exists(path))
        {
            try {
                Path Config = FabricLoader.getInstance().getConfigDir();
                File ConfigFile = Config.toFile();
                File file = new File(ConfigFile.getAbsolutePath()+"/" + Ref.MOD_ID + "/malg.zip");
                URL website = new URL(url_string);
                FileUtils.copyURLToFile(website, file);

                try (java.util.zip.ZipFile zipFile = new ZipFile(file)) {
                    Enumeration<? extends ZipEntry> entries = zipFile.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry entry = entries.nextElement();
                        File entryDestination = new File(ConfigFile.getAbsolutePath()+ "/" + Ref.MOD_ID,  entry.getName());
                        if (entry.isDirectory()) {
                            entryDestination.mkdirs();
                        } else {
                            entryDestination.getParentFile().mkdirs();
                            try (InputStream in = zipFile.getInputStream(entry);
                                 OutputStream out = new FileOutputStream(entryDestination)) {
                                IOUtils.copy(in, out);
                            }
                        }
                    }

                    if(!file.delete())
                    {
                        ChiselDecorEntryPoint.LOGGER.info("Failed to delete default zip: \n" + file.getAbsolutePath());
                    }
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
