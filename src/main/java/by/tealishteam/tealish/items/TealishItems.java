package by.tealishteam.tealish.items;

import by.tealishteam.tealish.blocks.TealishBlocks;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Blocks;
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
            () -> new TeaLeaves(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(1).saturationMod(1f).build()))
    );

    public static final RegistryObject<Item> LOOSE_LEAF_TEA = ITEMS.register("loose_leaf_tea",
            () -> new LooseLeafTea(new Item.Properties())
    );

    public static final RegistryObject<Item> TEA = ITEMS.register("tea",
            () -> new Tea(new Item.Properties().food(new FoodProperties.Builder().build()))
    );

    public static final RegistryObject<Item> MUG = ITEMS.register("mug", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TEA_SEEDS = ITEMS.register("tea_seeds", () -> new ItemNameBlockItem(TealishBlocks.TEA_LEAVES_CROP.get(), new Item.Properties()));

    public static void register(IEventBus modEventBus){
        ITEMS.register(modEventBus);
    }
}
