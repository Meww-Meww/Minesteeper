package by.meww_meww.minesteeper.blocks;

import by.meww_meww.minesteeper.particles.MinesteeperParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class Teapot extends TeapotShapedBlock {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public Teapot(Properties properties){
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.valueOf(false)));
    }

    @Override
    public InteractionResult use(BlockState blockState, Level worldIn, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult){

        if(worldIn.isClientSide){
            return InteractionResult.SUCCESS;
        }

        BlockEntity blockentity = worldIn.getBlockEntity(blockPos);
        if (!(blockentity instanceof TeapotEntity)) {
            return InteractionResult.PASS;
        }

        TeapotEntity teapotEntity = (TeapotEntity) blockentity;

        if(teapotEntity.attemptUseFluidItem(player, hand) == InteractionResult.PASS){
            player.openMenu(teapotEntity);
        }

        return InteractionResult.CONSUME;
    }

    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        if (blockState.getValue(LIT)) {
            if (randomSource.nextInt(10) == 0) {
                level.playLocalSound((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D, SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 0.5F + randomSource.nextFloat(), randomSource.nextFloat() * 0.7F + 0.6F, false);
            }

            double x = blockPos.getX();
            if(blockState.getValue(FACING) == Direction.EAST){
                x += 1.0D;
            } else if(blockState.getValue(FACING) == Direction.NORTH || blockState.getValue(FACING) == Direction.SOUTH){
                x += 0.5D;
            }

            double z = blockPos.getZ();
            if(blockState.getValue(FACING) == Direction.SOUTH){
                z += 1.0D;
            } else if(blockState.getValue(FACING) == Direction.WEST || blockState.getValue(FACING) == Direction.EAST){
                z += 0.5D;
            }

            level.addParticle(MinesteeperParticles.STEAM_PARTICLE.get(), x, (double) blockPos.getY() + 0.5D, z, 0, 0, 0);
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TeapotEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, MinesteeperBlockEntityTypes.TEAPOT.get(), TeapotEntity::serverTick);
    }
}
