package by.tealishteam.tealish;

import by.tealishteam.tealish.blocks.TealishBlockEntityTypes;
import by.tealishteam.tealish.blocks.TealishBlocks;
import by.tealishteam.tealish.items.TealishCreativeModeTab;
import by.tealishteam.tealish.items.TealishItems;
import by.tealishteam.tealish.items.colors.LooseLeafTeaColor;
import by.tealishteam.tealish.items.colors.TeaColor;
import by.tealishteam.tealish.menus.TealishMenuTypes;
import by.tealishteam.tealish.recipes.TealishRecipeProvider;
import by.tealishteam.tealish.recipes.TealishRecipes;
import com.mojang.logging.LogUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
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

@Mod(Tealish.MODID)
public class Tealish
{
    public static final String MODID = "tealish";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Tealish()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::colorSetup);
        modEventBus.addListener(this::gatherData);

        // Register the Deferred Register to the mod event bus so blocks get registered
        TealishBlocks.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        TealishItems.register(modEventBus);
        TealishBlockEntityTypes.register(modEventBus);
        TealishMenuTypes.register(modEventBus);
        TealishRecipes.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        TealishCreativeModeTab.register(modEventBus);

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
        event.register(new LooseLeafTeaColor(), TealishItems.LOOSE_LEAF_TEA.get());
        event.register(new TeaColor(), TealishItems.TEA.get());
    }

    public void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();

        TealishRecipeProvider tealishRecipes = new TealishRecipeProvider(packOutput);
        generator.addProvider(true, tealishRecipes);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
