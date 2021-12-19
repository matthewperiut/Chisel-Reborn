package com.matthewperiut.chisel.legacy.data;

import java.util.List;

import com.matthewperiut.chisel.Chisel;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.recipe.JIngredient;
import net.devtech.arrp.json.recipe.JKeys;
import net.devtech.arrp.json.recipe.JPattern;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.recipe.JResult;
import net.minecraft.util.Identifier;

public class Recipes {
    private Recipes() {}

    public static void addChiselRecipe(RuntimeResourcePack resourcePack) {
        JPattern pattern = JPattern.pattern("I ", " S");
        JPattern patternAlt = JPattern.pattern(" I", "S ");
        JIngredient stick = JIngredient.ingredient().item("minecraft:stick");
        JIngredient iron = JIngredient.ingredient().item("minecraft:iron_ingot");
        JKeys keys = JKeys.keys().key("S", stick).key("I", iron);
        JResult result = JResult.stackedResult("chisel:chisel", 1);
        resourcePack.addRecipe(new Identifier(Chisel.MOD_ID, "chisel"), JRecipe.shaped(pattern, keys, result));
        resourcePack.addRecipe(new Identifier(Chisel.MOD_ID, "chisel_alt"), JRecipe.shaped(patternAlt, keys, result));
    }

    public static void addRecipe(List<String> pattern, List<RecipeKey> keys, String output, int count, RuntimeResourcePack resourcePack) {
        JPattern jPattern;
        if (pattern.size() == 1) {
            jPattern = JPattern.pattern(pattern.get(0));
        } else if (pattern.size() == 2) {
            jPattern = JPattern.pattern(pattern.get(0), pattern.get(1));
        } else if (pattern.size() == 3){
            jPattern = JPattern.pattern(pattern.get(0), pattern.get(1), pattern.get(2));
        } else {
            return;
        }
        JKeys jKeys = JKeys.keys();
        for (int i = 0; i < keys.size(); i++) {
            JIngredient key = JIngredient.ingredient().item(keys.get(i).value);
            jKeys.key(keys.get(i).key, key);
        }
        JResult jResult = JResult.stackedResult(output, count);
        resourcePack.addRecipe(new Identifier(Chisel.MOD_ID, Identifier.splitOn(output, ':').getPath()), JRecipe.shaped(jPattern, jKeys, jResult));
    }

    public static class RecipeKey {
        public final String key;
        public final String value;

        public RecipeKey(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}