package by.tealishteam.tealish.recipes;

import by.tealishteam.tealish.menus.TeapotMenu;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;

public class TeapotRecipe implements Recipe<TeapotRecipe.Container> {

    public final FluidStack inputFluid;
    public final FluidStack outputFluid;
    public final Ingredient input;

    public TeapotRecipe(FluidStack inputFluid, FluidStack outputFluid, Ingredient input) {
        this.inputFluid = inputFluid;
        this.outputFluid = outputFluid;
        this.input = input;
    }

    public static class Container extends RecipeWrapper {
        private final FluidTank fluidTank;

        public Container(IItemHandlerModifiable inventory, FluidTank fluidTank) {
            super(inventory);
            this.fluidTank = fluidTank;
        }

        public FluidTank getFluidTank() {
            return fluidTank;
        }
    }

    @Override
    public boolean matches(Container container, Level level) {
        if (container.getFluidTank().getFluidAmount() < inputFluid.getAmount() ||
            container.getFluidTank().getFluid().getFluid() != inputFluid.getFluid()) {
            return false;
        }
        return input.test(container.getItem(TeapotMenu.INGREDIENT_SLOT));
    }

    @Override
    public ItemStack assemble(Container p_44001_, RegistryAccess p_267165_) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return null;
    }

    public FluidStack getResultFluid() {
        return outputFluid;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return TealishRecipes.TEAPOT_RECIPES.get();
    }

    @Override
    public RecipeType<?> getType() {
        return TealishRecipes.TEAPOT_RECIPE_TYPE.get();
    }
}
