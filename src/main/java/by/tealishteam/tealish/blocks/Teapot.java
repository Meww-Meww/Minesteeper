package by.tealishteam.tealish.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class Teapot extends TeapotShapedBlock {

    public Teapot(Properties properties){
        super(properties);
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

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TeapotEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, TealishBlockEntityTypes.TEAPOT.get(), TeapotEntity::serverTick);
    }
}
