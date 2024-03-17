package by.tealishteam.tealish.blocks;

import by.tealishteam.tealish.blocks.growers.BergamotTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static by.tealishteam.tealish.Tealish.MODID;

public class TealishBlocks {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final RegistryObject<Block> TEAPOT = BLOCKS.register("teapot", () -> new Teapot(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(1.25F, 4.2F)));
    public static final RegistryObject<Block> UNFIRED_TEAPOT = BLOCKS.register("unfired_teapot", () -> new TeapotShapedBlock(BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).strength(0.6F).sound(SoundType.GRAVEL)));

    public static final RegistryObject<Block> TEA_LEAVES_CROP = BLOCKS.register("tea_crop", () -> new CropBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> LAVENDER_CROP = BLOCKS.register("lavender_crop", () -> new CropBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> BERGAMOT_LEAVES = BLOCKS.register("bergamot_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isViewBlocking((BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) -> false).ignitedByLava().isRedstoneConductor(TealishBlocks::notViewBlocking).isSuffocating(TealishBlocks::notViewBlocking)));
    public static final RegistryObject<Block> BERGAMOT_SAPLING = BLOCKS.register("bergamot_sapling", () -> new SaplingBlock(new BergamotTreeGrower(), BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));

    public static void register(IEventBus modEventBus){
        BLOCKS.register(modEventBus);
    }

    private static Boolean notViewBlocking(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }
}
