package com.matthewperiut.chisel.legacy;

import com.matthewperiut.chisel.legacy.config.BlockVariant;
import net.devtech.arrp.json.models.JModel;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;

public class Ref {
    private Ref() {}

    //public static final ScreenHandlerType<ChiselScreenHandler> CHISEL_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(Ref.MOD_ID, "chisel_screen_handler"), ChiselScreenHandler::new);

    public static final String MOD_ID = "chisel";
    public static final JModel CUBE_TWO_LAYER_TOPSHADED =
            JModel.model("block/block")
                    .element(
                            JModel.element()
                                    .from(0, 0, 0)
                                    .to(16, 16, 16)
                                    .shade()
                                    .faces(JModel.faces()
                                            .down(JModel.face("bot").cullface(Direction.DOWN).uv(0, 0, 16, 16))
                                            .up(JModel.face("bot").cullface(Direction.UP).uv(0, 0, 16, 16))
                                            .north(JModel.face("bot").cullface(Direction.NORTH).uv(0, 0, 16, 16))
                                            .south(JModel.face("bot").cullface(Direction.SOUTH).uv(0, 0, 16, 16))
                                            .east(JModel.face("bot").cullface(Direction.EAST).uv(0, 0, 16, 16))
                                            .west(JModel.face("bot").cullface(Direction.WEST).uv(0, 0, 16, 16))
                                    ),
                            JModel.element()
                                    .from(0, 0, 0)
                                    .to(16, 16, 16)
                                    .shade()
                                    .faces(JModel.faces()
                                            .down(JModel.face("top").cullface(Direction.DOWN).uv(0, 0, 16, 16))
                                            .up(JModel.face("top").cullface(Direction.UP).uv(0, 0, 16, 16))
                                            .north(JModel.face("top").cullface(Direction.NORTH).uv(0, 0, 16, 16))
                                            .south(JModel.face("top").cullface(Direction.SOUTH).uv(0, 0, 16, 16))
                                            .west(JModel.face("top").cullface(Direction.WEST).uv(0, 0, 16, 16))
                                            .east(JModel.face("top").cullface(Direction.EAST).uv(0, 0, 16, 16))
                                    )
                    );

    public static final JModel CUBE_THREE_LAYER_TOPSHADED =
            JModel.model("block/block")
                    .element(
                            JModel.element()
                                    .from(0, 0, 0)
                                    .to(16, 16, 16)
                                    .shade()
                                    .faces(JModel.faces()
                                            .down(JModel.face("bot").cullface(Direction.DOWN).uv(0, 0, 16, 16))
                                            .up(JModel.face("bot").cullface(Direction.UP).uv(0, 0, 16, 16))
                                            .north(JModel.face("bot").cullface(Direction.NORTH).uv(0, 0, 16, 16))
                                            .south(JModel.face("bot").cullface(Direction.SOUTH).uv(0, 0, 16, 16))
                                            .east(JModel.face("bot").cullface(Direction.EAST).uv(0, 0, 16, 16))
                                            .west(JModel.face("bot").cullface(Direction.WEST).uv(0, 0, 16, 16))
                                    ),
                            JModel.element()
                                    .from(0, 0, 0)
                                    .to(16, 16, 16)
                                    .shade()
                                    .faces(JModel.faces()
                                            .down(JModel.face("mid").cullface(Direction.DOWN).uv(0, 0, 16, 16))
                                            .up(JModel.face("mid").cullface(Direction.UP).uv(0, 0, 16, 16))
                                            .north(JModel.face("mid").cullface(Direction.NORTH).uv(0, 0, 16, 16))
                                            .south(JModel.face("mid").cullface(Direction.SOUTH).uv(0, 0, 16, 16))
                                            .east(JModel.face("mid").cullface(Direction.EAST).uv(0, 0, 16, 16))
                                            .west(JModel.face("mid").cullface(Direction.WEST).uv(0, 0, 16, 16))
                                    ),
                            JModel.element()
                                    .from(0, 0, 0)
                                    .to(16, 16, 16)
                                    .shade()
                                    .faces(JModel.faces()
                                            .down(JModel.face("top").cullface(Direction.DOWN).uv(0, 0, 16, 16))
                                            .up(JModel.face("top").cullface(Direction.UP).uv(0, 0, 16, 16))
                                            .north(JModel.face("top").cullface(Direction.NORTH).uv(0, 0, 16, 16))
                                            .south(JModel.face("top").cullface(Direction.SOUTH).uv(0, 0, 16, 16))
                                            .east(JModel.face("top").cullface(Direction.EAST).uv(0, 0, 16, 16))
                                            .west(JModel.face("top").cullface(Direction.WEST).uv(0, 0, 16, 16))
                                    )
                    );



