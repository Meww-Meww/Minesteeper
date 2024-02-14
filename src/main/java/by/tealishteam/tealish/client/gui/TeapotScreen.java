package by.tealishteam.tealish.client.gui;

import by.tealishteam.tealish.Tealish;
import by.tealishteam.tealish.blocks.TeapotEntity;
import by.tealishteam.tealish.menus.TeapotMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

public class TeapotScreen extends AbstractContainerScreen<TeapotMenu> {
    private static final ResourceLocation TEAPOT_LOCATION = new ResourceLocation(Tealish.MODID,"textures/gui/container/teapot.png");
    private static final ResourceLocation LIT_PROGRESS_SPRITE = new ResourceLocation("container/furnace/lit_progress");
    private static final ResourceLocation BUBBLES_PROGRESS_SPRITE_1 = new ResourceLocation(Tealish.MODID,"bubbles-1");
    private static final ResourceLocation BUBBLES_PROGRESS_SPRITE_2 = new ResourceLocation(Tealish.MODID,"bubbles-2");
    private static final ResourceLocation KETTLE_PROGRESS_SPRITE = new ResourceLocation(Tealish.MODID,"kettle");

    private TeapotEntity teapot;

    public TeapotScreen(TeapotMenu teapotMenu, Inventory inventory, Component component) {
        super(teapotMenu, inventory, component);
        this.imageHeight = 166;
        this.inventoryLabelY = this.imageHeight - 94;
        this.teapot = teapotMenu.teapot;

        System.out.println(this.teapot.getFluidTank().getFluidAmount());
    }

    public void render(GuiGraphics graphics, int p_282102_, int p_282423_, float p_282621_) {
        super.render(graphics, p_282102_, p_282423_, p_282621_);
        this.renderTooltip(graphics, p_282102_, p_282423_);
    }

    protected void renderBg(GuiGraphics graphics, float p_282737_, int p_281678_, int p_281465_) {
        int menuX = (this.width - this.imageWidth) / 2;
        int menuY = (this.height - this.imageHeight) / 2;
        graphics.blit(TEAPOT_LOCATION, menuX, menuY, 0, 0, this.imageWidth, this.imageHeight);
        if (this.teapot.isLit()) {
            int height = 14;
            int width = 14;
            int unrenderedHeight = Mth.ceil(this.teapot.getLitProgress() * (float)(height - 1)) + 1;
            int renderedHeight = height - unrenderedHeight;
            int offsetX = 40;
            int offsetY = 50 - unrenderedHeight;
            graphics.blitSprite(LIT_PROGRESS_SPRITE, width, height, 0, renderedHeight, menuX + offsetX, menuY + offsetY, width, unrenderedHeight);
        }

        {
            int height = 12;
            int width = 16;
            int unrenderedWidth = Math.min(Mth.ceil((this.teapot.getBurnProgress() * 3) * (float)width), width);
            int offsetX = 59;
            int offsetY = 37;
            graphics.blitSprite(BUBBLES_PROGRESS_SPRITE_1, width, height, 0, 0, menuX + offsetX, menuY + offsetY, unrenderedWidth, height);
        }

        if(this.teapot.getBurnProgress() > 1F/3F){
            float sectionProgress = (this.teapot.getBurnProgress() - 1F/3F) * 3;
            int height = 12;
            int width = 12;
            int unrenderedWidth = Math.min(Mth.ceil(sectionProgress * (float)width), width);
            int offsetX = 87;
            int offsetY = 37;
            graphics.blitSprite(BUBBLES_PROGRESS_SPRITE_2, width, height, 0, 0, menuX + offsetX, menuY + offsetY, unrenderedWidth, height);
        }

        if(this.teapot.getBurnProgress() > 2F/3F){
            float sectionProgress = (this.teapot.getBurnProgress() - 2F/3F) * 3;
            int height = 11;
            int width = 12;
            int unrenderedHeight = Mth.ceil(sectionProgress * (float)height);
            int offsetX = 75;
            int offsetY = 47 - unrenderedHeight;
            graphics.blitSprite(KETTLE_PROGRESS_SPRITE, width, height, 0, height - unrenderedHeight, menuX + offsetX, menuY + offsetY, width, unrenderedHeight);
        }

        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        IClientFluidTypeExtensions props = IClientFluidTypeExtensions.of(teapot.getFluidTank().getFluid().getFluid());
        ResourceLocation still = props.getStillTexture(teapot.getFluidTank().getFluid());

        if(still != null) {
            AbstractTexture texture = minecraft.getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS);
            if (texture instanceof TextureAtlas atlas) {
                TextureAtlasSprite sprite = atlas.getSprite(still);

                int color = props.getTintColor();
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
                int stored = teapot.getFluidTank().getFluidAmount();
                float capacity = teapot.getFluidTank().getCapacity();
                float filledVolume = stored / capacity;
                int renderableHeight = (int) (filledVolume * height);

                int atlasWidth = (int) (sprite.contents().width() / (sprite.getU1() - sprite.getU0()));
                int atlasHeight = (int) (sprite.contents().height() / (sprite.getV1() - sprite.getV0()));

                graphics.pose().pushPose();
                graphics.pose().translate(0, height - 16, 0);
                for (int i = 0; i < Math.ceil(renderableHeight / 16f); i++) {
                    for(int j = 0; j < Math.ceil(width / 16f); j++) {
                        int drawingWidth = Math.min(16, width - j * 16);
                        int drawingHeight = Math.min(16, renderableHeight - i * 16);
                        int notDrawingHeight = 16 - drawingHeight;
                        int notDrawingWidth = 16 - drawingWidth;

                        graphics.blit(TextureAtlas.LOCATION_BLOCKS,
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
