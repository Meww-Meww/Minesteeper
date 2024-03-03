package by.tealishteam.tealish.world.generation;

import by.tealishteam.tealish.blocks.TealishBlocks;
import by.tealishteam.tealish.world.TealishFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import java.util.OptionalInt;

import static by.tealishteam.tealish.Tealish.MODID;

public class TealishConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> BERGAMOT_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(MODID, "bergamot_tree"));

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        register(context, BERGAMOT_TREE, TealishFeatures.BERGAMOT_TREE.get(),
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(Blocks.OAK_LOG),
                        new StraightTrunkPlacer(0, 1, 1),
                        BlockStateProvider.simple(TealishBlocks.BERGAMOT_LEAVES.get()),
                        new BlobFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), 1),
                        new TwoLayersFeatureSize(2, 1, 1))
                .ignoreVines().build());
    }
    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> configuredFeaturePath, F feature, FC config) {
        context.register(configuredFeaturePath, new ConfiguredFeature<>(feature, config));
    }
}
