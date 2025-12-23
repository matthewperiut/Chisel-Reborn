package com.periut.chisel.gui;

import com.periut.chisel.Chisel;
import com.periut.chisel.mixins.HandledScreenAccessor;
import com.periut.cryonicconfig.CryonicConfig;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class ChiselScreen extends HandledScreen<ScreenHandler> {
    boolean compactTexture;
    public static final Identifier OLD_TEXTURE = Identifier.of(Chisel.MOD_ID, "textures/chiselfabricgui.png");
    public static final Identifier TEXTURE = Identifier.of(Chisel.MOD_ID, "textures/chisel2gui.png");
    public static final int OLD_TEXTURE_WIDTH = 194;
    public static final int OLD_TEXTURE_HEIGHT = 209;
    public static final int TEXTURE_WIDTH = 248;
    public static final int TEXTURE_HEIGHT = 202;

    public ChiselScreen(ScreenHandler handler, PlayerInventory inventory, Text title)
    {
        super(handler, inventory, title);
        compactTexture = CryonicConfig.getConfig("chisel").getBoolean("compact_chisel_gui", false);
        if (compactTexture) {
            this.titleY = 5;
            this.backgroundWidth = OLD_TEXTURE_WIDTH;
            this.backgroundHeight = OLD_TEXTURE_HEIGHT;
        } else {
            this.titleX = 18;
            this.titleY = 62;
            this.backgroundWidth = TEXTURE_WIDTH;
            this.backgroundHeight = TEXTURE_HEIGHT;
        }
    }


    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        if (compactTexture) {
            context.drawTexture(RenderPipelines.GUI_TEXTURED, OLD_TEXTURE, x, y, 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, 256, 256);
        } else {
            context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, 256, 256);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        if (compactTexture) {
            titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        }
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY)
    {
      context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, 4210752, false);
    }

    @Override
    protected void drawSlot(DrawContext context, Slot slot, int mouseX, int mouseY) {
        int i = slot.x;
        int j = slot.y;
        ItemStack itemStack = slot.getStack();
        boolean bl = false;
        boolean bl2 = slot == ((HandledScreenAccessor)this).getTouchDragSlotStart() && !((HandledScreenAccessor)this).getTouchDragStack().isEmpty() && !((HandledScreenAccessor)this).getTouchIsRightClickDrag();
        ItemStack itemStack2 = this.handler.getCursorStack();
        String string = null;

        // Check if this is a big slot
        boolean isBigSlot = slot instanceof BigSlot && ((BigSlot)slot).isBigSlot();
        int slotSize = isBigSlot ? 32 : 16; // 2x size for big slots

        if (slot == ((HandledScreenAccessor)this).getTouchDragSlotStart() && !((HandledScreenAccessor)this).getTouchDragStack().isEmpty() && ((HandledScreenAccessor)this).getTouchIsRightClickDrag() && !itemStack.isEmpty()) {
            itemStack = itemStack.copyWithCount(itemStack.getCount() / 2);
        } else if (this.cursorDragging && this.cursorDragSlots.contains(slot) && !itemStack2.isEmpty()) {
            if (this.cursorDragSlots.size() == 1) {
                return;
            }

            if (ScreenHandler.canInsertItemIntoSlot(slot, itemStack2, true) && this.handler.canInsertIntoSlot(slot)) {
                bl = true;
                int k = Math.min(itemStack2.getMaxCount(), slot.getMaxItemCount(itemStack2));
                int l = slot.getStack().isEmpty() ? 0 : slot.getStack().getCount();
                int m = ScreenHandler.calculateStackSize(this.cursorDragSlots, ((HandledScreenAccessor)this).getHeldButtonType(), itemStack2) + l;
                if (m > k) {
                    m = k;
                    String var10000 = Formatting.YELLOW.toString();
                    string = var10000 + k;
                }

                itemStack = itemStack2.copyWithCount(m);
            } else {
                this.cursorDragSlots.remove(slot);
                ((HandledScreenAccessor) this).invokeCalculateOffset();
            }
        }

        if (itemStack.isEmpty() && slot.isEnabled()) {
            Identifier identifier = slot.getBackgroundSprite();
            if (identifier != null) {
                if (isBigSlot) {
                    // Scale the background texture for big slots
                    context.getMatrices().pushMatrix();
                    context.getMatrices().translate(i, j);
                    context.getMatrices().scale(2.0f, 2.0f);
                    context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, identifier, 0, 0, 16, 16);
                    context.getMatrices().popMatrix();
                } else {
                    context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, identifier, i, j, 16, 16);
                }
                bl2 = true;
            }
        }

        if (!bl2) {
            if (bl) {
                context.fill(i, j, i + slotSize, j + slotSize, -2130706433);
            }

            int k = slot.x + slot.y * this.backgroundWidth;

            if (isBigSlot) {
                // Render big slot item with 2x scale
                context.getMatrices().pushMatrix();
                context.getMatrices().translate(i-8, j-8);
                context.getMatrices().scale(2.0f, 2.0f);

                if (slot.disablesDynamicDisplay()) {
                    context.drawItemWithoutEntity(itemStack, 0, 0, k);
                } else {
                    context.drawItem(itemStack, 0, 0, k);
                }

                // Draw overlay at scaled size
                if (slot.id < 1 || slot.id > 60) {
                    context.drawStackOverlay(this.textRenderer, itemStack, 0, 0, string);
                }

                context.getMatrices().popMatrix();
            } else {
                // Normal rendering for regular slots
                if (slot.disablesDynamicDisplay()) {
                    context.drawItemWithoutEntity(itemStack, i, j, k);
                } else {
                    context.drawItem(itemStack, i, j, k);
                }

                if (slot.id < 1 || slot.id > 60) {
                    context.drawStackOverlay(this.textRenderer, itemStack, i, j, string);
                }
            }
        }
    }
}