package by.tealishteam.tealish.menus;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TeapotMenu extends AbstractContainerMenu {
    public static final int INGREDIENT_SLOT = 0;
    public static final int FUEL_SLOT = 1;
    public static final int SLOT_COUNT = 2;
    private final Container teapot;

    public TeapotMenu(int inventorySize, Inventory teapotInventory) {
        this(inventorySize, teapotInventory, new SimpleContainer(SLOT_COUNT));
    }

    public TeapotMenu(int inventorySize, Inventory teapotInventory, Container container) {
        super(TealishMenuTypes.TEAPOT_MENU.get(), inventorySize);
        this.teapot = container;
        checkContainerSize(container, SLOT_COUNT);
        this.teapot.startOpen(teapotInventory.player);

        this.addSlot(new Slot(this.teapot, INGREDIENT_SLOT, 40, 17));
        this.addSlot(new Slot(this.teapot, FUEL_SLOT, 40, 53));

        // Inventory slots
        for(int l = 0; l < 3; ++l) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(teapotInventory, k + l * 9 + 9, 8 + k * 18, l * 18 + 84));
            }
        }

        // Hotbar slots
        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(teapotInventory, i1, 8 + i1 * 18, 142));
        }
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