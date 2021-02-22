package com.knowyourknot.chiseldecor.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.knowyourknot.chiseldecor.ChiselDecorEntryPoint;
import com.knowyourknot.chiseldecor.Ref;
import com.knowyourknot.chiseldecor.data.Recipes;
import com.knowyourknot.chiseldecor.data.Recipes.RecipeKey;
import com.knowyourknot.chiseldecor.world.WorldGen;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockPack {
    private final String packDir;
    private File blockPackFolder;

    public BlockPack(String packDir) {
        this.packDir = packDir;
        this.blockPackFolder = new File(FabricLoader.getInstance().getConfigDir().toFile(), "chiseldecor/" + packDir);
        if (!blockPackFolder.exists()) {
            File blockPackZip = new File(FabricLoader.getInstance().getConfigDir().toFile(), "chiseldecor/" + packDir + ".zip");
            if (blockPackZip.exists()) {
                String msg = String.format("Blockpack %s needs to be removed from zip file. The blockpack will not load!", packDir);
                ChiselDecorEntryPoint.LOGGER.warn(msg);
            } else {
                String msg = String.format("Blockpack folder not found for blockpack %s. The blockpack will not load!", packDir);
                ChiselDecorEntryPoint.LOGGER.warn(msg);
            }
        }
    }

    public void register(RuntimeResourcePack resourcePack) {
        List<BlockType> types = getTypes();
        for (int i = 0; i < types.size(); i++) {
            types.get(i).register(resourcePack);
        }
        registerTextures(resourcePack);
        registerLang(resourcePack);
        registerRecipes(resourcePack);
        registerWorldGen();
    }

    public void registerTextures(RuntimeResourcePack resourcePack) {
        File textureFolder = new File(blockPackFolder, "textures");
        if (!textureFolder.exists()) {
            String msg = String.format("No textures folder found for blockpack %s.", packDir);
            ChiselDecorEntryPoint.LOGGER.info(msg);
            return;
        }
        List<File> textureFiles = getFilesFromDir(textureFolder);
        for (int i = 0; i < textureFiles.size(); i++) {
            File textureFile = textureFiles.get(i);
            // i know this is horrible don't @ me
            String region = textureFile.getAbsolutePath().substring(textureFolder.getAbsolutePath().length()).replace('\\', '/');
            resourcePack.addAsset(new Identifier(Ref.MOD_ID, "textures/blocks/" + packDir + region), readFile(textureFile));
        }
    }

    public void registerLang(RuntimeResourcePack resourcePack) {
        File langFolder = new File(blockPackFolder, "lang");
        if (!langFolder.exists()) {
            String msg = String.format("No lang folder found for blockpack %s.", packDir);
            ChiselDecorEntryPoint.LOGGER.info(msg);
            return;
        }
        List<File> langFiles = getFilesFromDir(langFolder);
        for (int i = 0; i < langFiles.size(); i++) {
            File langFile = langFiles.get(i);
            String region = langFile.getName();
            resourcePack.addAsset(new Identifier(Ref.MOD_ID, "lang/" + region), readFile(langFile));
        }
    }

    public void registerWorldGen() {
        File worldGen = new File(blockPackFolder, "worldgen.json");
        if (!worldGen.exists()) {
            return;
        }
        JsonObject worldGenJson = getJsonFromFile(worldGen);
        JsonElement worldGens = worldGenJson.get("world_gen");
        if (worldGens == null || !worldGens.isJsonArray()) {
            return;
        }
        JsonArray worldGenArray = worldGens.getAsJsonArray();
        for (int i = 0; i < worldGenArray.size(); i++) {
            JsonObject worldGenEntry = worldGenArray.get(i).getAsJsonObject();
            String worldGenType = worldGenEntry.get("type").getAsString();
            String worldGenBlockId = worldGenEntry.get("block").getAsString();
            Block worldGenBlock = Registry.BLOCK.get(Identifier.splitOn(worldGenBlockId, ':'));
            if (!worldGenBlock.equals(Blocks.AIR) && worldGenType.equals("ore")) {
                WorldGen.addStoneWorldGen(worldGenBlock);
            }
        }
    }

    public void registerRecipes(RuntimeResourcePack resourcePack) {
        File recipes = new File(blockPackFolder, "recipes.json");
        if (!recipes.exists()) {
            return;
        }
        JsonObject recipesJson = getJsonFromFile(recipes);
        JsonElement recipesElement = recipesJson.get("recipes");
        if (recipesElement == null || !recipesElement.isJsonArray()) {
            return;
        }
        JsonArray recipesArray = recipesElement.getAsJsonArray();
        for (int i = 0; i < recipesArray.size(); i++) {
            JsonObject recipeJson = recipesArray.get(i).getAsJsonObject();
            JsonObject keysJson = recipeJson.get("keys").getAsJsonObject();
            JsonArray patternJson = recipeJson.get("pattern").getAsJsonArray();
            JsonObject resultJson = recipeJson.get("result").getAsJsonObject();

            List<RecipeKey> keys = new ArrayList<>();
            Iterator<Entry<String, JsonElement>> keysIterator = keysJson.entrySet().iterator();
            while (keysIterator.hasNext()) {
                Entry<String, JsonElement> keyJson = keysIterator.next();
                keys.add(new RecipeKey(keyJson.getKey(), keyJson.getValue().getAsString()));
            }

            List<String> pattern = new ArrayList<>();
            for (int j = 0; j < patternJson.size(); j++) {
                pattern.add(patternJson.get(j).getAsString());
            }

            String output = resultJson.get("item").getAsString();
            int amount = resultJson.get("amount").getAsInt();

            Recipes.addRecipe(pattern, keys, output, amount, resourcePack);
        }
    }

    public byte[] readFile(File lang) {
        try(FileInputStream stream = new FileInputStream(lang)) {
            byte[] bytes = new byte[stream.available()];
            int i = 0;
            while (i != -1) {
                i = stream.read(bytes);
            }
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }

    // gets all block types as defined by the json files in the block pack folder
    public List<BlockType> getTypes() {
        File typesFolder = new File(blockPackFolder, "types");
        if (!blockPackFolder.exists()) {
            String msg = String.format("No types folder found for blockpack %s.", packDir);
            ChiselDecorEntryPoint.LOGGER.info(msg);
            return new ArrayList<>();
        }
        List<File> typeFiles = getFilesFromDir(typesFolder);
        List<BlockType> types = new ArrayList<>();

        for (int i = 0; i < typeFiles.size(); i++) {
            File typeFile = typeFiles.get(i);
            JsonObject json = getJsonFromFile(typeFile);
            types.add(new BlockType(packDir, json));
        }
        return types;
    }

    public List<File> getFilesFromDir(File dir) {
        if (!dir.exists()) {
            return new ArrayList<>();
        }
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
            int i = 0;
            while (i != -1) {
                i = stream.read(bytes);
            }
            
            String blockTypeString = new String(bytes);
            return new JsonParser().parse(blockTypeString).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
