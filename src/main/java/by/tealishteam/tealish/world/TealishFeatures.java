package by.tealishteam.tealish.world;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static by.tealishteam.tealish.Tealish.MODID;

public class TealishFeatures {
    private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, MODID);
    public static final RegistryObject<TreeFeature> BERGAMOT_TREE = FEATURES.register("bergamot_tree", () -> new TreeFeature(TreeConfiguration.CODEC));

    public static void register(IEventBus modEventBus){
        FEATURES.register(modEventBus);
    }
}
