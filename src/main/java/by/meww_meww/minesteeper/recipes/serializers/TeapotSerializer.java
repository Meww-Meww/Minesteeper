package by.meww_meww.minesteeper.recipes.serializers;

import by.meww_meww.minesteeper.recipes.TeapotRecipe;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public class TeapotSerializer<T extends TeapotRecipe> implements RecipeSerializer<T> {
    private final TeapotSerializer.Factory<T> constructor;
    private final Codec<T> CODEC;


    @Override
    public Codec<T> codec() {
        return CODEC;
    }

    public TeapotSerializer(TeapotSerializer.Factory<T> factory) {
        this.constructor = factory;
        this.CODEC = RecordCodecBuilder.create(instance -> instance.group(
                FluidStack.CODEC.fieldOf("inputFluid").forGetter(recipe -> recipe.inputFluid),
                FluidStack.CODEC.fieldOf("outputFluid").forGetter(recipe -> recipe.outputFluid),
                Ingredient.CODEC.fieldOf("input").forGetter(recipe -> recipe.input)
        ).apply(instance, factory::create));
    }

    @Override
    public @Nullable T fromNetwork(FriendlyByteBuf buffer) {
        final FluidStack inputFluid = buffer.readFluidStack();
        final FluidStack outputFluid = buffer.readFluidStack();
        final Ingredient ingredient = Ingredient.fromNetwork(buffer);

        return this.constructor.create(inputFluid, outputFluid, ingredient);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        buffer.writeFluidStack(recipe.inputFluid);
        buffer.writeFluidStack(recipe.outputFluid);
        recipe.input.toNetwork(buffer);
    }

    public interface Factory<T extends TeapotRecipe> {
        T create(FluidStack inputFluid, FluidStack outputFluid, Ingredient input);
    }
}
