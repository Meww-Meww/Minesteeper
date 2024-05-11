package by.meww_meww.minesteeper.world.generation;

import by.meww_meww.minesteeper.blocks.MinesteeperBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static net.minecraftforge.versions.forge.ForgeVersion.MOD_ID;

public class MinesteeperPlacedFeatures {
    public static final ResourceKey<PlacedFeature> BERGAMOT_TREE_FEATURE = ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(MOD_ID, "bergamot_tree"));
    public static void bootstrap(BootstapContext<PlacedFeature> context)
    {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, configuredFeatures, BERGAMOT_TREE_FEATURE, MinesteeperConfiguredFeatures.BERGAMOT_TREE, List.of(
                CountPlacement.of(1000),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_TOP_SOLID,
                PlacementUtils.filteredByBlockSurvival(MinesteeperBlocks.BERGAMOT_SAPLING.get()),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(BlockPos.ZERO.above(2), Blocks.AIR)),
                BiomeFilter.biome()
        ));
    }

    private static void register(BootstapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures, ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuredFeatures.getOrThrow(configuredFeature), List.copyOf(modifiers)));
    }
}
