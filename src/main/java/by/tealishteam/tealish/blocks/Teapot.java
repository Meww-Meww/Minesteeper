package by.tealishteam.tealish.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class Teapot extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape BASE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 2.0D, 12.0D);
    private static final VoxelShape SIDE_N = Block.box(4.0D, 2.0D, 3.0D,4.0D, 8.0D, 4.0D);
    private static final VoxelShape SIDE_E = Block.box(3.0D, 2.0D, 4.0D, 4.0D, 8.0D, 12.0D);
    private static final VoxelShape SIDE_S = Block.box(4.0D, 2.0D, 12.0D, 12.0D, 8.0D, 13.0D);
    private static final VoxelShape SIDE_W = Block.box(12.0D, 2.0D, 4.0D, 13.0D, 8.0D, 12.0D);
    private static final VoxelShape TOP = Block.box(4.0D, 8.0D, 4.0D, 12.0D, 9.0D, 12.0D);
    private static final VoxelShape LID = Block.box(5.0D, 9.0D, 5.0D, 11.0D, 10.0D, 11.0D);
    private static final VoxelShape HANDLE = Block.box(7.0D, 10.0D, 7.0D, 9.0D, 11.0D, 9.0D);
    private static final VoxelShape AABB = Shapes.or(BASE, SIDE_N, SIDE_E, SIDE_S, SIDE_W, TOP, LID, HANDLE);

    public Teapot(Properties properties){
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
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

    public BlockState getStateForPlacement(BlockPlaceContext p_48781_) {
        return this.defaultBlockState().setValue(FACING, p_48781_.getHorizontalDirection().getClockWise());
    }

    public VoxelShape getShape(BlockState p_48816_, BlockGetter p_48817_, BlockPos p_48818_, CollisionContext p_48819_) {
        return AABB;
    }

    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    public BlockState rotate(BlockState p_48811_, Rotation p_48812_) {
        return p_48811_.setValue(FACING, p_48812_.rotate(p_48811_.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_48814_) {
        p_48814_.add(FACING);
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
