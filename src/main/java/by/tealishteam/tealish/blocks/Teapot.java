package by.tealishteam.tealish.blocks;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class Teapot extends Block implements EntityBlock {
    //public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public Teapot(Properties properties){
        super(properties);
        //registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public InteractionResult use(BlockState blockState, Level worldIn, BlockPos blockPos, Player player, InteractionHand handIn, BlockHitResult blockHitResult){

        if(worldIn.isClientSide){
            return InteractionResult.SUCCESS;
        }

        BlockEntity blockentity = worldIn.getBlockEntity(blockPos);
        LogUtils.getLogger().info("blockentity {}", blockentity);
        LogUtils.getLogger().info("blockstate {}", blockState);
        LogUtils.getLogger().info("worldIn {}", worldIn);
        LogUtils.getLogger().info("blockPos {}", blockPos);
        if (blockentity instanceof TeapotEntity) {
            player.openMenu((TeapotEntity)blockentity);
            LogUtils.getLogger().info("OPEN MENU");
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TeapotEntity(pos, state);
    }
}
