package com.knowyourknot.chiseldecor.data;

import com.knowyourknot.chiseldecor.Ref;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.recipe.JIngredient;
import net.devtech.arrp.json.recipe.JKeys;
import net.devtech.arrp.json.recipe.JPattern;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.recipe.JResult;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Recipes {
    private Recipes() {}

    public static void addDefaultRecipes(RuntimeResourcePack resourcePack) {
        addChiselRecipe(resourcePack);
        Item factory = Registry.ITEM.get(new Identifier(Ref.MOD_ID, "default/factory/rust2"));
        if (!factory.equals(Items.AIR)) {
            addFactoryRecipe(resourcePack);
        }

        Item futura = Registry.ITEM.get(new Identifier(Ref.MOD_ID, "default/futura/controller"));
        if (!futura.equals(Items.AIR)) {
            addFuturaRecipe(resourcePack);
        }

        Item laboratory = Registry.ITEM.get(new Identifier(Ref.MOD_ID, "default/laboratory/largewall"));
        if (!laboratory.equals(Items.AIR)) {
            addLaboratoryRecipe(resourcePack);
        }

        Item tyrian = Registry.ITEM.get(new Identifier(Ref.MOD_ID, "default/tyrian/shining"));
        if (!tyrian.equals(Items.AIR)) {
            addTyrianRecipe(resourcePack);
        }
    }

    public static void addChiselRecipe(RuntimeResourcePack resourcePack) {
        JPattern pattern = JPattern.pattern("I ", " S");
        JPattern patternAlt = JPattern.pattern(" I", "S ");
        JIngredient stick = JIngredient.ingredient().item("minecraft:stick");
        JIngredient iron = JIngredient.ingredient().item("minecraft:iron_ingot");
        JKeys keys = JKeys.keys().key("S", stick).key("I", iron);
        JResult result = JResult.stackedResult("chiseldecor:chisel", 1);
        resourcePack.addRecipe(new Identifier(Ref.MOD_ID, "chisel"), JRecipe.shaped(pattern, keys, result));
        resourcePack.addRecipe(new Identifier(Ref.MOD_ID, "chisel_alt"), JRecipe.shaped(patternAlt, keys, result));
    }

    public static void addFactoryRecipe(RuntimeResourcePack resourcePack) {
        JPattern pattern = JPattern.pattern("ISI", "S S", "ISI");
        JIngredient stone = JIngredient.ingredient().item("minecraft:stone");
        JIngredient iron = JIngredient.ingredient().item("minecraft:iron_ingot");
        JKeys keys = JKeys.keys().key("S", stone).key("I", iron);
        JResult result = JResult.stackedResult("chiseldecor:default/factory/rust2", 32);
        resourcePack.addRecipe(new Identifier(Ref.MOD_ID, "default/factory"), JRecipe.shaped(pattern, keys, result));
    }

    public static void addFuturaRecipe(RuntimeResourcePack resourcePack) {
        JPattern pattern = JPattern.pattern("SSS", "SRS", "SSS");
        JIngredient stone = JIngredient.ingredient().item("minecraft:stone_bricks");
        JIngredient redstone = JIngredient.ingredient().item("minecraft:redstone");
        JKeys keys = JKeys.keys().key("S", stone).key("R", redstone);
        JResult result = JResult.stackedResult("chiseldecor:default/futura/controller", 8);
        resourcePack.addRecipe(new Identifier(Ref.MOD_ID, "default/futura"), JRecipe.shaped(pattern, keys, result));
    }

    public static void addVoidstoneRecipe(RuntimeResourcePack resourcePack) {
        JPattern pattern = JPattern.pattern("OSO", "SES", "OSO");
        JIngredient stone = JIngredient.ingredient().item("minecraft:stone");
        JIngredient obsidian = JIngredient.ingredient().item("minecraft:obsidian");
        JIngredient enderPearl = JIngredient.ingredient().item("minecraft:ender_pearl");
        JKeys keys = JKeys.keys().key("S", stone).key("O", obsidian).key("E", enderPearl);
        JResult result = JResult.stackedResult("chiseldecor:default/voidstone/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", 8);
        resourcePack.addRecipe(new Identifier(Ref.MOD_ID, "default/voidstone"), JRecipe.shaped(pattern, keys, result));
    }

    public static void addLaboratoryRecipe(RuntimeResourcePack resourcePack) {
        JPattern pattern = JPattern.pattern("QSQ", "S S", "QSQ");
        JIngredient stone = JIngredient.ingredient().item("minecraft:stone");
        JIngredient quartz = JIngredient.ingredient().item("minecraft:quartz");
        JKeys keys = JKeys.keys().key("S", stone).key("Q", quartz);
        JResult result = JResult.stackedResult("chiseldecor:default/laboratory/largewall", 32);
        resourcePack.addRecipe(new Identifier(Ref.MOD_ID, "default/laboratory"), JRecipe.shaped(pattern, keys, result));
    }

    public static void addTyrianRecipe(RuntimeResourcePack resourcePack) {
        JPattern pattern = JPattern.pattern("SSS", "SIS", "SSS");
        JIngredient stone = JIngredient.ingredient().item("minecraft:stone");
        JIngredient iron = JIngredient.ingredient().item("minecraft:iron_ingot");
        JKeys keys = JKeys.keys().key("S", stone).key("I", iron);
        JResult result = JResult.stackedResult("chiseldecor:default/tyrian/shining", 8);
        resourcePack.addRecipe(new Identifier(Ref.MOD_ID, "default/tyrian"), JRecipe.shaped(pattern, keys, result));
    }
}
