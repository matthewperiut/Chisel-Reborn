package com.matthewperiut.chisel.config;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.matthewperiut.chisel.Chisel;
import com.matthewperiut.chisel.Ref;
import com.matthewperiut.chisel.block.ChiselGroupLookup;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockType {
    private final String packDir;
    private String typeName;
    private String chiselGroupName;
    private FabricBlockSettings settings;
    private List<BlockVariant> variants;
    private List<Identifier> tagsToInclude;
    private List<Identifier> itemsToInclude;

    public BlockType(String packDir, JsonObject typeJson) {
        this.packDir = packDir;
        parse(typeJson);
    }

    public void parse(JsonObject typeJson) {
        this.typeName = getName(typeJson);
        this.chiselGroupName = getChiselGroupName(typeJson);
        this.settings = getBlockSettings(typeJson);
        this.variants = getVariants(typeJson);
        this.tagsToInclude = getTagsToInclude(typeJson);
        this.itemsToInclude = getItemsToInclude(typeJson);
    }

    public void register(RuntimeResourcePack resourcePack) {
        registerChiselGroup();
        registerBlocks();
        registerResources(resourcePack);
    }

    public void registerBlocks() {
        for (int i = 0; i < variants.size(); i++) {
            BlockVariant variant = variants.get(i);
            variant.registerBlock();
            ChiselGroupLookup.addItemToGroup(chiselGroupName, variant.identifier());
        }
    }

    public void registerResources(RuntimeResourcePack resourcePack) {
        for (int i = 0; i < variants.size(); i++) {
            BlockVariant variant = variants.get(i);
            variant.registerDrop(resourcePack);
            // variant.registerTexture(resourcePack);
            // variant.registerMcMeta(resourcePack);
            variant.registerBlockModel(resourcePack);
            variant.registerItemModel(resourcePack);
            variant.registerBlockState(resourcePack);
        }
    }

    public void registerChiselGroup() {
        ChiselGroupLookup.addGroup(chiselGroupName);
        ChiselGroupLookup.addItemToGroup(chiselGroupName, itemsToInclude);
        ChiselGroupLookup.addTagToGroup(chiselGroupName, tagsToInclude);
    }

    public String getName(JsonObject typeJson) {
        JsonElement nameElement = typeJson.get("name");
        if (nameElement == null) {
            // cause trouble
            return null;
        }
        try {
            return nameElement.getAsString();
        } catch (ClassCastException e) {
            String msg = String.format("Blocktype from blockpack %s has no name!", packDir);
            Chisel.LOGGER.info(msg);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getChiselGroupName(JsonObject typeJson) {
        JsonElement chiselGroupNameElement = typeJson.get("chisel_group");
        if (chiselGroupNameElement == null) {
            return packDir + "/" + typeName;
        }
        try {
            return chiselGroupNameElement.getAsString();
        } catch (ClassCastException e) {
            String msg = String.format("Blocktype %s from blockpack %s has an invalid chisel group! Setting to default", typeName, packDir);
            Chisel.LOGGER.info(msg);
        }
        return packDir + "/" + typeName;
    }

    public List<BlockVariant> getVariants(JsonObject typeJson) {
        List<BlockVariant> tempVariants = new ArrayList<>();

        JsonElement element = typeJson.get("variants");
        if (element == null || !element.isJsonObject()) {
            // cause trouble
            return tempVariants;
        }
        JsonObject variantJson = element.getAsJsonObject();

        JsonElement parentElement = variantJson.get("parent");
        if (parentElement != null) {
            String parent = parentElement.getAsString();
            tempVariants.addAll(Ref.getInheritedVariants(packDir, typeName, parent));
        }
        
        JsonElement newVariants = variantJson.get("new");
        if (newVariants != null && newVariants.isJsonArray()) {
            tempVariants.addAll(parseNewVariants(newVariants.getAsJsonArray()));
        }
        
        JsonElement removeElement = variantJson.get("remove");
        if (removeElement != null && removeElement.isJsonArray()) {
            JsonArray removeArray = removeElement.getAsJsonArray();
            List<String> variantsToRemove = new ArrayList<>();
            for (int i = 0; i < removeArray.size(); i++) {
                variantsToRemove.add(removeArray.get(i).getAsString());
            }
            tempVariants.removeIf(a -> variantsToRemove.contains(a.getVariantName()));
        }

        return tempVariants;
    }

    public List<BlockVariant> parseNewVariants(JsonArray newVariantsJson) {
        List<BlockVariant> newVariants = new ArrayList<>();
        for (int i = 0; i < newVariantsJson.size(); i++) {
            JsonElement variantElement = newVariantsJson.get(i);
            if (variantElement == null || !variantElement.isJsonObject()) {
                continue;
            }
            JsonObject variant = variantElement.getAsJsonObject();
            JsonElement modelElement = variant.get("model");
            String model;
            if (modelElement != null) {
                model = modelElement.getAsString();
            } else {
                model = "";
            }
            boolean transparent = false;
            JsonElement transparentElement = variant.get("transparent");
            if (transparentElement != null) {
                transparent = transparentElement.getAsBoolean();
            }
            JsonElement namesJsonElement = variant.get("names");
            if (namesJsonElement == null || !namesJsonElement.isJsonArray()) {
                continue;
            }
            JsonArray namesJson = namesJsonElement.getAsJsonArray();
            for (int j = 0; j < namesJson.size(); j++) {
                String variantName = namesJson.get(j).getAsString();
                newVariants.add(new BlockVariant(packDir, typeName, variantName, settings, model, transparent));
            }
        }
        return newVariants;
    }

    public List<Identifier> getTagsToInclude(JsonObject typeJson) {
        List<Identifier> tempTagsToInclude = new ArrayList<>();
        JsonElement element = typeJson.get("tags_to_include");
        if (element == null || !element.isJsonArray()) {
            return tempTagsToInclude;
        }
        JsonArray tagsToIncludeJson = element.getAsJsonArray();
        for (int i = 0; i < tagsToIncludeJson.size(); i++) {
            JsonElement subElement = tagsToIncludeJson.get(i);
            if (subElement == null) {
                continue;
            }
            Identifier id = Identifier.splitOn(subElement.getAsString(), ':');
            tempTagsToInclude.add(id);
        }
        return tempTagsToInclude;
    }

    public List<Identifier> getItemsToInclude(JsonObject typeJson) {
        List<Identifier> tempItemsToInclude = new ArrayList<>();
        JsonElement element = typeJson.get("items_to_include");
        if (element == null || !element.isJsonArray()) {
            return tempItemsToInclude;
        }
        JsonArray itemsToIncludeJson = element.getAsJsonArray();
        for (int i = 0; i < itemsToIncludeJson.size(); i++) {
            JsonElement subElement = itemsToIncludeJson.get(i);
            if (subElement == null) {
                continue;
            }
            Identifier id = Identifier.splitOn(subElement.getAsString(), ':');
            tempItemsToInclude.add(id);
        }
        return tempItemsToInclude;
    }

    public FabricBlockSettings getBlockSettings(JsonObject typeJson) {
        JsonElement settingsJsonElement = typeJson.get("settings");
        if (settingsJsonElement == null || !settingsJsonElement.isJsonObject()) {
            return blockTypeSettingsErrorMessage();
        }
        JsonObject settingsJson = settingsJsonElement.getAsJsonObject();
        JsonElement imitateJson = settingsJson.get("imitate");
        if (imitateJson != null) {
            try {
                String blockToImitate = imitateJson.getAsString();
                Identifier blockIdentifier = Identifier.splitOn(blockToImitate, ':');
                Block block = Registry.BLOCK.get(blockIdentifier);
                if (block == Blocks.AIR) {
                    return blockTypeSettingsErrorMessage();
                } else {
                    return FabricBlockSettings.copyOf(block);
                }
            } catch (ClassCastException | IllegalStateException e) {
                return blockTypeSettingsErrorMessage();
            }
        }
        return FabricBlockSettings.copyOf(Blocks.STONE);
    }

    public FabricBlockSettings blockTypeSettingsErrorMessage() {
        String msg = String.format("Blockpack %s contains invalid settings for type %s. Inheriting settings from minecraft:stone", packDir, typeName);
        Chisel.LOGGER.info(msg);
        return FabricBlockSettings.copyOf(Blocks.STONE);
    }

}
