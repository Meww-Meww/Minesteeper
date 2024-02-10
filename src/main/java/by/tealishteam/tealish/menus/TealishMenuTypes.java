package by.tealishteam.tealish.menus;

import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static by.tealishteam.tealish.Tealish.MODID;

public class TealishMenuTypes {
    private static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);
    public static final RegistryObject<MenuType<TeapotMenu>> TEAPOT_MENU = MENU_TYPES.register("teapot", () -> new MenuType<>(TeapotMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static void register(IEventBus modEventBus){
        MENU_TYPES.register(modEventBus);
    }
}
