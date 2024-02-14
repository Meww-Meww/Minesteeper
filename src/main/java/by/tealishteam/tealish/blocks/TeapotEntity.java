package by.tealishteam.tealish.blocks;

import by.tealishteam.tealish.items.LooseLeafTea;
import by.tealishteam.tealish.menus.TeapotMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class TeapotEntity extends BaseContainerBlockEntity {
    private int litTime;
    private int litDuration;
    private int cookingProgress;
    private int cookingTotalTime = 200;
    private FluidTank waterTank;

    private NonNullList<ItemStack> items = NonNullList.withSize(TeapotMenu.SLOT_COUNT, ItemStack.EMPTY);

    public TeapotEntity(BlockPos pos, BlockState state) {
        super(TealishBlockEntityTypes.TEAPOT.get(), pos, state);
        waterTank = new FluidTank(FluidType.BUCKET_VOLUME);
    }

    protected Component getDefaultName() {
        return Component.translatable("container.teapot");
    }

    protected AbstractContainerMenu createMenu(int menuType, Inventory inventory) {
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        buffer.writeBlockPos(worldPosition);
        return new TeapotMenu(menuType, inventory, buffer);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag, this.items);
        this.waterTank.readFromNBT(compoundTag.getCompound("Fluid"));
    }

    @Override
    public void saveAdditional(CompoundTag compound){
        super.saveAdditional(compound);
        ContainerHelper.saveAllItems(compound, this.items);
        compound.put("Fluid", waterTank.writeToNBT(new CompoundTag()));
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag data = super.getUpdateTag();
        this.saveAdditional(data);
        return data;
    }

    @Override
    public void handleUpdateTag(CompoundTag syncData) {
        super.handleUpdateTag(syncData);
        this.load(syncData);
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        return this.getItems().get(index);
    }

    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    public ItemStack removeItem(int index, int amount) {
        return ContainerHelper.removeItem(this.getItems(), index, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(this.getItems(), index);
    }

    @Override
    public void setItem(int index, ItemStack itemStack) {
        this.getItems().set(index, itemStack);
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        this.getItems().clear();
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, TeapotEntity teapot) {
        if (teapot.isLit()) {
            teapot.litTime--;
        }

        ItemStack fuel = teapot.items.get(1);
        ItemStack ingredient = teapot.items.get(0);
        boolean hasTeaLeaves = ingredient.getItem() instanceof LooseLeafTea;
        boolean hasFuel = !fuel.isEmpty();
        if (teapot.isLit() || hasTeaLeaves && hasFuel) {
            if (!teapot.isLit() && teapot.canBurn(teapot.getItems())) {
                teapot.litTime = teapot.getBurnDuration(fuel);
                teapot.litDuration = teapot.litTime;
                if (teapot.isLit()) {
                    if (fuel.hasCraftingRemainingItem()) {
                        teapot.items.set(1, fuel.getCraftingRemainingItem());
                    } else if (hasFuel) {
                        fuel.shrink(1);

                        // I have no idea why this is necessary, but its in vanilla so ok :/
                        if (fuel.isEmpty()) {
                            teapot.items.set(1, fuel.getCraftingRemainingItem());
                        }
                    }
                }
            }

            if (teapot.isLit() && teapot.canBurn(teapot.getItems())) {
                teapot.cookingProgress++;
                if (teapot.cookingProgress == teapot.cookingTotalTime) {
                    teapot.cookingProgress = 0;
                    //teapot.cookingTotalTime = getTotalCookTime(p_155014_, p_155017_);
                    teapot.burn(teapot.getItems());
                    //if (teapot.burn(teapot.getItems())) {
                        // TODO logic for producing tea fluid
                        //p_155017_.setRecipeUsed(recipeholder);
                    //}
                }
            } else {
                teapot.cookingProgress = 0;
            }
        } else if (!teapot.isLit() && teapot.cookingProgress > 0) {
            teapot.cookingProgress = Mth.clamp(teapot.cookingProgress - 2, 0, teapot.cookingTotalTime);
        }

    }

    public InteractionResult attemptUseFluidItem(Player player, InteractionHand hand, Level level, BlockState blockState, BlockPos blockPos) {
        System.out.println(waterTank.getFluidAmount());
        if(player.level().isClientSide()){
            return InteractionResult.PASS;
        }

        if(!player.hasItemInSlot(EquipmentSlot.MAINHAND) || hand != InteractionHand.MAIN_HAND){
            return InteractionResult.PASS;
        }

        ItemStack heldStack = player.getMainHandItem();
        if (heldStack.getItem() == Items.BUCKET) {
            if(waterTank.getFluidAmount() < FluidType.BUCKET_VOLUME){
                return InteractionResult.PASS;
            }

            FluidStack outStack = drain(FluidType.BUCKET_VOLUME, IFluidHandler.FluidAction.EXECUTE);
            ItemStack filledBucket = new ItemStack(outStack.getFluid().getBucket(), 1);
            if (heldStack.getCount() == 1) {
                player.setItemInHand(hand, filledBucket);
            } else {
                heldStack.shrink(1);
                if (!player.addItem(filledBucket)) {
                    player.drop(filledBucket, true);
                }
            }

            this.setChanged();
            return InteractionResult.CONSUME;

        } else if (heldStack.getItem() instanceof BucketItem filledBucket) {
            FluidStack bucketContent = new FluidStack(filledBucket.getFluid(), FluidType.BUCKET_VOLUME);
            if (waterTank.fill(bucketContent, IFluidHandler.FluidAction.SIMULATE) == FluidType.BUCKET_VOLUME) {//can fit entire bucket in tank?
                waterTank.fill(bucketContent, IFluidHandler.FluidAction.EXECUTE);
                player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BUCKET, 1));

                level.sendBlockUpdated(blockPos, blockState, this.getBlockState(), Block.UPDATE_CLIENTS);
                this.setChanged();
                return InteractionResult.CONSUME;
            }
        }

        return InteractionResult.PASS;
    }

    public FluidStack drain(int amount, IFluidHandler.FluidAction action) {
        FluidStack currentFluid = waterTank.getFluid();

        if(amount > currentFluid.getAmount()){
            waterTank.drain(currentFluid.getAmount(), action);
            return FluidStack.EMPTY;
        }

        return waterTank.drain(amount, action);
    }

    public boolean isLit() {
        return this.litTime > 0;
    }

    public float getLitProgress() {
        int duration = litDuration;
        if (litDuration == 0) {
            duration = 200;
        }

        return Mth.clamp((float)litTime / (float)duration, 0.0F, 1.0F);
    }

    public float getBurnProgress() {
        return cookingTotalTime != 0 &&cookingProgress != 0 ? Mth.clamp((float)cookingProgress / (float)cookingTotalTime, 0.0F, 1.0F) : 0.0F;
    }

    public FluidTank getFluidTank() {
        return waterTank;
    }

    private boolean canBurn(NonNullList<ItemStack> teapotItems) {
        // TODO add logic for when teapot has tea
        return true;
    }

   private int getBurnDuration(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        } else {
            return ForgeHooks.getBurnTime(fuel, RecipeType.SMELTING);
        }
   }

    private boolean burn(NonNullList<ItemStack> teapotItems) {
        if (this.canBurn(teapotItems)) {
            ItemStack ingredient = teapotItems.get(0);
            ingredient.shrink(1);
            return true;
        } else {
            return false;
        }
    }
}
