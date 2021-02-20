package com.knowyourknot.chiseldecor.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class ChiselBlockItem extends BlockItem {
    private final String packDir;
    private final String typeName;
    private final String variantName;
    private final int tooltips;

    public ChiselBlockItem(Block block, Settings settings, String packDir, String typeName, String variantName) {
        this(block, settings, packDir, typeName, variantName, 0);
    }

    public ChiselBlockItem(Block block, Settings settings, String packDir, String typeName, String variantName, int tooltips) {
        super(block, settings);
        this.packDir = packDir;
        this.typeName = typeName;
        this.variantName = variantName;
        this.tooltips = tooltips;
    }

    @Override
    public String getTranslationKey() {
        return "block.chiseldecor." + packDir.replace('/', '.') + "." + typeName.replace('/', '.');
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(new TranslatableText(getTranslationKey() + "." + variantName).setStyle(Style.EMPTY.withColor(Formatting.GRAY)));
        for (int i = 0; i < tooltips; i++) {
            tooltip.add(new TranslatableText(getTranslationKey() + "." + variantName + ".desc." + i).setStyle(Style.EMPTY.withColor(Formatting.GRAY)));
        }
    }

    
}
