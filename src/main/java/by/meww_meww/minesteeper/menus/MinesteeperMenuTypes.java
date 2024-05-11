package by.meww_meww.minesteeper.menus;

import by.meww_meww.minesteeper.Minesteeper;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MinesteeperMenuTypes {
    private static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Minesteeper.MODID);
    public static final RegistryObject<MenuType<TeapotMenu>> TEAPOT_MENU = MENU_TYPES.register("teapot", () -> new MenuType<>(TeapotMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static void register(IEventBus modEventBus){
        MENU_TYPES.register(modEventBus);
    }
}
