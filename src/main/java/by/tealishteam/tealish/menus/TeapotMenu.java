package by.tealishteam.tealish.menus;

import com.mojang.logging.LogUtils;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TeapotMenu extends AbstractContainerMenu {
    public static final int CONTAINER_SIZE = 5;
    private final Container teapot;

    public TeapotMenu(int inventorySize, Inventory teapotInventory) {
        super(TealishMenuTypes.TEAPOT_MENU.get(), inventorySize);
        this.teapot = new SimpleContainer(CONTAINER_SIZE);
        this.teapot.startOpen(teapotInventory.player);
        LogUtils.getLogger().info("NEW MENU");
        this.addSlot(new Slot(this.teapot, 0, 44 * 18, 20));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < this.teapot.getContainerSize()) {
                if (!this.moveItemStackTo(itemstack1, this.teapot.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.teapot.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.teapot.stillValid(player);
    }
}