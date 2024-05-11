package by.meww_meww.minesteeper;

import by.meww_meww.minesteeper.blocks.MinesteeperBlockEntityTypes;
import by.meww_meww.minesteeper.blocks.MinesteeperBlocks;
import by.meww_meww.minesteeper.effects.MinesteeperEffects;
import by.meww_meww.minesteeper.fluids.MinesteeperFluids;
import by.meww_meww.minesteeper.items.LooseLeafTea;
import by.meww_meww.minesteeper.items.Tea;
import by.meww_meww.minesteeper.items.MinesteeperCreativeModeTab;
import by.meww_meww.minesteeper.items.MinesteeperItems;
import by.meww_meww.minesteeper.loot.MinesteeperLootModifiers;
import by.meww_meww.minesteeper.menus.MinesteeperMenuTypes;
import by.meww_meww.minesteeper.network.NetworkHandler;
import by.meww_meww.minesteeper.particles.MinesteeperParticles;
import by.meww_meww.minesteeper.recipes.MinesteeperRecipes;
import by.meww_meww.minesteeper.world.MinesteeperFeatures;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

@Mod(Minesteeper.MODID)
public class Minesteeper
{
    public static final String MODID = "minesteeper";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Minesteeper()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::colorSetup);
        modEventBus.addListener(this::registerBlockColors);

        NetworkHandler.register();

        // Register the Deferred Register to the mod event bus so blocks get registered
        MinesteeperBlocks.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        MinesteeperItems.register(modEventBus);
        MinesteeperFluids.register(modEventBus);
        MinesteeperBlockEntityTypes.register(modEventBus);
        MinesteeperMenuTypes.register(modEventBus);
        MinesteeperRecipes.register(modEventBus);
        MinesteeperParticles.register(modEventBus);
        MinesteeperFeatures.register(modEventBus);
        MinesteeperEffects.register(modEventBus);
        MinesteeperLootModifiers.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        MinesteeperCreativeModeTab.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    private void colorSetup(final RegisterColorHandlersEvent.Item event)
    {
        event.register(new LooseLeafTea.LooseLeafTeaColor(), MinesteeperItems.LOOSE_LEAF_TEA.get());
        event.register(new Tea.TeaColor(), MinesteeperItems.TEA.get());

        event.register((stack, tintIndex) -> {
            BlockState state = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
            BlockColors blockColors = Minecraft.getInstance().getBlockColors();
            return blockColors.getColor(state, null, null, tintIndex);
        }, MinesteeperBlocks.BERGAMOT_LEAVES.get());
    }

    public void registerBlockColors(RegisterColorHandlersEvent.Block event){
        event.register((state, world, pos, tintIndex) ->
                        world != null && pos != null ? BiomeColors.getAverageFoliageColor(world, pos)
                                : FoliageColor.getDefaultColor(),
                MinesteeperBlocks.BERGAMOT_LEAVES.get());
    }

    @SubscribeEvent
    public void cancelSleep(PlayerSleepInBedEvent event){
        if(event.getEntity().hasEffect(MinesteeperEffects.CAFFEINATED.get())){
            event.setResult(Player.BedSleepingProblem.OTHER_PROBLEM);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
