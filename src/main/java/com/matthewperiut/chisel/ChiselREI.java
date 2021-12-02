package com.matthewperiut.chisel;
// will fix later
/*
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.common;
import me.shedaniel.rei.api.RecipeCategory;
import me.shedaniel.rei.api.RecipeDisplay;
import me.shedaniel.rei.api.RecipeHelper;
import me.shedaniel.rei.api.common.plugins.REIPlugin;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.ServerTagManagerHolder;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ChiselDecorEntryPointREI implements REIPlugin {

    @Override
    public Identifier getPluginIdentifier() {
        return new Identifier(Ref.MOD_ID, "chisel_recipes");
    }

    @Override
    public void registerPluginCategories(RecipeHelper recipeHelper) {
        recipeHelper.registerCategory(new ChiselCategory());
        REIPluginV0.super.registerPluginCategories(recipeHelper);
    }

    @Override
    public void registerRecipeDisplays(RecipeHelper recipeHelper) {
        Iterator<String> chiselGroupNames = ChiselGroupLookup.getGroupNameIterator();
        while (chiselGroupNames.hasNext()) {
            recipeHelper.registerDisplay(new ChiselDisplay(chiselGroupNames.next()));
        }
        REIPluginV0.super.registerRecipeDisplays(recipeHelper);
    }

    @Override
    public void registerOthers(RecipeHelper recipeHelper) {
        recipeHelper.registerWorkingStations(new Identifier(Ref.MOD_ID, "chisel_recipes_category"),
                EntryStack.create(ChiselDecorEntryPoint.ITEM_CHISEL));
    }

    public static class ChiselDisplay implements RecipeDisplay {
        private List<Item> chiselGroupItems;

        public ChiselDisplay(String chiselGroup) {
            chiselGroupItems = ChiselGroupLookup.getBlocksInGroup(chiselGroup, ServerTagManagerHolder.getTagManager().getItems());
        }

        @Override
        public @NotNull List<List<EntryStack>> getInputEntries() {
            List<List<EntryStack>> entryStackLists = new ArrayList<>();
            List<EntryStack> entryStacks = new ArrayList<>();
            for (int i = 0; i < chiselGroupItems.size(); i++) {
                Item item = chiselGroupItems.get(i);
                entryStacks.add(EntryStack.create(new ItemStack(item, 1)));
            }
            entryStackLists.add(entryStacks);
            return entryStackLists;
        }

        @Override
        public @NotNull List<List<EntryStack>> getResultingEntries() {
            List<List<EntryStack>> entryStackLists = new ArrayList<>();
            for (int i = 0; i < chiselGroupItems.size(); i++) {
                List<EntryStack> entryStacks = new ArrayList<>();
                Item item = chiselGroupItems.get(i);
                entryStacks.add(EntryStack.create(new ItemStack(item, 1)));
                entryStackLists.add(entryStacks);
            }
            
            return entryStackLists;
        }

        @Override
        public @NotNull Identifier getRecipeCategory() {
            return new Identifier(Ref.MOD_ID, "chisel_recipes_category");
        }

    }

    public static class ChiselCategory implements RecipeCategory<ChiselDisplay> {
        public ChiselCategory() {
            //
        }

        @Override
        public @NotNull Identifier getIdentifier() {
            return new Identifier(Ref.MOD_ID, "chisel_recipes_category");
        }

        @Override
        public @NotNull String getCategoryName() {
            return I18n.translate("rei.chiseldecor.category");
        }

        @Override
        public int getDisplayWidth(ChiselDisplay display) {
            return 118;
        }

        @Override
        public int getDisplayHeight() {
            return 260;
        }

        @Override
        public int getFixedRecipesPerPage() {
            return 1;
        }

        @Override
        public int getMaximumRecipePerPage() {
            return 1;
        }

        @Override
        public @NotNull EntryStack getLogo() {
            return EntryStack.create(new ItemStack(ChiselDecorEntryPoint.ITEM_CHISEL, 1));
        }
        
        @Override
        public @NotNull List<Widget> setupDisplay(ChiselDisplay recipeDisplay, Rectangle bounds) {
            List<Widget> widgets = new ArrayList<>();
            Point startPoint = new Point(bounds.getMinX() + 5, bounds.getMinY() + 5);
            widgets.addAll(RecipeCategory.super.setupDisplay(recipeDisplay, bounds));
            widgets.add(Widgets.createTexturedWidget(new Identifier(Ref.MOD_ID, "textures/rei_recipes.png"), startPoint.x, startPoint.y, 108, 235));
            // draw slots
            List<Slot> slots = new ArrayList<>();
            slots.add(Widgets.createSlot(new Point(startPoint.x + 1 + 45, startPoint.y + 1 + 16)).backgroundEnabled(false).markInput().entries(recipeDisplay.getInputEntries().get(0)));
            int j = recipeDisplay.getResultingEntries().size();
            for (int x = 0; x < 6; x++) {
                for (int y = 0; y < 10; y++) {
                    if (6 * y + x >= j) {
                        break;
                    }
                    slots.add(Widgets.createSlot(new Point(startPoint.x + 1 + 18 * x, startPoint.y + 1 + 55 + 18 * y)).markOutput().entries(recipeDisplay.getResultingEntries().get(6 * y + x)));
                }
            }
            widgets.addAll(slots);
            return widgets;
        }

    }

}*/