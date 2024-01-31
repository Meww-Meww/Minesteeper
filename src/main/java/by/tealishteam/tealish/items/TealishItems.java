package by.tealishteam.tealish.items;

import by.tealishteam.tealish.blocks.TealishBlocks;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static by.tealishteam.tealish.Tealish.MODID;

public class TealishItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    // Block Items
    public static final RegistryObject<Item> TEAPOT = ITEMS.register("teapot",
            () -> new BlockItem(TealishBlocks.TEAPOT.get(), new Item.Properties())
    );

    // Items
    public static final RegistryObject<Item> TEA_LEAVES = ITEMS.register("tea_leaves",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(1).saturationMod(1f).build()))
    );

    public static void register(IEventBus modEventBus){
        ITEMS.register(modEventBus);
    }
}
