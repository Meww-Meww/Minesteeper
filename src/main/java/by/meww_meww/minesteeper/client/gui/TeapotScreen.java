package by.meww_meww.minesteeper.client.gui;

import by.meww_meww.minesteeper.Minesteeper;
import by.meww_meww.minesteeper.blocks.TeapotEntity;
import by.meww_meww.minesteeper.menus.TeapotMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

public class TeapotScreen extends AbstractContainerScreen<TeapotMenu> {
    private static final ResourceLocation TEAPOT_LOCATION = new ResourceLocation(Minesteeper.MODID,"textures/gui/container/teapot.png");
    private static final ResourceLocation LIT_PROGRESS_SPRITE = new ResourceLocation("container/furnace/lit_progress");
    private static final ResourceLocation BUBBLES_PROGRESS_SPRITE_1 = new ResourceLocation(Minesteeper.MODID,"bubbles-1");
    private static final ResourceLocation BUBBLES_PROGRESS_SPRITE_2 = new ResourceLocation(Minesteeper.MODID,"bubbles-2");
    private static final ResourceLocation KETTLE_PROGRESS_SPRITE = new ResourceLocation(Minesteeper.MODID,"kettle");

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

        if(this.menu.getFluid() != null) {

            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            IClientFluidTypeExtensions props = IClientFluidTypeExtensions.of(this.menu.getFluid().getFluid());
            ResourceLocation still = props.getStillTexture(this.menu.getFluid());

            if (still != null) {
                TextureAtlas atlas = minecraft.getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS);
                TextureAtlasSprite sprite = atlas.getSprite(still);

                int color = props.getTintColor(this.menu.getFluid());

                RenderSystem.setShaderColor(
                        FastColor.ARGB32.red(color) / 255.0F,
                        FastColor.ARGB32.green(color) / 255.0F,
                        FastColor.ARGB32.blue(color) / 255.0F,
                        FastColor.ARGB32.alpha(color) / 255.0F);
                RenderSystem.enableBlend();

                int width = 36;
                int height = 52;
                int offsetX = menuX + 101;
                int offsetY = menuY - 19;
                int stored = this.menu.getFluid().getAmount();
                float filledVolume = stored / TeapotEntity.CAPACITY;
                int renderableHeight = (int) (filledVolume * height);

                int atlasWidth = (int) (sprite.contents().width() / (sprite.getU1() - sprite.getU0()));
                int atlasHeight = (int) (sprite.contents().height() / (sprite.getV1() - sprite.getV0()));

                graphics.pose().pushPose();
                graphics.pose().translate(0, height - 16, 0);
                for (int i = 0; i < Math.ceil(renderableHeight / 16f); i++) {
                    for (int j = 0; j < Math.ceil(width / 16f); j++) {
                        int drawingWidth = Math.min(16, width - j * 16);
                        int drawingHeight = Math.min(16, renderableHeight - i * 16);
                        int notDrawingHeight = 16 - drawingHeight;
                        int notDrawingWidth = 16 - drawingWidth;

                        graphics.blit(InventoryMenu.BLOCK_ATLAS,
                                offsetX + j * 16,
                                offsetY + i * 16,
                                0,
                                sprite.getU0() * atlasWidth + notDrawingWidth,
                                sprite.getV0() * atlasHeight + notDrawingHeight,
                                drawingWidth,
                                drawingHeight,
                                atlasWidth,
                                atlasHeight);
                    }
                }
                RenderSystem.setShaderColor(1, 1, 1, 1);

                graphics.pose().popPose();
            }
        }
    }
}
