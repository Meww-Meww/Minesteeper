package by.tealishteam.tealish.client.gui;

import by.tealishteam.tealish.Tealish;
import by.tealishteam.tealish.menus.TeapotMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class TeapotScreen extends AbstractContainerScreen<TeapotMenu> {
    private static final ResourceLocation TEAPOT_LOCATION = new ResourceLocation(Tealish.MODID,"textures/gui/container/teapot.png");

    public TeapotScreen(TeapotMenu teapotMenu, Inventory inventory, Component component) {
        super(teapotMenu, inventory, component);
        this.imageHeight = 166;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    public void render(GuiGraphics graphics, int p_282102_, int p_282423_, float p_282621_) {
        super.render(graphics, p_282102_, p_282423_, p_282621_);
        this.renderTooltip(graphics, p_282102_, p_282423_);
    }

    protected void renderBg(GuiGraphics p_281616_, float p_282737_, int p_281678_, int p_281465_) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        p_281616_.blit(TEAPOT_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }
}
