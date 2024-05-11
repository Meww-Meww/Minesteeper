package by.meww_meww.minesteeper.client;

import by.meww_meww.minesteeper.Minesteeper;
import by.meww_meww.minesteeper.blocks.MinesteeperBlocks;
import by.meww_meww.minesteeper.client.gui.TeapotScreen;
import by.meww_meww.minesteeper.menus.MinesteeperMenuTypes;
import by.meww_meww.minesteeper.particles.MinesteeperParticles;
import by.meww_meww.minesteeper.particles.SteamParticle;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer;

@Mod.EventBusSubscriber(modid = Minesteeper.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents
{
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(
                () -> MenuScreens.register(MinesteeperMenuTypes.TEAPOT_MENU.get(), TeapotScreen::new)
        );
        ItemBlockRenderTypes.setRenderLayer(MinesteeperBlocks.TEA_LEAVES_CROP.get(), RenderType.cutout());
        setRenderLayer(MinesteeperBlocks.LAVENDER_CROP.get(), RenderType.cutout());
        setRenderLayer(MinesteeperBlocks.BERGAMOT_SAPLING.get(), RenderType.cutout());
    }

    @SubscribeEvent( priority = EventPriority.LOWEST)
    public static void registerParticles(RegisterParticleProvidersEvent event){
        event.registerSpriteSet(MinesteeperParticles.STEAM_PARTICLE.get(), SteamParticle.Provider::new);
    }
}
