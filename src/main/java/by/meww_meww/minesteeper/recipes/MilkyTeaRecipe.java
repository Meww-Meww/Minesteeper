package by.meww_meww.minesteeper.recipes;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

public class MilkyTeaRecipe extends TaggedTeaRecipe {
    public MilkyTeaRecipe(FluidStack inputFluid, FluidStack outputFluid, Ingredient input) {
        super("Milk", inputFluid, outputFluid, input);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MinesteeperRecipes.MILKY_TEA_RECIPE.get();
    }
}
