package by.tealishteam.tealish.client;

import by.tealishteam.tealish.blocks.TealishBlocks;
import by.tealishteam.tealish.client.gui.TeapotScreen;
import by.tealishteam.tealish.menus.TealishMenuTypes;
import by.tealishteam.tealish.particles.SteamParticle;
import by.tealishteam.tealish.particles.TealishParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static by.tealishteam.tealish.Tealish.MODID;
import static net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents
{
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(
                () -> MenuScreens.register(TealishMenuTypes.TEAPOT_MENU.get(), TeapotScreen::new)
        );
        setRenderLayer(TealishBlocks.TEA_LEAVES_CROP.get(), RenderType.cutout());
    }

    @SubscribeEvent( priority = EventPriority.LOWEST)
    public static void registerParticles(RegisterParticleProvidersEvent event){
        event.registerSpriteSet(TealishParticles.STEAM_PARTICLE.get(), SteamParticle.Provider::new);
    }
}
