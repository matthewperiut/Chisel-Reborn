package com.knowyourknot.chiseldecor.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.knowyourknot.chiseldecor.tags.ChiselRuntimeResourcePackImpl;

import net.fabricmc.loader.api.FabricLoader;

public class BlockPack {
    private final String packDir;

    public BlockPack(String packDir) {
        this.packDir = packDir;
    }

    public void register(ChiselRuntimeResourcePackImpl resourcePack) {
        List<BlockType> types = getTypes();
        for (int i = 0; i < types.size(); i++) {
            types.get(i).register(resourcePack);
        }
    }

    // gets all block types as defined by the json files in the block pack folder
    public List<BlockType> getTypes() {
        File blockPackFolder = new File(FabricLoader.getInstance().getConfigDir().toFile(), "chiseldecor/" 
        + packDir);
        List<File> typeFiles = getFilesFromDir(blockPackFolder);
        List<BlockType> types = new ArrayList<>();

        for (int i = 0; i < typeFiles.size(); i++) {
            File typeFile = typeFiles.get(i);
            JsonObject json = getJsonFromFile(typeFile);
            types.add(new BlockType(packDir, json));
        }
        return types;
    }

    public List<File> getFilesFromDir(File dir) {
        List<File> dirFiles = new ArrayList<>();
        File[] dirFilesArr = dir.listFiles();
        for (int i = 0; i < dirFilesArr.length; i++) {
            File dirFile = dirFilesArr[i];
            if (dirFile.isDirectory()) {
                dirFiles.addAll(getFilesFromDir(dirFile));
            } else {
                dirFiles.add(dirFile);
            }
        }
        return dirFiles;
    }

    public JsonObject getJsonFromFile(File blockTypeFile) {
        try(FileInputStream stream = new FileInputStream(blockTypeFile)) {
            byte[] bytes = new byte[stream.available()];
            stream.read(bytes);
            String blockTypeString = new String(bytes);
            return new JsonParser().parse(blockTypeString).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
