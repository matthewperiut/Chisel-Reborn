package com.knowyourknot.chiseldecor.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.fabricmc.loader.api.FabricLoader;

public class DefaultBlockPack {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File BLOCKPACK_DIRECTORY = new File(FabricLoader.getInstance().getConfigDir().toFile(), "chiseldecor/default");
    private static final List<BlockTypeData> blockTypeData = new ArrayList<>();

    private DefaultBlockPack() {}

    public static void deleteDefaultBlockPack() {
        if (!BLOCKPACK_DIRECTORY.exists()) {
            return;
        }
        deleteAllFilesInFolder(BLOCKPACK_DIRECTORY);

    }

    public static void deleteAllFilesInFolder(File dir) {
        File[] dirFilesArr = dir.listFiles();
        for (int i = 0; i < dirFilesArr.length; i++) {
            File dirFile = dirFilesArr[i];
            if (dirFile.isDirectory()) {
                deleteAllFilesInFolder(dirFile);
            } else {
                try {
                    Files.delete(dirFile.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            }
        }
    }

    public static void createDefaultBlockPack() {
        if (!BLOCKPACK_DIRECTORY.exists()) {
            BLOCKPACK_DIRECTORY.mkdir();
        }
        createBlockTypes();
        writeFiles();
    }

    public static void createBlockTypes() {
        add(metal("aluminium").tags("c:aluminium_blocks"));
        add(stone("andesite").tags("c:andesite").items(new String[]{"minecraft:andesite", "minecraft:polished_andesite"}));
        add(stone("basalt").addVariants("raw").tags("c:basalt").items(new String[]{"minecraft:basalt", "minecraft:polished_basalt"}));
        add(stone("bricks").tags("c:clay_bricks").items("minecraft:bricks"));
        add(metal("bronze").tags("c:bronze_blocks"));
        add(stone("block_charcoal").tags("c:charcoal_blocks").items("minecraft:charcoal_block"));
        add(stone("block_coal").tags("c:coal_blocks").items("minecraft:coal_block"));
        add(metal("cobalt").tags("c:cobalt_blocks"));
        add(stone("cobblestone").tags("c:cobblestone").items("minecraft:cobblestone"));
        // TODO add mossy cobblestone with CTM wizardry
        for (int i = 0; i < COLOURS.length; i++) {
            String concreteName = "concrete_" + COLOURS[i];
            add(stone(concreteName).items(COLOURS[i] + "_concrete").tags("c:" + concreteName));
        }
        add(metal("copper").tags("c:copper_blocks"));
        add(new BlockTypeData("diamond", "minecraft:diamond_block").tags("c:diamond_blocks").items("minecraft:diamond_block").addVariants(VARIANTS_DIAMOND).addVariants(VARIANTS_DIAMOND_SIDED, SIDED, false));
        add(stone("diorite").tags("c:diorite").items(new String[]{"minecraft:diorite", "minecraft:polished_diorite"}));
        add(new BlockTypeData("dirt", "minecraft:dirt").items(new String[]{"minecraft:dirt", "minecraft:coarse_dirt"}).tags("c:dirt").addVariants(VARIANTS_DIRT).addVariants(VARIANTS_DIRT_PILLAR, PILLAR, false));
        add(metal("electrum").tags("c:electrum_blocks"));
        add(new BlockTypeData("emerald", "minecraft:emerald_block").items("minecraft:emerald_block").tags("c:emerald_blocks").addVariants(VARIANTS_EMERALD));
        add(stone("purpur").tags("c:purpur_blocks").items(new String[]{"minecraft:purpur_block", "minecraft:purpur_pillar"}));
        add(stone("endstone").tags("c:end_stones").items("minecraft:end_stone"));
        add(new BlockTypeData("factory", "minecraft:iron_block").addVariants(VARIANTS_FACTORY).addVariants(VARIANTS_FACTORY_PILLAR, PILLAR, false));
        add(new BlockTypeData("futura", "minecraft:iron_block").addVariants(VARIANTS_FUTURA_THREE_LAYER_TOPSHADED, "three_layer_topshaded", false).addVariants(VARIANTS_FUTURA).addVariants(VARIANTS_FUTURA_TWO_LAYER_TOPSHADED, "two_layer_topshaded", false));
        add(new BlockTypeData("glass", "minecraft:glass").tags("c:glass").items("minecraft:glass").addVariants(VARIANTS_GLASS, "", true));
        for (int i = 0; i < COLOURS.length; i++) {
            String glassName = "glass_stained/" + COLOURS[i];
            BlockTypeData glassBlockData = new BlockTypeData(glassName, "minecraft:glass").tags("c:glass_" + COLOURS[i]).items(COLOURS[i] + "_stained_glass").addVariants(VARIANTS_GLASS_STAINED, "", true);
            for (int j = 0; j < VARIANTS_GLASS_STAINED_NAMED.length; j++) {
                glassBlockData.addVariants(new String[]{COLOURS[i] + "-" + VARIANTS_GLASS_STAINED_NAMED[j]}, "", true);
            }
            add(glassBlockData);
        }
        // glass panes
        add(new BlockTypeData("glowstone", "minecraft:glowstone").tags("c:glowstone_blocks").items("minecraft:glowstone").addVariants(VARIANTS_STONE_STANDARD).remove(new String[]{"weaver", "zag", "bricks-chaotic", "cuts"}).addVariants(VARIANTS_GLOWSTONE));
        add(metal("gold").tags("c:gold_blocks").items("minecraft:gold_block").addVariants(VARIANTS_GOLD).addVariants(VARIANTS_GOLD_SIDED, SIDED, false));
        add(stone("granite").tags("c:granite").items(new String[]{"minecraft:granite", "minecraft:polished_granite"}));
        add(stone("hardenedclay").tags("c:terracotta_blocks_plain").items("minecraft:terracotta"));
        // terracotta colours
        add(new BlockTypeData("ice", "minecraft:ice").tags("c:ice_default").items("minecraft:ice").addVariants(VARIANTS_STONE_STANDARD, "", true));
        add(new BlockTypeData("icepillar", "minecraft:ice").customChiselGroup("default/ice").tags("c:ice_default").addVariants(VARIANTS_PILLAR, "", true).addVariants(VARIANTS_PILLAR_PILLAR, PILLAR, true).remove(new String[] {"rough", "wideplain", "simple", "default", "pillar", "widedecor", "convex", "widegreek", "greekdecor", "plaindecor"}));
        add(metal("invar").tags("c:invar_blocks"));
        add(metal("iron").tags("c:iron_blocks").items("minecraft:iron_block").addVariants(VARIANTS_IRON).addVariants(VARIANTS_IRON_SIDED, SIDED, false).addVariants(VARIANTS_IRON_PILLAR, PILLAR, false));
        // iron bars
        add(new BlockTypeData("laboratory", "minecraft:iron_block").tags("c:laboratory_blocks").addVariants(VARIANTS_LABORATORY).addVariants(VARIANTS_LABORATORY_PILLAR, PILLAR, false));
        add(new BlockTypeData("lapis", "minecraft:lapis_block").tags("c:lapis_blocks").items("minecraft:lapis_block").addVariants(VARIANTS_LAPIS));
        // lavastone
        add(metal("lead").tags("c:lead_blocks"));
        // leaves
        add(stone("limestone").tags("c:limestone").addVariants("raw"));
        add(stone("marble").tags("c:marble").addVariants("raw"));
        add(new BlockTypeData("marblepillar", "minecraft:stone").customChiselGroup("default/marble").tags("c:marble").addVariants(VARIANTS_PILLAR).addVariants(VARIANTS_PILLAR_PILLAR, PILLAR, false));
        add(new BlockTypeData("netherbrick", "minecraft:nether_bricks").tags("c:nether_bricks").items(new String[]{"minecraft:nether_bricks", "minecraft:cracked_nether_bricks", "minecraft:chiseled_nether_bricks"}).addVariants(VARIANTS_NETHERBRICK));
        add(new BlockTypeData("netherrack", "minecraft:netherrack").tags("c:netherrace").items("minecraft:netherrace").addVariants(VARIANTS_NETHERRACK));
        add(metal("nickel").tags("c:nickel_blocks"));
        add(new BlockTypeData("obsidian", "minecraft:obsidian").tags("c:obsidian").items("minecraft:obsidian").addVariants(VARIANTS_OBSIDIAN).addVariants(VARIANTS_OBSIDIAN_SIDED, SIDED, false).addVariants(VARIANTS_OBSIDIAN_PILLAR, PILLAR, false));
        add(planks("acacia").tags("c:acacia_planks").items("minecraft:acacia_planks"));
        add(planks("birch").tags("c:birch_planks").items("minecraft:birch_planks"));
        add(planks("dark-oak").tags("c:dark_oak_planks").items("minecraft:dark_oak_planks"));
        add(planks("jungle").tags("c:jungle_planks").items("minecraft:jungle_planks"));
        add(planks("oak").tags("c:oak_planks").items("minecraft:oak_planks"));
        add(planks("spruce").tags("c:spruce_planks").items("minecraft:spruce_planks"));
        add(metal("platinum").tags("c:platinum_blocks"));
        add(stone("prismarine").tags("c:prismarine").items(new String[]{"minecraft:prismarine", "minecraft:prismarine_bricks", "minecraft:dark_prismarine"}));
        add(stone("quartz").tags("c:quartz_blocks").items(new String[]{"minecraft:quartz_block", "minecraft:chiseled_quartz_block", "minecraft:quartz_pillar", "minecraft:quartz_bricks"}));
        add(new BlockTypeData("redstone", "minecraft:redstone_block").tags("c:redstone_blocks").items("minecraft:redstone_block").addVariants(VARIANTS_STONE_STANDARD).remove(new String[]{"weaver", "zag", "bricks-chaotic", "cuts"}));
        add(stone("sandstoneyellow").tags("c:sandstone").items(new String[]{"minecraft:sandstone", "minecraft:cut_sandstone", "minecraft:chiseled_sandstone", "minecraft:smooth_sandstone"}));
        add(stone("sandstonered").tags("c:red_sandstones").items(new String[]{"minecraft:red_sandstone", "minecraft:cut_red_sandstone", "minecraft:chiseled_red_sandstone", "minecraft:smooth_red_sandstone"}));
        BlockTypeData scribblesData = new BlockTypeData("sandstone-scribbles", "minecraft:sandstone").customChiselGroup("default/sandstoneyellow");
        BlockTypeData scribblesRedData = new BlockTypeData("sandstonered-scribbles", "minecraft:sandstone").customChiselGroup("default/sandstonered");
        String[] scribbles = new String[16];
        for (int i = 0; i < 16; i++) {
            scribbles[i] = "scribbles-" + i;
        }
        scribblesData.addVariants(scribbles, PILLAR, false);
        scribblesRedData.addVariants(scribbles, PILLAR, false);
        add(scribblesData);
        add(scribblesRedData);

        add(metal("silver").tags("c:silver_blocks"));
        add(metal("steel").tags("c:steel_blocks"));
        add(stone("stone").tags("c:grey_stones").items(new String[]{"minecraft:stone", "minecraft:smooth_stone", "minecraft:stone_bricks", "minecraft:cracked_stone_bricks", "minecraft:chiseled_stone_bricks"}).addVariants(new String[]{"largeornate", "poison", "sunken"}));
        add(new BlockTypeData("technical", "minecraft:iron_block").customChiselGroup("default/factory").tags("c:technical_blocks").addVariants(VARIANTS_TECHNICAL).addVariants(VARIANTS_TECHNICAL_SIDED, SIDED, false).addVariants(VARIANTS_TECHNICAL_PILLAR, PILLAR, false).addVariants(VARIANTS_TECHNICAL_PILLAR_TRANSPARENT, PILLAR, true));
        // temple & templemossy - ctm
        add(metal("tin").tags("c:tin_blocks"));
        add(new BlockTypeData("tyrian", "minecraft:iron_block").tags("c:tyrian_blocks").addVariants(VARIANTS_TYRIAN));
        add(metal("uranium").tags("c:uranium_blocks"));
        // voidstone?
        // stone("waterstone") - ctm
        for (int i = 0; i < COLOURS.length; i++) {
            BlockTypeData woolData = new BlockTypeData("wool/" + COLOURS[i], "minecraft:white_wool");
            BlockTypeData carpetData = new BlockTypeData("wool_carpet/" + COLOURS[i], "minecraft:white_carpet");
            woolData.tags("c:" + COLOURS[i] + "_wool").items("minecraft:" + COLOURS[i] + "_wool");
            carpetData.tags("c:" + COLOURS[i] + "_carpet").items("minecraft:" + COLOURS[i] + "_carpet");
            woolData.addVariants(new String[]{COLOURS[i], COLOURS[i] + "-llama"});
            carpetData.addVariants(new String[]{COLOURS[i], COLOURS[i] + "-llama"}, CARPET, false);
            add(woolData);
            add(carpetData);
        }
    }

    public static void add(BlockTypeData typeData) {
        blockTypeData.add(typeData);
    }

    public static BlockTypeData stone(String typeName) {
        return new BlockTypeData(typeName, "minecraft:stone").parent("stone");
    } 

    public static BlockTypeData planks(String typeName) {
        return new BlockTypeData("planks-" + typeName, "minecraft:oak_planks").parent("planks");
    } 

    public static BlockTypeData metal(String typeName) {
        return new BlockTypeData("metals/" + typeName, "minecraft:iron_block").parent("metal");
    }

    public static void writeFiles() {
        for (int i = 0; i < blockTypeData.size(); i++) {
            BlockTypeData data = blockTypeData.get(i);
            String typeName = data.getName();
            JsonObject json = data.toJson();

            // from https://github.com/OroArmor/Oro-Config/blob/master/src/main/java/com/oroarmor/config/Config.java
            // method saveConfigToFile
            File blockTypeFile = new File(BLOCKPACK_DIRECTORY, typeName + ".json");
            blockTypeFile.getParentFile().mkdirs();
            try (FileOutputStream stream = new FileOutputStream(blockTypeFile)) {
                stream.write(GSON.toJson(json).getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class BlockTypeData {
        private final String name;
        private final String imitate;
        private String chiselGroupName = "";
        private String[] tags = new String[]{};
        private String[] items = new String[]{};
        private String parent = "";
        private String[] remove = new String[]{};
        private final List<BlockVariantData> variants = new ArrayList<>();

        public BlockTypeData(String name, String imitate) {
            this.name = name;
            this.imitate = imitate;
        }

        public String getName() {
            return this.name;
        }

        public BlockTypeData customChiselGroup(String groupName) {
            this.chiselGroupName = groupName;
            return this;
        }

        public BlockTypeData tags(String tag) {
            this.tags = new String[]{tag};
            return this;
        }

        public BlockTypeData tags(String[] tags) {
            this.tags = tags;
            return this;
        }

        public BlockTypeData items(String item) {
            this.items = new String[]{item};
            return this;
        }

        public BlockTypeData items(String[] items) {
            this.items = items;
            return this;
        }

        public BlockTypeData parent(String parent) {
            this.parent = parent;
            return this;
        }

        public BlockTypeData remove(String[] remove) {
            this.remove = remove;
            return this;
        }

        public BlockTypeData addVariants(String name) {
            this.variants.add(new BlockVariantData(new String[]{name}, "", false));
            return this;
        }

        public BlockTypeData addVariants(String[] names) {
            this.variants.add(new BlockVariantData(names, "", false));
            return this;
        }

        public BlockTypeData addVariants(String[] names, String model, boolean transparent) {
            this.variants.add(new BlockVariantData(names, model, transparent));
            return this;
        }

        public JsonObject toJson() {
            JsonObject json = new JsonObject();
            json.addProperty("name", name);

            JsonObject settings = new JsonObject();
            settings.addProperty("imitate", imitate);
            json.add("settings", settings);

            if (!chiselGroupName.equals("")) {
                json.addProperty("chisel_group", chiselGroupName);
            }

            if (items.length != 0) {
                JsonArray itemsJson = new JsonArray();
                for (int i = 0; i < items.length; i++) {
                    itemsJson.add(items[i]);
                }
                json.add("items_to_include", itemsJson);
            }

            if (tags.length != 0) {
                JsonArray tagsJson = new JsonArray();
                for (int i = 0; i < tags.length; i++) {
                    tagsJson.add(tags[i]);
                }
                json.add("tags_to_include", tagsJson);
            }

            JsonObject variantJson = new JsonObject();
            if (!parent.equals("")) {
                variantJson.addProperty("parent", parent);
            }
            if (remove.length != 0) {
                JsonArray removeJson = new JsonArray();
                for (int i = 0; i < remove.length; i++) {
                    removeJson.add(remove[i]);
                }
                variantJson.add("remove", removeJson);
            }
            JsonArray newJson = new JsonArray();
            for (int i = 0; i < variants.size(); i++) {
                newJson.add(variants.get(i).toJson());
            }
            variantJson.add("new", newJson);
            json.add("variants", variantJson);
            
            return json;
        }

        private static class BlockVariantData {
            private final String[] names;
            private final String model;
            private final boolean transparent;
            
            public BlockVariantData(String[] names, String model, boolean transparent) {
                this.names = names;
                this.model = model;
                this.transparent = transparent;
            }

            public JsonObject toJson() {
                JsonObject variantJson = new JsonObject();
                JsonArray namesJson = new JsonArray();
                for (int i = 0; i < names.length; i++) {
                    namesJson.add(names[i]);
                }
                variantJson.add("names", namesJson);
                if (!model.equals("")) {
                    variantJson.addProperty("model", model);
                }
                variantJson.addProperty("transparent", transparent);
                return variantJson;
            }
        }
    }

    private static final String SIDED = "block/cube_bottom_top";
    private static final String PILLAR = "block/cube_column";
    private static final String CARPET = "block/carpet";

    private static final String[] COLOURS = {
        "black",
        "blue",
        "brown",
        "cyan",
        "gray",
        "green",
        "light_blue",
        "light_gray",
        "lime",
        "magenta",
        "orange",
        "pink",
        "purple",
        "red",
        "white",
        "yellow"
    };

    private static final String[] VARIANTS_STONE_STANDARD = {
        "cracked",
        "bricks-soft",
        "bricks-cracked",
        "bricks-encased",
        "braid",
        "array",
        "tiles-large",
        "tiles-small",
        "chaotic-medium",
        "chaotic-small",
        "dent",
        "french-1",
        "french-2",
        "jellybean",
        "layers",
        "mosaic",
        "ornate",
        "panel",
        "road",
        "slanted",
        // "zag", - issue with connected texture - Invalid texture type provided: "ar"
        "weaver",
        "bricks-solid",
        "bricks-small",
        "circular",
        "tiles-medium",
        "prism",
        "bricks-chaotic",
        "cuts",
        "bricks-triple"
    };

    private static final String[] VARIANTS_PILLAR = {
        "convex",
        "default",
        "greekdecor",
        "greekgreek",
        "greekplain",
        "pillar",
        "plaindecor",
        "plaingreek",
        "plainplain",
        "rough",
        "simple",
        "widedecor",
        "widegreek",
        "wideplain"
    };

    private static final String[] VARIANTS_PILLAR_PILLAR = {
        "carved",
        "ornamental"
    };

    private static final String[] VARIANTS_BOOKSHELF = {
        "abandoned",
        "brim",
        "cans",
        "historican",
        "hoarder",
        "necromancer",
        "necromancer-novice",
        "papers",
        "rainbow",
        "redtomes"
    };

    private static final String[] VARIANTS_DIAMOND = {
        "terrain-diamond-cells",
        "terrain-diamond-space",
        "terrain-diamond-spaceblack",
        "terrain-diamond-bismuth",
        "terrain-diamond-crushed",
        "terrain-diamond-four",
        "terrain-diamond-fourornate",
        "terrain-diamond-zelda",
        "terrain-diamond-ornatelayer"
    };

    private static final String[] VARIANTS_DIAMOND_SIDED = {
        "terrain-diamond-embossed",
        "terrain-diamond-gem",
        "terrain-diamond-simple"
    };

    private static final String[] VARIANTS_DIRT = {
        "bricks",
        "netherbricks",
        "bricks3",
        "cobble",
        "reinforcedcobbledirt",
        "reinforceddirt",
        "happy",
        "bricks2",
        "bricksdirt2",
        "chunky",
        "horizontal",
        "plate",
        "layers"
    };

    private static final String[] VARIANTS_DIRT_PILLAR = {
        "hor",
        "vertical",
        "vert",
    };

    private static final String[] VARIANTS_EMERALD = {
        "panel",
        "panelclassic",
        "smooth",
        "chunk",
        "goldborder",
        "zelda",
        "cell",
        "cellbismuth",
        "four",
        "fourornate",
        "ornate",
        "masonryemerald",
        "emeraldcircle",
        "emeraldprismatic"
    };

    private static final String[] VARIANTS_FACTORY = {
        "rust2",
        "dots",
        "rust",
        "platex",
        "wireframewhite",
        "wireframe",
        "hazard",
        "hazardorange",
        "circuit",
        "goldplate",
        "goldplating",
        "grinder",
        "plating",
        "rustplates",
        "frameblue",
        "iceiceice",
        "tilemosaic",
        "wireframeblue",
        "column",
        "vent"
    };

    private static final String[] VARIANTS_FACTORY_PILLAR = {
        "metalbox",
    };

    private static final String[] VARIANTS_FUTURA = {
        "screen_cyan",
        "screen_metallic"
        // "circuit_plate" this is incomplete
    };

    private static final String[] VARIANTS_FUTURA_TWO_LAYER_TOPSHADED = {
        "rainbow_wave"
    };

    private static final String[] VARIANTS_FUTURA_THREE_LAYER_TOPSHADED = {
        "controller",
        "controller_purple",
        "uber_wavy"
    };

    private static final String[] VARIANTS_GLOWSTONE = {
        "bismuth",
        "tiles-large-bismuth",
        "tiles-medium-bismuth",
        "neon",
        "neon-panel"
    };

    private static final String[] VARIANTS_GOLD = {
        "goldeye"
    };

    private static final String[] VARIANTS_GOLD_SIDED = {
        "terrain-gold-simple",
        "terrain-gold-largeingot",
        "terrain-gold-smallingot",
        "terrain-gold-plates",
        "terrain-gold-brick",
        "terrain-gold-cart",
        "terrain-gold-coin-heads",
        "terrain-gold-coin-tails",
        "terrain-gold-crate-dark",
        "terrain-gold-crate-light",
        "terrain-gold-rivets",
        "terrain-gold-star",
        "terrain-gold-space",
        "terrain-gold-spaceblack",
    };

    // same variants for panes
    private static final String[] VARIANTS_GLASS = {
        "terrain-glassbubble",
        "chinese",
        "japanese",
        "terrain-glassdungeon",
        "terrain-glasslight",
        "terrain-glassnoborder",
        "terrain-glass-ornatesteel",
        "terrain-glass-screen",
        "terrain-glassshale",
        "terrain-glass-steelframe",
        "terrain-glassstone",
        "terrain-glassstreak",
        "terrain-glass-thickgrid",
        "terrain-glass-thingrid",
        "a1-glasswindow-ironfencemodern",
        "chrono",
        "chinese2",
        "japanese2"
    };

    private static final String[] VARIANTS_GLASS_STAINED = {
        "brick",
        // "framed", texture broken
        "panel",
        "rough",
        "streaks"
    };

    private static final String[] VARIANTS_GLASS_STAINED_NAMED = {
        "bubble",
        "forestry",
        "panel",
        "panel-fancy",
        "transparent"
    };

    private static final String[] VARIANTS_IRON = {
        "terrain-iron-space",
        "terrain-iron-spaceblack"
    };

    private static final String[] VARIANTS_IRON_SIDED = {
        "terrain-iron-simple",
        "terrain-iron-largeingot",
        "terrain-iron-smallingot",
        "terrain-iron-plates",
        "terrain-iron-brick",
        "terrain-iron-coin-heads",
        "terrain-iron-coin-tails",
        "terrain-iron-moon",
        "terrain-iron-crate-dark",
        "terrain-iron-crate-light",
        "terrain-iron-rivets"
    };

    private static final String[] VARIANTS_IRON_PILLAR = {
        "terrain-iron-vents",
        "terrain-iron-gears"
    };

    private static final String[] VARIANTS_LABORATORY = {
        "largewall",
        "roundel",
        "largetile",
        "smalltile",
        "floortile",
        "checkertile",
        "clearscreen",
        "fuzzscreen",
        "wallvents",
        "wallpanel",
        "infocon"
    };

    private static final String[] VARIANTS_LABORATORY_PILLAR = {
        "dottedpanel",
        "directionleft",
        "directionright",
        "largesteel",
        "smallsteel",
    };

    private static final String[] VARIANTS_LAPIS = {
        "terrain-lapisblock-chunky",
        "terrain-lapisblock-panel",
        "terrain-lapisblock-zelda",
        "terrain-lapisornate",
        "terrain-lapistile",
        "a1-blocklapis-panel",
        "a1-blocklapis-smooth",
        "a1-blocklapis-ornatelayer",
        "masonrylapis"
    };

    private static final String[] VARIANTS_NETHERBRICK = {
        "a1-netherbrick-brinstar",
        "a1-netherbrick-classicspatter",
        "a1-netherbrick-guts",
        "a1-netherbrick-gutsdark",
        "a1-netherbrick-gutssmall",
        "a1-netherbrick-lavabrinstar",
        "a1-netherbrick-lavabrown",
        "a1-netherbrick-lavaobsidian",
        "a1-netherbrick-lavastonedark",
        "a1-netherbrick-meat",
        "a1-netherbrick-meatred",
        "a1-netherbrick-meatredsmall",
        "a1-netherbrick-meatsmall",
        "a1-netherbrick-red",
        "a1-netherbrick-redsmall",
        "netherfancybricks"
    };

    private static final String[] VARIANTS_NETHERRACK = {
        "a1-netherrack-bloodgravel",
        "a1-netherrack-bloodrock",
        "a1-netherrack-bloodrockgrey",
        "a1-netherrack-brinstar",
        "a1-netherrack-brinstarshale",
        "a1-netherrack-classic",
        "a1-netherrack-classicspatter",
        "a1-netherrack-guts",
        "a1-netherrack-gutsdark",
        "a1-netherrack-meat",
        "a1-netherrack-meatred",
        "a1-netherrack-meatrock",
        "a1-netherrack-red",
        "a1-netherrack-wells"
    };

    private static final String[] VARIANTS_OBSIDIAN = {
        "panel-shiny",
        "panel",
        "chunks",
        "growth",
        "crystal",
        "map-a",
        "map-b",
        "panel-light",
        "blocks",
        "tiles"
    };

    private static final String[] VARIANTS_OBSIDIAN_SIDED = {
        "crate"
    };

    private static final String[] VARIANTS_OBSIDIAN_PILLAR = {
        "greek",
        "pillar",
        "pillar-quartz",
        "chiseled"
    };

    private static final String[] VARIANTS_TECHNICAL = {
        "scaffold",
        "cautiontape",
        "industrialrelic",
        "pipeslarge",
        "pipessmall",
        "vent",
        "ventglowing",
        "insulationv2",
        "spinningstuffanim",
        "cables",
        "rustyboltedplates",
        "grate",
        "graterusty",
        "scaffoldtransparent",
        "massivefan",
        "massivehexplating",
        "weatheredgreenpanels",
        "weatheredorangepanels",
        "sturdy",
        "exhaustplating",
        "makeshiftpanels",
        "engineering",
        "scaffoldlarge",
        "piping",
        "tapedrive"
    };

    private static final String[] VARIANTS_TECHNICAL_SIDED = {
        "megacell"
    };

    private static final String[] VARIANTS_TECHNICAL_PILLAR = {
        "fanfast",
        "fanstill",
        "malfunctionfan",
    };

    private static final String[] VARIANTS_TECHNICAL_PILLAR_TRANSPARENT = {
        "fanfasttransparent",
        "fanstilltransparent"
    };

    private static final String[] VARIANTS_TYRIAN = {
        "shining",
        "tyrian",
        "chaotic",
        "softplate",
        "rust",
        "elaborate",
        "routes",
        "platform",
        "platetiles",
        "diagonal",
        "dent",
        "blueplating",
        "black",
        "black2",
        "opening",
        "plate"
    };


}
