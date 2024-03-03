package by.tealishteam.tealish.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static by.tealishteam.tealish.Tealish.MODID;

public class TealishCreativeModeTab {
    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final RegistryObject<CreativeModeTab> TEALISH_TAB = CREATIVE_MODE_TABS.register("tealish_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> TealishItems.TEA_LEAVES.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(TealishItems.TEA_LEAVES.get());
                output.accept(TealishItems.LAVENDER.get());
                output.accept(TealishItems.BERGAMOT_SAPLING.get());
                output.accept(TealishItems.BERGAMOT_LEAVES.get());
                output.accept(TealishItems.TEAPOT.get());
                output.accept(TealishItems.UNFIRED_TEAPOT.get());
                output.accept(TealishItems.TEA_SEEDS.get());
                output.accept(TealishItems.LAVENDER_SEEDS.get());
                output.accept(TealishItems.LOOSE_LEAF_TEA.get());
                output.accept(TealishItems.TEA.get());
                output.accept(TealishItems.MUG.get());
            }).build());

    public static void register(IEventBus modEventBus){
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
