package com.knowyourknot.chiseldecor.item;

import com.knowyourknot.chiseldecor.Ref;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ItemDebug extends Item {

    public ItemDebug(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        System.out.println(world.getTagManager().getBlocks().getTag(new Identifier(Ref.MOD_ID, "test/diamond")).values());
        return super.use(world, user, hand);
    }
}
