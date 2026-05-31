package com.periut.chisel.gui;

import com.periut.chisel.Chisel;
import com.periut.cryonicconfig.CryonicConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ChiselScreen extends AbstractContainerScreen<AbstractContainerMenu> {
    private final boolean compactTexture;
    public static final Identifier OLD_TEXTURE = Identifier.fromNamespaceAndPath(Chisel.MOD_ID, "textures/chiselfabricgui.png");
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(Chisel.MOD_ID, "textures/chisel2gui.png");
    public static final int OLD_TEXTURE_WIDTH = 194;
    public static final int OLD_TEXTURE_HEIGHT = 209;
    public static final int TEXTURE_WIDTH = 248;
    public static final int TEXTURE_HEIGHT = 202;

    public ChiselScreen(AbstractContainerMenu handler, Inventory inventory, Component title) {
        // MC 26.1: imageWidth/imageHeight are final and must be passed to the super constructor.
        super(handler, inventory, title,
                isCompact() ? OLD_TEXTURE_WIDTH : TEXTURE_WIDTH,
                isCompact() ? OLD_TEXTURE_HEIGHT : TEXTURE_HEIGHT);
        this.compactTexture = isCompact();
        if (compactTexture) {
            this.titleLabelY = 5;
        } else {
            this.titleLabelX = 18;
            this.titleLabelY = 62;
        }
    }

    private static boolean isCompact() {
        return CryonicConfig.getConfig("chisel").getBoolean("compact_chisel_gui", false);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick) {
        // MC 26.1 uses a retained-mode "extract" rendering pipeline (GuiGraphicsExtractor) instead of
        // the old GuiGraphics renderBg/render. Draw the chisel background, then let vanilla extract the
        // slots/items/labels/tooltips (item/slot strata render above this background).
        Identifier tex = compactTexture ? OLD_TEXTURE : TEXTURE;
        extractor.blit(RenderPipelines.GUI_TEXTURED, tex, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        super.extractRenderState(extractor, mouseX, mouseY, partialTick);
    }

    @Override
    protected void init() {
        super.init();
        if (compactTexture) {
            titleLabelX = (imageWidth - font.width(title)) / 2;
        }
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor extractor, int mouseX, int mouseY) {
        extractor.text(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
    }

    @Override
    protected void extractSlot(GuiGraphicsExtractor extractor, Slot slot, int mouseX, int mouseY) {
        boolean isBigSlot = slot instanceof BigSlot && ((BigSlot) slot).isBigSlot();
        if (!isBigSlot) {
            super.extractSlot(extractor, slot, mouseX, mouseY);
            return;
        }
        // Custom: render the big (input) slot's contents at 2x scale.
        ItemStack itemStack = slot.getItem();
        if (itemStack.isEmpty()) {
            if (slot.isActive()) {
                Identifier icon = slot.getNoItemIcon();
                if (icon != null) {
                    extractor.pose().pushMatrix();
                    extractor.pose().translate(slot.x, slot.y);
                    extractor.pose().scale(2.0f, 2.0f);
                    extractor.blitSprite(RenderPipelines.GUI_TEXTURED, icon, 0, 0, 16, 16);
                    extractor.pose().popMatrix();
                }
            }
            return;
        }
        extractor.pose().pushMatrix();
        extractor.pose().translate(slot.x - 8, slot.y - 8);
        extractor.pose().scale(2.0f, 2.0f);
        if (slot.isFake()) {
            extractor.fakeItem(itemStack, 0, 0);
        } else {
            extractor.item(itemStack, 0, 0);
        }
        if (slot.index < 1 || slot.index > 60) {
            extractor.itemDecorations(this.font, itemStack, 0, 0);
        }
        extractor.pose().popMatrix();
    }
}
