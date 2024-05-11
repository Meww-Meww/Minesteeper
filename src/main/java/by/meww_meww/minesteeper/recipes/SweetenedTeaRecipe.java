package by.meww_meww.minesteeper.recipes;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

public class SweetenedTeaRecipe extends TaggedTeaRecipe {
    public SweetenedTeaRecipe(FluidStack inputFluid, FluidStack outputFluid, Ingredient input) {
        super("Sugar", inputFluid, outputFluid, input);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MinesteeperRecipes.SWEETENED_TEA_RECIPE.get();
    }
}
