package by.tealishteam.tealish.recipes;

import by.tealishteam.tealish.items.Brewable;
import by.tealishteam.tealish.items.TealishItems;
import com.google.common.collect.Lists;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.List;

public class LooseLeafTeaRecipe extends CustomRecipe {
    public LooseLeafTeaRecipe(CraftingBookCategory craftingBookCategory) {
        super(craftingBookCategory);
    }

    @Override
    public boolean matches(CraftingContainer craftingContainer, Level level) {
        if(craftingContainer.isEmpty()){
            return false;
        }

        for(int i = 0; i < craftingContainer.getContainerSize(); ++i) {
            ItemStack itemstack = craftingContainer.getItem(i);
            if (!itemstack.isEmpty()) {
                if(!(itemstack.getItem() instanceof Brewable)){
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public ItemStack assemble(CraftingContainer craftingContainer, RegistryAccess registryAccess) {
        ItemStack looseLeafTeaItem = new ItemStack(TealishItems.LOOSE_LEAF_TEA.get());
        CompoundTag compoundtag = looseLeafTeaItem.getOrCreateTagElement("Effects");
        List<Integer> list = Lists.newArrayList();

        for(int i = 0; i < craftingContainer.getContainerSize(); ++i) {
            ItemStack itemstack = craftingContainer.getItem(i);
            if (!itemstack.isEmpty()) {
                if (itemstack.getItem() instanceof Brewable) {
                    list.add(((Brewable)itemstack.getItem()).getColor());
                }
            }
        }

        compoundtag.putIntArray("Colors", list);

        return looseLeafTeaItem;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return new ItemStack(TealishItems.LOOSE_LEAF_TEA.get());
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        System.out.println("CAN CRAFT");
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return TealishRecipes.LOOSE_LEAF_TEA.get();
    }

    @Override
    public boolean isSpecial(){
        return true;
    }
}
