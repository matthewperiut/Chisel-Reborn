package com.knowyourknot.chiseldecor.gui;

import com.knowyourknot.chiseldecor.Ref;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ChiselScreen extends HandledScreen<ChiselScreenHandler> {
    public static final Identifier TEXTURE = new Identifier(Ref.MOD_ID, "textures/chisel2gui.png");
    public static final int TEXTURE_WIDTH = 248;
    public static final int TEXTURE_HEIGHT = 202;

    public ChiselScreen(ChiselScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.titleX = 7;
        this.titleY = 62;
        this.backgroundWidth =  TEXTURE_WIDTH;
        this.backgroundHeight = TEXTURE_HEIGHT;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        client.getTextureManager().bindTexture(TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        float tempTitleX = 7f + 25f - (0.5f * this.textRenderer.getWidth(this.title));
        this.textRenderer.draw(matrices, this.title, tempTitleX, this.titleY, 4210752);
    }
}
