package com.matthewperiut.chisel.mixins;

import com.matthewperiut.chisel.gui.BigSlot;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public class SlotMixin implements BigSlot {
    @Unique
    boolean bigSlot = false;
    public boolean isBigSlot() {
        return bigSlot;
    }
    public Slot setBigSlot(boolean bigSlot) {
        this.bigSlot = bigSlot;
        return (Slot) (Object) this;
    }
    @Inject(method = "canBeHighlighted", at = @At("HEAD"), cancellable = true)
    void canBeHighlightedBig(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(!bigSlot);
    }
}
