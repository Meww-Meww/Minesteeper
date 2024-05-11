package by.meww_meww.minesteeper.world;

import by.meww_meww.minesteeper.Minesteeper;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MinesteeperFeatures {
    private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Minesteeper.MODID);
    public static final RegistryObject<TreeFeature> BERGAMOT_TREE = FEATURES.register("bergamot_tree", () -> new TreeFeature(TreeConfiguration.CODEC));

    public static void register(IEventBus modEventBus){
        FEATURES.register(modEventBus);
    }
}
