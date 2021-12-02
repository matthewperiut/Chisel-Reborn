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
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DefaultResources
{
    static public String ExportResource(String resourceName) throws Exception {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        String jarFolder;
        try {
            stream = DefaultResources.class.getResourceAsStream(resourceName);//note that each / is a directory down in the "jar tree" been the jar the root of the tree
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            jarFolder = new File(DefaultResources.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
            resStreamOut = new FileOutputStream(jarFolder + resourceName);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            stream.close();
            resStreamOut.close();
        }

        return jarFolder + resourceName;
    }

    static public void ExtractFromJar()
    {
        Path path = Paths.get(FabricLoader.getInstance().getConfigDir().toString() + "/" + Ref.MOD_ID + "/default.zip");
        if(!Files.exists(path))
        {
            try
            {
                // Extract from jar
                String resrc = ExportResource("/default.zip");
                File zipFile = new File(resrc);
                File destination = path.toFile();
                zipFile.renameTo(destination);

                // Extract from zip
                String fileZip = destination.getAbsolutePath();
                File destDir = new File(FabricLoader.getInstance().getConfigDir().toString() + "/" + Ref.MOD_ID );
                UnzipFiles.extractFolder(fileZip, destDir.toString());
            }
            catch (Exception e)
            {
                Chisel.LOGGER.info(e.getMessage());
            }
        }
    }
}
