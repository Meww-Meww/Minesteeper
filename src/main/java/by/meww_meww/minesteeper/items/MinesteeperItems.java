package by.meww_meww.minesteeper.items;

import by.meww_meww.minesteeper.Minesteeper;
import by.meww_meww.minesteeper.blocks.MinesteeperBlocks;
import by.meww_meww.minesteeper.items.ingredients.Bergamot;
import by.meww_meww.minesteeper.items.ingredients.Lavender;
import by.meww_meww.minesteeper.items.ingredients.Mint;
import by.meww_meww.minesteeper.items.ingredients.TeaLeaves;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MinesteeperItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Minesteeper.MODID);

    // Block Items
    public static final RegistryObject<Item> TEAPOT = ITEMS.register("teapot",
            () -> new BlockItem(MinesteeperBlocks.TEAPOT.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> UNFIRED_TEAPOT = ITEMS.register("unfired_teapot",
            () -> new BlockItem(MinesteeperBlocks.UNFIRED_TEAPOT.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> BERGAMOT_SAPLING = ITEMS.register("bergamot_sapling",
            () -> new BlockItem(MinesteeperBlocks.BERGAMOT_SAPLING.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> BERGAMOT_LEAVES = ITEMS.register("bergamot_leaves",
            () -> new BlockItem(MinesteeperBlocks.BERGAMOT_LEAVES.get(), new Item.Properties())
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

    public static final RegistryObject<Item> TEA_SEEDS = ITEMS.register("tea_seeds", () -> new ItemNameBlockItem(MinesteeperBlocks.TEA_LEAVES_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> LAVENDER_SEEDS = ITEMS.register("lavender_seeds", () -> new ItemNameBlockItem(MinesteeperBlocks.LAVENDER_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> MINT_SEEDS = ITEMS.register("mint_seeds", () -> new ItemNameBlockItem(MinesteeperBlocks.MINT_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> ROOIBOS_SEEDS = ITEMS.register("rooibos_seeds", () -> new ItemNameBlockItem(MinesteeperBlocks.ROOIBOS_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> SAGE_SEEDS = ITEMS.register("sage_seeds", () -> new ItemNameBlockItem(MinesteeperBlocks.SAGE_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> ROSEHIP_SEEDS = ITEMS.register("rosehip_seeds", () -> new ItemNameBlockItem(MinesteeperBlocks.ROSEHIP_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> LEMONGRASS_SEEDS = ITEMS.register("lemongrass_seeds", () -> new ItemNameBlockItem(MinesteeperBlocks.LEMONGRASS_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> GINGER_SEEDS = ITEMS.register("ginger_seeds", () -> new ItemNameBlockItem(MinesteeperBlocks.GINGER_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHAMOMILE_SEEDS = ITEMS.register("chamomile_seeds", () -> new ItemNameBlockItem(MinesteeperBlocks.CHAMOMILE_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> CONEFLOWER_SEEDS = ITEMS.register("coneflower_seeds", () -> new ItemNameBlockItem(MinesteeperBlocks.CONEFLOWER_CROP.get(), new Item.Properties()));

    public static void register(IEventBus modEventBus){
        ITEMS.register(modEventBus);
    }
}
