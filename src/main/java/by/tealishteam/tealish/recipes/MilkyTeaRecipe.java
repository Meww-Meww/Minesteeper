package by.tealishteam.tealish.recipes;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

public class MilkyTeaRecipe extends TeapotRecipe {
    public MilkyTeaRecipe(FluidStack inputFluid, FluidStack outputFluid, Ingredient input) {
        super(inputFluid, outputFluid, input);
    }

    @Override
    public boolean matches(Container container, Level level) {
        if(!container.getFluidTank().getFluid().hasTag()){
            return false;
        }

        return super.matches(container, level) && !container.getFluidTank().getFluid().getTag().getBoolean("Milk");
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return TealishRecipes.MILKY_TEA_RECIPE.get();
    }
}
