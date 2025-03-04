package com.periut.chisel.mixins;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HandledScreen.class)
public interface HandledScreenAccessor {
    @Accessor("touchDragSlotStart")
    Slot getTouchDragSlotStart();
    @Accessor("touchDragStack")
    ItemStack getTouchDragStack();
    @Accessor("touchIsRightClickDrag")
    boolean getTouchIsRightClickDrag();
    @Accessor("heldButtonType")
    int getHeldButtonType();
    @Invoker("calculateOffset")
    void invokeCalculateOffset();
}
