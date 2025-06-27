package com.periut.chisel.mixins;

import com.periut.chisel.gui.BigSlot;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
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

    @Shadow @Final private static Identifier SLOT_HIGHLIGHT_BACK_TEXTURE;

    @Shadow @Final private static Identifier SLOT_HIGHLIGHT_FRONT_TEXTURE;

    protected HandledScreenMixin(Text title) {
        super(title);
    }

    @Shadow protected abstract boolean isPointWithinBounds(int x, int y, int width, int height, double pointX, double pointY);

    @Shadow protected int x;

    @Unique
    public final void drawSlotHighlightBackBig(DrawContext context) {
        if (this.focusedSlot != null && ((BigSlot)this.focusedSlot).isBigSlot()) {
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, SLOT_HIGHLIGHT_BACK_TEXTURE, this.focusedSlot.x-20, this.focusedSlot.y-20, 56, 56);
        }

    }

    @Unique
    public final void drawSlotHighlightFrontBig(DrawContext context) {
        if (this.focusedSlot != null && ((BigSlot)this.focusedSlot).isBigSlot()) {
            context.drawGuiTexture(RenderPipelines.GUI_NAUSEA_OVERLAY, SLOT_HIGHLIGHT_FRONT_TEXTURE, this.focusedSlot.x - 20, this.focusedSlot.y - 20, 56, 56);
        }
    }

    @Inject(method = "renderMain", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;drawSlotHighlightBack(Lnet/minecraft/client/gui/DrawContext;)V"))
    void back(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        drawSlotHighlightBackBig(context);
    }

    @Inject(method = "renderMain", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;drawSlotHighlightFront(Lnet/minecraft/client/gui/DrawContext;)V"))
    void front(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        drawSlotHighlightFrontBig(context);
    }

    @Inject(method = "isPointOverSlot", at = @At("HEAD"), cancellable = true)
    private void isPointOverSlotBig(Slot slot, double pointX, double pointY, CallbackInfoReturnable<Boolean> cir) {
        if (((BigSlot)slot).isBigSlot()) {
            cir.setReturnValue(this.isPointWithinBounds(slot.x-16, slot.y-16, 48, 48, pointX, pointY));
        }
    }
}