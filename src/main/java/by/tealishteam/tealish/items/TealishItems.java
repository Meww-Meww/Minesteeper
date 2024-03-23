package by.tealishteam.tealish.items;

import by.tealishteam.tealish.blocks.TealishBlocks;
import by.tealishteam.tealish.items.ingredients.Bergamot;
import by.tealishteam.tealish.items.ingredients.Lavender;
import by.tealishteam.tealish.items.ingredients.Mint;
import by.tealishteam.tealish.items.ingredients.TeaLeaves;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
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

    public static final RegistryObject<Item> UNFIRED_TEAPOT = ITEMS.register("unfired_teapot",
            () -> new BlockItem(TealishBlocks.UNFIRED_TEAPOT.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> BERGAMOT_SAPLING = ITEMS.register("bergamot_sapling",
            () -> new BlockItem(TealishBlocks.BERGAMOT_SAPLING.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> BERGAMOT_LEAVES = ITEMS.register("bergamot_leaves",
            () -> new BlockItem(TealishBlocks.BERGAMOT_LEAVES.get(), new Item.Properties())
    );

    // Items
    public static final RegistryObject<Item> TEA_LEAVES = ITEMS.register("tea_leaves",
            () -> new TeaLeaves(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(1).saturationMod(1f).build()))
    );

    public static final RegistryObject<Item> MINT = ITEMS.register("mint",
            () -> new Mint(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(1).saturationMod(1f).build()))
    );

    public static final RegistryObject<Item> LAVENDER = ITEMS.register("lavender",
            () -> new Lavender(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(1).saturationMod(1f).build()))
    );

    public static final RegistryObject<Item> BERGAMOT = ITEMS.register("bergamot",
            () -> new Bergamot(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(2).saturationMod(1f).build()))
    );

    public static final RegistryObject<Item> SUGAR_CUBE = ITEMS.register("sugar_cube", () -> new Item(new Item.Properties()) );

    public static final RegistryObject<Item> LOOSE_LEAF_TEA = ITEMS.register("loose_leaf_tea",
            () -> new LooseLeafTea(new Item.Properties())
    );

    public static final RegistryObject<Item> TEA = ITEMS.register("tea",
            () -> new Tea(new Item.Properties().stacksTo(1).food(new FoodProperties.Builder().build()))
    );

    public static final RegistryObject<Item> MUG = ITEMS.register("mug", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TEA_SEEDS = ITEMS.register("tea_seeds", () -> new ItemNameBlockItem(TealishBlocks.TEA_LEAVES_CROP.get(), new Item.Properties()));

    public static final RegistryObject<Item> LAVENDER_SEEDS = ITEMS.register("lavender_seeds", () -> new ItemNameBlockItem(TealishBlocks.LAVENDER_CROP.get(), new Item.Properties()));

    public static final RegistryObject<Item> MINT_SEEDS = ITEMS.register("mint_seeds", () -> new ItemNameBlockItem(TealishBlocks.MINT_CROP.get(), new Item.Properties()));

    public static void register(IEventBus modEventBus){
        ITEMS.register(modEventBus);
    }
}
