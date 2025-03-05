package com.periut.chisel.mixins;

import com.periut.chisel.gui.ChiselScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public class HandledScreenMixin {
    @Inject(method = "drawSlot", at = @At("HEAD"), cancellable = true)
    void drawSlotCustom(DrawContext context, Slot slot, CallbackInfo ci) {
        if (((Object)this) instanceof ChiselScreen cs) {
            cs.customDrawSlot(context, slot);
            ci.cancel();
        }
    }
}
