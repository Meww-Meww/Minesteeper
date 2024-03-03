package by.tealishteam.tealish.world;

import by.tealishteam.tealish.blocks.TealishBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;

import java.util.List;

public class BergamotTreeConfiguration extends TreeConfiguration
{
    public final int minHeight;
    public final int maxHeight;

    public static final Codec<BergamotTreeConfiguration> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder.group(
                BlockStateProvider.CODEC.fieldOf("trunk_provider").forGetter((instance) -> instance.trunkProvider),
                BlockStateProvider.CODEC.fieldOf("foliage_provider").forGetter((instance) -> instance.foliageProvider),
                Codec.INT.fieldOf("min_height").forGetter((instance) -> instance.minHeight),
                Codec.INT.fieldOf("max_height").forGetter((instance) -> instance.maxHeight),
                TreeDecorator.CODEC.listOf().fieldOf("decorators").forGetter(instance -> instance.decorators)
        ).apply(builder, BergamotTreeConfiguration::new);
    });

    private BergamotTreeConfiguration(BlockStateProvider trunkProvider, BlockStateProvider foliageProvider, int minHeight, int maxHeight, List<TreeDecorator> decorators)
    {
        super(trunkProvider, null, foliageProvider, null, null, null, new TwoLayersFeatureSize(1, 0, 1), decorators, false, false);

        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    public static class Builder
    {
        protected BlockStateProvider trunkProvider;
        protected BlockStateProvider foliageProvider;
        protected List<TreeDecorator> decorators;
        protected int minHeight;
        protected int maxHeight;

        public Builder()
        {
            this.trunkProvider = BlockStateProvider.simple(Blocks.OAK_LOG);
            this.foliageProvider = BlockStateProvider.simple(TealishBlocks.BERGAMOT_LEAVES.get());
            this.minHeight = 2;
            this.maxHeight = 4;
        }

        public BergamotTreeConfiguration build()
        {
            return new BergamotTreeConfiguration(this.trunkProvider, this.foliageProvider, this.minHeight, this.maxHeight, this.decorators);
        }
    }
}
