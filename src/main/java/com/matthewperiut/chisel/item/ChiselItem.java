package com.matthewperiut.chisel.item;

import com.matthewperiut.chisel.gui.ChiselScreenHandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ChiselItem extends Item implements NamedScreenHandlerFactory {

    public ChiselItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        super.use(world, user, hand);
        user.openHandledScreen(this);
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player)
    {
        return new ChiselScreenHandler(syncId, inv);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("gui.chisel.chisel");
    }

}