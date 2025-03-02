package com.matthewperiut.chisel.fabric.mixin;

import com.matthewperiut.chisel.gui.BigSlot;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin extends Screen {

    @Shadow
    protected Slot focusedSlot;

    protected HandledScreenMixin(Text title) {
        super(title);
    }

    @Shadow protected abstract boolean isPointWithinBounds(int x, int y, int width, int height, double pointX, double pointY);

    @Shadow protected int x;

    @Unique
    public final void drawSlotHighlightBackBig(DrawContext context) {
        if (this.focusedSlot != null && ((BigSlot)this.focusedSlot).isBigSlot()) {
            context.fillGradient(RenderLayer.getGuiOverlay(), focusedSlot.x-16, focusedSlot.y-16, focusedSlot.x + 32, focusedSlot.y + 32, -2130706433, -2130706433, 0);
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;canBeHighlighted()Z"))
    void back(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        drawSlotHighlightBackBig(context);
    }

    @Inject(method = "isPointOverSlot", at = @At("HEAD"), cancellable = true)
    private void isPointOverSlotBig(Slot slot, double pointX, double pointY, CallbackInfoReturnable<Boolean> cir) {
        if (((BigSlot)slot).isBigSlot()) {
            cir.setReturnValue(this.isPointWithinBounds(slot.x-16, slot.y-16, 48, 48, pointX, pointY));
        }
    }
}