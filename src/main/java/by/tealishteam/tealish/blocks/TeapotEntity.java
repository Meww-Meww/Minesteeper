package by.tealishteam.tealish.blocks;

import by.tealishteam.tealish.items.LooseLeafTea;
import by.tealishteam.tealish.menus.TeapotMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;

public class TeapotEntity extends BaseContainerBlockEntity {
    public static final int DATA_LIT_TIME = 0;
    public static final int DATA_LIT_DURATION = 1;
    public static final int DATA_COOKING_PROGRESS = 2;
    public static final int DATA_COOKING_TOTAL_TIME = 3;

    public static final int DATA_COUNT = 4;

    private int litTime;
    private int litDuration;
    private int cookingProgress;
    private int cookingTotalTime;

    private NonNullList<ItemStack> items = NonNullList.withSize(TeapotMenu.SLOT_COUNT, ItemStack.EMPTY);

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
        super(TealishBlockEntityTypes.TEAPOT.get(), pos, state);
    }

    protected Component getDefaultName() {
        return Component.translatable("container.teapot");
    }

    protected AbstractContainerMenu createMenu(int menuType, Inventory inventory) {
        return new TeapotMenu(menuType, inventory, this, dataAccess);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag, this.items);
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

    private boolean isLit() {
        return this.litTime > 0;
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