    public static final String STONE = "stone";
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
    private static final String[] VARIANTS_STONE_PILLAR = {
            "pillar",
            "twisted"
    };
    public static List<BlockVariant> getStoneVariants(String packDir, String typeName) {
        List<BlockVariant> variants = new ArrayList<>();
        FabricBlockSettings settings = FabricBlockSettings.copyOf(Blocks.STONE);

        for (int i = 0; i < VARIANTS_STONE_STANDARD.length; i++) {
            variants.add(new BlockVariant(packDir, typeName, VARIANTS_STONE_STANDARD[i], settings, "block/cube_all", false));
        }
        for (int i = 0; i < VARIANTS_STONE_PILLAR.length; i++) {
            variants.add(new BlockVariant(packDir, typeName, VARIANTS_STONE_PILLAR[i], settings, "block/cube_column", false));
        }
        return variants;
    }

    public static final String METAL = "metal";
    private static final String[] VARIANTS_METAL_STANDARD = {
            "caution",
            "crate",
            "machine",
            "badgreggy",
            "bolted",
            "scaffold"
    };
    private static final String[] VARIANTS_METAL_SIDED = {
            "thermal"
    };
    public static List<BlockVariant> getMetalVariants(String packDir, String typeName) {
        List<BlockVariant> variants = new ArrayList<>();
        FabricBlockSettings settings = FabricBlockSettings.copyOf(Blocks.IRON_BLOCK);

        for (int i = 0; i < VARIANTS_METAL_STANDARD.length; i++) {
            variants.add(new BlockVariant(packDir, typeName, VARIANTS_METAL_STANDARD[i], settings, "block/cube_all", false));
        }
        for (int i = 0; i < VARIANTS_METAL_SIDED.length; i++) {
            variants.add(new BlockVariant(packDir, typeName, VARIANTS_METAL_SIDED[i], settings, "block/cube_bottom_top", false));
        }
        return variants;
    }

    public static final String PLANKS = "planks";
    private static final String[] VARIANTS_PLANKS_STANDARD = {
            "blinds",
            "chaotic",
            "chaotic-hor",
            "clean",
            "crate",
            "crateex",
            "crate-fancy",
            "fancy",
            "large",
            "panel-nails",
            "parquet",
            "short",
            "vertical",
            "vertical-uneven"
    };
    private static final String[] VARIANTS_PLANKS_PILLAR = {
            "double"
    };
    public static List<BlockVariant> getPlanksVariants(String packDir, String typeName) {
        List<BlockVariant> variants = new ArrayList<>();
        FabricBlockSettings settings = FabricBlockSettings.copyOf(Blocks.OAK_PLANKS);

        for (int i = 0; i < VARIANTS_PLANKS_STANDARD.length; i++) {
            variants.add(new BlockVariant(packDir, typeName, VARIANTS_PLANKS_STANDARD[i], settings, "block/cube_all", false));
        }
        for (int i = 0; i < VARIANTS_PLANKS_PILLAR.length; i++) {
            variants.add(new BlockVariant(packDir, typeName, VARIANTS_PLANKS_PILLAR[i], settings, "block/cube_column", false));
        }
        return variants;
    }

    public static List<BlockVariant> getInheritedVariants(String packDir, String typeName, String parent) {
        if (parent.equals(STONE)) {
            return getStoneVariants(packDir, typeName);
        }
        if (parent.equals(METAL)) {
            return getMetalVariants(packDir, typeName);
        }
        if (parent.equals(PLANKS)) {
            return getPlanksVariants(packDir, typeName);
        }
        return new ArrayList<>();
    }
}