package by.meww_meww.minesteeper.blocks;

import by.meww_meww.minesteeper.items.MinesteeperItems;
import by.meww_meww.minesteeper.menus.TeapotMenu;
import by.meww_meww.minesteeper.recipes.TeapotRecipe;
import by.meww_meww.minesteeper.fluids.TeaFluid;
import by.meww_meww.minesteeper.network.NetworkHandler;
import by.meww_meww.minesteeper.network.packets.SyncTeapotMenu;
import by.meww_meww.minesteeper.recipes.MinesteeperRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;

public class TeapotEntity extends BaseContainerBlockEntity implements Container {

    public static final int DATA_LIT_TIME = 0;
    public static final int DATA_LIT_DURATION = 1;
    public static final int DATA_COOKING_PROGRESS = 2;
    public static final int DATA_COOKING_TOTAL_TIME = 3;
    public static final int DATA_COUNT = 4;

    public static final int CAPACITY = FluidType.BUCKET_VOLUME;

    public int litTime;
    private int litDuration;
    private int cookingProgress;
    private int cookingTotalTime = 200;
    private FluidTank waterTank = new FluidTank(CAPACITY);

    private NonNullList<ItemStack> items = NonNullList.withSize(TeapotMenu.SLOT_COUNT, ItemStack.EMPTY);

    private ItemStackHandler itemHandler = new ItemStackHandler(items);

    private final RecipeManager.CachedCheck<TeapotRecipe.Container, TeapotRecipe> quickCheck = RecipeManager.createCheck(MinesteeperRecipes.TEAPOT_RECIPE_TYPE.get());
    private TeapotRecipe.Container container = new TeapotRecipe.Container(itemHandler, waterTank);

    protected final ContainerData dataAccess = new ContainerData() {
        public int get(int type) {
            switch (type) {
                case DATA_LIT_TIME:
                    return TeapotEntity.this.litTime;
                case DATA_LIT_DURATION:
                    return TeapotEntity.this.litDuration;
                case DATA_COOKING_PROGRESS:
                    return TeapotEntity.this.cookingProgress;
                case DATA_COOKING_TOTAL_TIME:
                    return TeapotEntity.this.cookingTotalTime;
                default:
                    return 0;
            }
        }

        public void set(int type, int value) {
            switch (type) {
                case DATA_LIT_TIME:
                    TeapotEntity.this.litTime = value;
                    break;
                case DATA_LIT_DURATION:
                    TeapotEntity.this.litDuration = value;
                    break;
                case DATA_COOKING_PROGRESS:
                    TeapotEntity.this.cookingProgress = value;
                    break;
                case DATA_COOKING_TOTAL_TIME:
                    TeapotEntity.this.cookingTotalTime = value;
            }
        }
        public int getCount() {
            return DATA_COUNT;
        }
    };

    public TeapotEntity(BlockPos pos, BlockState state) {
        super(MinesteeperBlockEntityTypes.TEAPOT.get(), pos, state);
    }

    protected Component getDefaultName() {
        return Component.translatable("container.teapot");
    }

