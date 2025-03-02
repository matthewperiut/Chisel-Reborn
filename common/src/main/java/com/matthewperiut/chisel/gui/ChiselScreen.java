package com.matthewperiut.chisel.gui;

import com.matthewperiut.chisel.Chisel;
import com.matthewperiut.chisel.mixins.HandledScreenAccessor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import com.periut.cryonicconfig.CryonicConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.texture.Sprite;
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
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        if (compactTexture) {
            RenderSystem.setShaderTexture(0, OLD_TEXTURE);
            context.drawTexture(OLD_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        } else {
            RenderSystem.setShaderTexture(0, TEXTURE);
            context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
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

    protected void drawSlot(DrawContext context, Slot slot) {
        int i = slot.x;
        int j = slot.y;
        ItemStack itemStack = slot.getStack();
        boolean bl = false;
        boolean bl2 = slot == ((HandledScreenAccessor)this).getTouchDragSlotStart() && !((HandledScreenAccessor)this).getTouchDragStack().isEmpty() && !((HandledScreenAccessor)this).getTouchIsRightClickDrag();
        ItemStack itemStack2 = this.handler.getCursorStack();
        String string = null;
        int k;

        // Check if this is a big slot
        boolean isBigSlot = slot instanceof BigSlot && ((BigSlot)slot).isBigSlot();

        if (slot == ((HandledScreenAccessor)this).getTouchDragSlotStart() && !((HandledScreenAccessor)this).getTouchDragStack().isEmpty() && ((HandledScreenAccessor)this).getTouchIsRightClickDrag() && !itemStack.isEmpty()) {
            itemStack = itemStack.copyWithCount(itemStack.getCount() / 2);
        } else if (this.cursorDragging && this.cursorDragSlots.contains(slot) && !itemStack2.isEmpty()) {
            if (this.cursorDragSlots.size() == 1) {
                return;
            }

            if (ScreenHandler.canInsertItemIntoSlot(slot, itemStack2, true) && this.handler.canInsertIntoSlot(slot)) {
                bl = true;
                k = Math.min(itemStack2.getMaxCount(), slot.getMaxItemCount(itemStack2));
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

        context.getMatrices().push();
        context.getMatrices().translate(0.0F, 0.0F, 100.0F);

        if (itemStack.isEmpty() && slot.isEnabled()) {
            Pair<Identifier, Identifier> pair = slot.getBackgroundSprite();
            if (pair != null) {
                Sprite sprite = (Sprite)this.client.getSpriteAtlas((Identifier)pair.getFirst()).apply((Identifier)pair.getSecond());
                context.drawSprite(i, j, 0, 16, 16, sprite);
                bl2 = true;
            }
        }

        if (!bl2) {
            if (bl) {
                context.fill(i, j, i + 16, j + 16, -2130706433);
            }

            k = slot.x + slot.y * this.backgroundWidth;

            // Handle big slots differently
            if (isBigSlot && !itemStack.isEmpty()) {
                // Save state for big item rendering
                context.getMatrices().push();

                // Center at slot position
                float centerX = i + 8;
                float centerY = j + 8;

                // Move to center, scale up, then move back
                context.getMatrices().translate(centerX, centerY, 0);
                context.getMatrices().scale(2.0f, 2.0f, 1.0f);  // 2x size
                context.getMatrices().translate(-8, -8, 0);

                // Draw the item at 2x size
                if (slot.disablesDynamicDisplay()) {
                    context.drawItemWithoutEntity(itemStack, 0, 0, k);
                } else {
                    context.drawItem(itemStack, 0, 0, k);
                }

                // Only draw overlay if needed (using your original condition)
                if (slot.id < 1 || slot.id > 61) {
                    context.drawItemInSlot(this.textRenderer, itemStack, 0, 0, string);
                }

                // Restore state
                context.getMatrices().pop();
            } else {
                // Normal item rendering (unchanged)
                if (slot.disablesDynamicDisplay()) {
                    context.drawItemWithoutEntity(itemStack, i, j, k);
                } else {
                    context.drawItem(itemStack, i, j, k);
                }

                if (slot.id < 1 || slot.id > 61) {
                    context.drawItemInSlot(this.textRenderer, itemStack, i, j, string);
                }
            }
        }

        context.getMatrices().pop();
    }
}