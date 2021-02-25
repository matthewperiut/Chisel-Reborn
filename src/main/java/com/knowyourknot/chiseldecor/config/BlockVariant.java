package com.knowyourknot.chiseldecor.config;

import com.knowyourknot.chiseldecor.ChiselDecorEntryPoint;
import com.knowyourknot.chiseldecor.Ref;
import com.knowyourknot.chiseldecor.block.ChiselBlockCarpet;
import com.knowyourknot.chiseldecor.item.ChiselBlockItem;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.models.JModel;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.GlassBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockVariant {
    private final String packDir;
    private final String typeName;
    private final String variantName;
    private final Block block;
    private final String model;

    public BlockVariant(String packDir, String typeName, String variantName, FabricBlockSettings settings, String model, boolean transparent) {
        this.packDir = packDir;
        this.typeName = typeName;
        this.variantName = variantName;
        this.model = model;
        this.block = makeBlock(settings, transparent);
    }

    public String getName() {
        return packDir + "/" + typeName + "/" + variantName;
    }

    public String getVariantName() {
        return variantName;
    }

    public Identifier identifier() {
        return new Identifier(Ref.MOD_ID, getName());
    }

    public Block makeBlock(FabricBlockSettings settings, boolean transparent) {
        FabricBlockSettings tempSettings = FabricBlockSettings.copyOf(settings);
        if (transparent) {
            tempSettings.nonOpaque();
        }
        if (model.equals("block/carpet")) {
            return new ChiselBlockCarpet(tempSettings);
        }
        if (transparent) {
            Block tempBlock = new GlassBlock(tempSettings);
            ChiselDecorEntryPoint.TRANSPARENT_BLOCKS.add(tempBlock);
            return tempBlock;
        }
        return new Block(tempSettings);
    }

    public void registerBlock() {       
        Registry.register(Registry.BLOCK, identifier(), block);
        ChiselBlockItem item = new ChiselBlockItem(block, new FabricItemSettings().group(Ref.ITEM_GROUP), packDir, typeName, variantName);
        Registry.register(Registry.ITEM, identifier(), item);

    }

    public void registerDrop(RuntimeResourcePack resourcePack) {
        JLootTable table = JLootTable.loot("minecraft:block").pool(JLootTable.pool().rolls(1).entry(JLootTable.entry().type("minecraft:item").name(identifier().toString())).condition(JLootTable.predicate("minecraft:survives_explosion")));
        resourcePack.addLootTable(new Identifier(Ref.MOD_ID, "blocks/" + packDir + "/" + typeName + "/" + variantName), table);
    }

    public void registerBlockModel(RuntimeResourcePack resourcePack) {
        String texturePath = Ref.MOD_ID + ":blocks/" + getName();
        JModel blockModel;
        if (model.equals("block/cube_column")) {
            String textureSide = texturePath + "-side";
            String textureEnd = texturePath + "-top";
            blockModel = JModel.model(model).textures(JModel.textures()
                .var("side", textureSide)
                .var("end", textureEnd)
            );
        } else if (model.equals("block/cube_bottom_top")) {
            String textureSide = texturePath + "-side";
            String textureTop = texturePath + "-top";
            String textureBottom = texturePath + "-bottom";
            blockModel = JModel.model(model).textures(JModel.textures()
                .var("side", textureSide)
                .var("top", textureTop)
                .var("bottom", textureBottom)
            );
        } else if (model.equals("two_layer_topshaded")) {
            String textureParticle = texturePath + "-particle";
            String textureTop = texturePath + "-top";
            String textureBot = texturePath + "-bot";
            blockModel = Ref.CUBE_TWO_LAYER_TOPSHADED.clone().textures(JModel.textures()
                .var("particle", textureParticle)
                .var("top", textureTop)
                .var("bot", textureBot)
            );
        } else if (model.equals("three_layer_topshaded")) {
            String textureParticle = texturePath + "-particle";
            String textureTop = texturePath + "-top";
            String textureMid = texturePath + "-mid";
            String textureBot = texturePath + "-bot";
            blockModel = Ref.CUBE_THREE_LAYER_TOPSHADED.clone().textures(JModel.textures()
                .var("particle", textureParticle)
                .var("top", textureTop)
                .var("mid", textureMid)
                .var("bot", textureBot)
            );
        } else if (model.equals("block/carpet")) {
            blockModel = JModel.model(model).textures(JModel.textures()
                .var("wool", texturePath)
            );
        } else {
            blockModel = JModel.model("block/cube_all").textures(JModel.textures().var("all", texturePath));
        }
        resourcePack.addModel(blockModel, new Identifier(Ref.MOD_ID, "block/" + getName()));  
    }

    public void registerItemModel(RuntimeResourcePack resourcePack) {
        String modelPath = Ref.MOD_ID + ":block/" + getName();
        JModel itemModel = JModel.model(modelPath);
        resourcePack.addModel(itemModel, new Identifier(Ref.MOD_ID, "item/" + getName()));
    }

    public void registerBlockState(RuntimeResourcePack resourcePack) {
        JState blockState = JState.state(JState.variant(JState.model(Ref.MOD_ID + ":block/" + getName())));
        resourcePack.addBlockState(blockState, identifier());
    }
}