    protected AbstractContainerMenu createMenu(int menuType, Inventory inventory) {
        return new TeapotMenu(menuType, inventory, this, dataAccess, this.getBlockPos());
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag, this.items);
        itemHandler = new ItemStackHandler(items);
        this.waterTank.readFromNBT(compoundTag.getCompound("Fluid"));
        container = new TeapotRecipe.Container(itemHandler, waterTank);
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

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, TeapotEntity teapot) {
        boolean updated = false;
        boolean previousLitState = teapot.isLit();

        if (teapot.isLit()) {
            teapot.litTime--;
            updated = true;
        }

        ItemStack fuel = teapot.items.get(TeapotMenu.FUEL_SLOT);
        ItemStack ingredient = teapot.items.get(TeapotMenu.INGREDIENT_SLOT);
        boolean hasInput = !ingredient.isEmpty();
        boolean hasFuel = !fuel.isEmpty();
        if (teapot.isLit() || hasInput && hasFuel) {
            RecipeHolder<?> recipeHolder;
            if (hasInput) {
                recipeHolder = teapot.quickCheck.getRecipeFor(teapot.container, level).orElse(null);
            } else {
                recipeHolder = null;
            }

            if (!teapot.isLit() && teapot.canBurn(recipeHolder, teapot.getItems(), level)) {
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

            if (teapot.isLit() && teapot.canBurn(recipeHolder, teapot.getItems(), level)) {
                teapot.cookingProgress++;
                if (teapot.cookingProgress == teapot.cookingTotalTime) {
                    teapot.cookingProgress = 0;
                    teapot.burn(recipeHolder, teapot.getItems(), level);
                }
                updated = true;
            } else {
                teapot.cookingProgress = 0;
                updated = true;
            }
        } else if (!teapot.isLit() && teapot.cookingProgress > 0) {
            teapot.cookingProgress = Mth.clamp(teapot.cookingProgress - 2, 0, teapot.cookingTotalTime);
            updated = true;
        }

        if (previousLitState != teapot.isLit()) {
            blockState = blockState.setValue(Teapot.LIT, Boolean.valueOf(teapot.isLit()));
            level.setBlock(blockPos, blockState, 3);
        }

        if(updated){
            teapot.setChanged();
        }
    }

    public InteractionResult attemptUseFluidItem(Player player, InteractionHand hand) {
        if(player.level().isClientSide()){
            return InteractionResult.PASS;
        }

        if(!player.hasItemInSlot(EquipmentSlot.MAINHAND) || hand != InteractionHand.MAIN_HAND){
            return InteractionResult.PASS;
        }

        ItemStack heldStack = player.getMainHandItem();
        if (heldStack.getItem() == Items.BUCKET || heldStack.getItem() == MinesteeperItems.MUG.get()) {
            if(waterTank.getFluidAmount() < FluidType.BUCKET_VOLUME){
                return InteractionResult.PASS;
            }

            if(heldStack.getItem() == Items.BUCKET && waterTank.getFluid().getFluid().getBucket() == null){
                return InteractionResult.PASS;
            } else if(heldStack.getItem() == MinesteeperItems.MUG.get() && !(waterTank.getFluid().getFluid() instanceof TeaFluid)){
                return InteractionResult.PASS;
            }

            FluidStack outStack = drain(FluidType.BUCKET_VOLUME, IFluidHandler.FluidAction.EXECUTE);
            ItemStack filledBucket;

            if(outStack.getFluid() instanceof TeaFluid){
                filledBucket = TeaFluid.getDrink(outStack, 1);
            }
            else {
                filledBucket = new ItemStack(outStack.getFluid().getBucket(), 1);
            }

            if (heldStack.getCount() == 1) {
                player.setItemInHand(hand, filledBucket);
            } else {
                heldStack.shrink(1);
                if (!player.addItem(filledBucket)) {
                    player.drop(filledBucket, true);
                }
            }

            this.sendUpdatePacket();
            this.setChanged();
            return InteractionResult.CONSUME;

        } else if (heldStack.getItem() instanceof BucketItem filledBucket) {
            FluidStack bucketContent = new FluidStack(filledBucket.getFluid(), FluidType.BUCKET_VOLUME);
            if (waterTank.fill(bucketContent, IFluidHandler.FluidAction.SIMULATE) == FluidType.BUCKET_VOLUME) {//can fit entire bucket in tank?
                waterTank.fill(bucketContent, IFluidHandler.FluidAction.EXECUTE);
                player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BUCKET, 1));

                this.sendUpdatePacket();
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

    public void sendFluidInfo(ServerPlayer player){
        NetworkHandler.channel.send(new SyncTeapotMenu(waterTank.getFluid()), PacketDistributor.PLAYER.with(player));
    }

    private void sendUpdatePacket(){
        NetworkHandler.channel.send(new SyncTeapotMenu(waterTank.getFluid()), PacketDistributor.TRACKING_CHUNK.with(level.getChunkAt(getBlockPos())));
    }

    private boolean canBurn(@Nullable RecipeHolder<?> recipe, NonNullList<ItemStack> items, Level level) {
        if (!items.get(TeapotMenu.INGREDIENT_SLOT).isEmpty() && recipe != null) {
            return ((RecipeHolder<TeapotRecipe>)recipe).value().matches(container, level);
        }
        return false;
    }

   private int getBurnDuration(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        } else {
            return ForgeHooks.getBurnTime(fuel, RecipeType.SMELTING);
        }
   }

    private boolean burn(@Nullable RecipeHolder<?> recipe, NonNullList<ItemStack> items, Level level) {
        if (this.canBurn(recipe, items, level)) {
            ItemStack ingredient = items.get(TeapotMenu.INGREDIENT_SLOT);

            if(((TeapotRecipe)recipe.value()).getResultFluid().getFluid() instanceof TeaFluid) {
                this.waterTank.setFluid(TeaFluid.getFluidStackWithEffects(ingredient, this.waterTank.getFluid(), ((TeapotRecipe) recipe.value()).getResultFluid().getAmount()));
            } else {
                this.waterTank.setFluid(((TeapotRecipe) recipe.value()).getResultFluid());
            }

            ingredient.shrink(1);
            this.sendUpdatePacket();
            return true;
        } else {
            return false;
        }
    }
}
