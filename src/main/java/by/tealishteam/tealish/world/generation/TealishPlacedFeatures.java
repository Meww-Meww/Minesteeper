package by.tealishteam.tealish.world.generation;

import by.tealishteam.tealish.blocks.TealishBlocks;
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
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

import static net.minecraftforge.versions.forge.ForgeVersion.MOD_ID;

public class TealishPlacedFeatures {
    public static final ResourceKey<PlacedFeature> BERGAMOT_TREE_FEATURE = ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(MOD_ID, "bergamot_tree"));
    public static void bootstrap(BootstapContext<PlacedFeature> context)
    {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, configuredFeatures, BERGAMOT_TREE_FEATURE, TealishConfiguredFeatures.BERGAMOT_TREE, List.of(
                PlacementUtils.filteredByBlockSurvival(TealishBlocks.BERGAMOT_SAPLING.get()),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(BlockPos.ZERO.above(2), Blocks.AIR))
        ));
    }

    private static void register(BootstapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures, ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuredFeatures.getOrThrow(configuredFeature), List.copyOf(modifiers)));
    }
}
