package by.tealishteam.tealish.client.gui;

import by.tealishteam.tealish.Tealish;
import by.tealishteam.tealish.menus.TeapotMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class TeapotScreen extends AbstractContainerScreen<TeapotMenu> {
    private static final ResourceLocation TEAPOT_LOCATION = new ResourceLocation(Tealish.MODID,"textures/gui/container/teapot.png");
    private static final ResourceLocation LIT_PROGRESS_SPRITE = new ResourceLocation("container/furnace/lit_progress");
    private static final ResourceLocation BUBBLES_PROGRESS_SPRITE_1 = new ResourceLocation(Tealish.MODID,"bubbles-1");
    private static final ResourceLocation BUBBLES_PROGRESS_SPRITE_2 = new ResourceLocation(Tealish.MODID,"bubbles-2");
    private static final ResourceLocation KETTLE_PROGRESS_SPRITE = new ResourceLocation(Tealish.MODID,"kettle");

    public TeapotScreen(TeapotMenu teapotMenu, Inventory inventory, Component component) {
        super(teapotMenu, inventory, component);
        this.imageHeight = 166;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    public void render(GuiGraphics graphics, int p_282102_, int p_282423_, float p_282621_) {
        super.render(graphics, p_282102_, p_282423_, p_282621_);
        this.renderTooltip(graphics, p_282102_, p_282423_);
    }

    protected void renderBg(GuiGraphics graphics, float p_282737_, int p_281678_, int p_281465_) {
        int menuX = (this.width - this.imageWidth) / 2;
        int menuY = (this.height - this.imageHeight) / 2;
        graphics.blit(TEAPOT_LOCATION, menuX, menuY, 0, 0, this.imageWidth, this.imageHeight);
        if (this.menu.isLit()) {
            int height = 14;
            int width = 14;
            int unrenderedHeight = Mth.ceil(this.menu.getLitProgress() * (float)(height - 1)) + 1;
            int renderedHeight = height - unrenderedHeight;
            int offsetX = 40;
            int offsetY = 50 - unrenderedHeight;
            graphics.blitSprite(LIT_PROGRESS_SPRITE, width, height, 0, renderedHeight, menuX + offsetX, menuY + offsetY, width, unrenderedHeight);
        }

        {
            int height = 12;
            int width = 16;
            int unrenderedWidth = Math.min(Mth.ceil((this.menu.getBurnProgress() * 3) * (float)width), width);
            int offsetX = 59;
            int offsetY = 37;
            graphics.blitSprite(BUBBLES_PROGRESS_SPRITE_1, width, height, 0, 0, menuX + offsetX, menuY + offsetY, unrenderedWidth, height);
        }

        if(this.menu.getBurnProgress() > 1F/3F){
            float sectionProgress = (this.menu.getBurnProgress() - 1F/3F) * 3;
            int height = 12;
            int width = 12;
            int unrenderedWidth = Math.min(Mth.ceil(sectionProgress * (float)width), width);
            int offsetX = 87;
            int offsetY = 37;
            graphics.blitSprite(BUBBLES_PROGRESS_SPRITE_2, width, height, 0, 0, menuX + offsetX, menuY + offsetY, unrenderedWidth, height);
        }

        if(this.menu.getBurnProgress() > 2F/3F){
            float sectionProgress = (this.menu.getBurnProgress() - 2F/3F) * 3;
            int height = 11;
            int width = 12;
            int unrenderedHeight = Mth.ceil(sectionProgress * (float)height);
            int offsetX = 75;
            int offsetY = 47 - unrenderedHeight;
            graphics.blitSprite(KETTLE_PROGRESS_SPRITE, width, height, 0, height - unrenderedHeight, menuX + offsetX, menuY + offsetY, width, unrenderedHeight);
        }
    }
}
