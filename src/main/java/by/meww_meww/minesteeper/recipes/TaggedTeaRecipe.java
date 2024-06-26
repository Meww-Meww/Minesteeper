package by.meww_meww.minesteeper.recipes;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

public abstract class TaggedTeaRecipe extends TeapotRecipe {
    private final String tag;

    public TaggedTeaRecipe(String tag, FluidStack inputFluid, FluidStack outputFluid, Ingredient input) {
        super(inputFluid, outputFluid, input);
        this.tag = tag;
    }

    @Override
    public boolean matches(Container container, Level level) {
        if(!container.getFluidTank().getFluid().hasTag()){
            return false;
        }

        return super.matches(container, level) && !container.getFluidTank().getFluid().getTag().getBoolean(tag);
    }
}

