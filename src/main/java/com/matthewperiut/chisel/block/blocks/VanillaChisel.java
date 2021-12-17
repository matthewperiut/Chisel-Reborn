package com.matthewperiut.chisel.block.blocks;

import com.matthewperiut.chisel.Chisel;
import com.matthewperiut.chisel.block.ChiselGroupLookup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class VanillaChisel
{
    public static String vanilla[][] = new String[][]{
            {
                "sandstone", "chiseled_sandstone", "cut_sandstone", "smooth_sandstone"
            },
            {
                "red_sandstone", "chiseled_red_sandstone", "cut_red_sandstone", "smooth_red_sandstone"
            },
            {
                "cobblestone"
            },
            {
                "stone", "smooth_stone", "stone_bricks", "chiseled_stone_bricks", "cracked_stone_bricks", "mossy_stone_bricks"
            },
            {
                "nether_bricks", "chiseled_nether_bricks", "cracked_nether_bricks"
            },
            {
                "blackstone", "polished_blackstone", "chiseled_polished_blackstone", "polished_blackstone_bricks", "cracked_polished_blackstone_bricks"
            },
            {
                "deepslate", "polished_deepslate", "deepslate_bricks", "cracked_deepslate_bricks", "deepslate_tiles", "cracked_deepslate_tiles", "chiseled_deepslate"
            },
            {
                "quartz_block", "chiseled_quartz_block", "quartz_bricks", "quartz_pillar", "smooth_quartz"
            },
            {
                "end_stone", "end_stone_bricks"
            },
            {
                "purpur_block", "purpur_pillar"
            },
            {
                "granite", "polished_granite"
            }
    };

    public static void Register()
    {
        for(int i = 0; i < vanilla.length; i++)
        {
            for(int j = 0; j < vanilla[i].length; j++)
            {
                ChiselGroupLookup.addItemToGroup(vanilla[i][0], new Identifier("minecraft",vanilla[i][j]));
            }
        }
    }
}
