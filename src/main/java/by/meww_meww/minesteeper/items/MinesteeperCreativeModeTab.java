package by.meww_meww.minesteeper.items;

import by.meww_meww.minesteeper.Minesteeper;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class MinesteeperCreativeModeTab {
    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Minesteeper.MODID);
    public static final RegistryObject<CreativeModeTab> MINESTEEPER_TAB = CREATIVE_MODE_TABS.register("minesteeper_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> MinesteeperItems.TEA_LEAVES.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(MinesteeperItems.TEA_LEAVES.get());
                output.accept(MinesteeperItems.MINT.get());
                output.accept(MinesteeperItems.LAVENDER.get());
                output.accept(MinesteeperItems.BERGAMOT.get());
                output.accept(MinesteeperItems.SUGAR_CUBE.get());
                output.accept(MinesteeperItems.TEAPOT.get());
                output.accept(MinesteeperItems.UNFIRED_TEAPOT.get());
                output.accept(MinesteeperItems.TEA_SEEDS.get());
                output.accept(MinesteeperItems.MINT_SEEDS.get());
                output.accept(MinesteeperItems.LAVENDER_SEEDS.get());
                output.accept(MinesteeperItems.BERGAMOT_SAPLING.get());
                output.accept(MinesteeperItems.BERGAMOT_LEAVES.get());
                output.accept(MinesteeperItems.LOOSE_LEAF_TEA.get());
                output.accept(MinesteeperItems.TEA.get());
                output.accept(MinesteeperItems.MUG.get());
            }).build());

    public static void register(IEventBus modEventBus){
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
