package by.tealishteam.tealish.recipes;

import by.tealishteam.tealish.menus.TeapotMenu;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;

public class TeapotRecipe implements Recipe<TeapotRecipe.Container> {

    private final FluidStack inputFluid;
    private final FluidStack outputFluid;
    private final Ingredient input;

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
            !container.getFluidTank().getFluid().isFluidEqual(inputFluid)) {
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

    @Override
    public RecipeSerializer<?> getSerializer() {
        return TealishRecipes.TEAPOT_RECIPES.get();
    }

    @Override
    public RecipeType<?> getType() {
        return TealishRecipes.TEAPOT_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<TeapotRecipe> {
        private static final Codec<TeapotRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                FluidStack.CODEC.fieldOf("inputFluid").forGetter(recipe -> recipe.inputFluid),
                FluidStack.CODEC.fieldOf("outputFluid").forGetter(recipe -> recipe.outputFluid),
                Ingredient.CODEC.fieldOf("input").forGetter(recipe -> recipe.input)
        ).apply(instance, TeapotRecipe::new));

        @Override
        public Codec<TeapotRecipe> codec() {
            return CODEC;
        }

        @Override
        public @Nullable TeapotRecipe fromNetwork(FriendlyByteBuf buffer) {
            final FluidStack inputFluid = buffer.readFluidStack();
            final FluidStack outputFluid = buffer.readFluidStack();
            final Ingredient ingredient = Ingredient.fromNetwork(buffer);

            return new TeapotRecipe(inputFluid, outputFluid, ingredient);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, TeapotRecipe recipe) {
            buffer.writeFluidStack(recipe.inputFluid);
            buffer.writeFluidStack(recipe.outputFluid);
            recipe.input.toNetwork(buffer);
        }
    }
}
