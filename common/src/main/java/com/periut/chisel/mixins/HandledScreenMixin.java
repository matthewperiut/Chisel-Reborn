package com.periut.chisel.mixins;

import com.periut.chisel.gui.BigSlot;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Restores the "big slot" behaviour for chisel's 2x input slot under MC 26.1's extract-rendering GUI:
 *  - expands the hover/click hit-region to the full 32x32 visual area (getHoveredSlot is used by both
 *    render-time hover AND mouseClicked/Dragged/Released, so this covers clicking anywhere on it), and
 *  - draws the slot highlight sprites at 2x (48x48) around the big slot.
 * The big slot's SlotMixin makes isHighlightable() return false, so vanilla skips its 24x24 highlight
 * for it; this custom 48x48 draw is therefore purely additive.
 */
@Mixin(AbstractContainerScreen.class)
public abstract class HandledScreenMixin {

    @Shadow
    protected Slot hoveredSlot;

    @Shadow
    protected abstract boolean isHovering(int x, int y, int w, int h, double pointX, double pointY);

    @Unique
    private static final Identifier chisel$HIGHLIGHT_BACK = Identifier.withDefaultNamespace("container/slot_highlight_back");
    @Unique
    private static final Identifier chisel$HIGHLIGHT_FRONT = Identifier.withDefaultNamespace("container/slot_highlight_front");

    // Vanilla getHoveredSlot only matches the regular 16x16 region; widen it to 32x32 for the big slot.
    @Inject(method = "getHoveredSlot", at = @At("RETURN"), cancellable = true)
    private void chisel$expandBigSlotHover(double mouseX, double mouseY, CallbackInfoReturnable<Slot> cir) {
        if (cir.getReturnValue() != null) {
            return;
        }
        AbstractContainerScreen<?> self = (AbstractContainerScreen<?>) (Object) this;
        for (Slot slot : self.getMenu().slots) {
            if (slot.isActive() && slot instanceof BigSlot bigSlot && bigSlot.isBigSlot()
                    && this.isHovering(slot.x - 8, slot.y - 8, 32, 32, mouseX, mouseY)) {
                cir.setReturnValue(slot);
                return;
            }
        }
    }

    @Inject(method = "extractSlotHighlightBack", at = @At("HEAD"))
    private void chisel$bigHighlightBack(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        if (chisel$isBigSlotHovered()) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, chisel$HIGHLIGHT_BACK, this.hoveredSlot.x - 16, this.hoveredSlot.y - 16, 48, 48);
        }
    }

    @Inject(method = "extractSlotHighlightFront", at = @At("HEAD"))
    private void chisel$bigHighlightFront(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        if (chisel$isBigSlotHovered()) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, chisel$HIGHLIGHT_FRONT, this.hoveredSlot.x - 16, this.hoveredSlot.y - 16, 48, 48);
        }
    }

    @Unique
    private boolean chisel$isBigSlotHovered() {
        return this.hoveredSlot instanceof BigSlot bigSlot && bigSlot.isBigSlot();
    }
}
