package by.tealishteam.tealish.recipes;

import by.tealishteam.tealish.items.ingredients.Brewable;
import by.tealishteam.tealish.items.TealishItems;
import by.tealishteam.tealish.utils.EffectSerialization;
import com.google.common.collect.Lists;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
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
        CompoundTag compoundtag = looseLeafTeaItem.getOrCreateTag();
        List<MobEffectInstance> effects = Lists.newArrayList();

        int rAvg = 0xA2;
        int gAvg = 0xC6;
        int bAvg = 0x6A;
        int totalWeight = 0;
        for(int i = 0; i < craftingContainer.getContainerSize(); i++){
            ItemStack itemstack = craftingContainer.getItem(i);
            if (!itemstack.isEmpty()) {
                int color = ((Brewable)itemstack.getItem()).getColor();
                int colorWeight = ((Brewable)itemstack.getItem()).getColorWeight();
                if(totalWeight == 0){
                    rAvg = ((color & 0xFF0000) >> 16) * colorWeight;
                    gAvg = ((color & 0xFF00) >> 8) * colorWeight;
                    bAvg = (color & 0xFF) * colorWeight;
                } else {
                    rAvg += ((color & 0xFF0000) >> 16) * colorWeight;
                    gAvg += ((color & 0xFF00) >> 8) * colorWeight;
                    bAvg += (color & 0xFF) * colorWeight;
                }
                totalWeight += colorWeight;
                effects.add(((Brewable)itemstack.getItem()).getEffect());
            }
        }

        if(totalWeight > 0){
            rAvg = rAvg / totalWeight;
            gAvg = gAvg / totalWeight;
            bAvg = bAvg / totalWeight;
        }

        int avgColor = (rAvg << 16) | (gAvg << 8) | bAvg;

        compoundtag.putInt("Color", avgColor);
        EffectSerialization.toCompoundTag(effects, compoundtag);

        return looseLeafTeaItem;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return new ItemStack(TealishItems.LOOSE_LEAF_TEA.get());
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
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
