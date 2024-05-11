package by.meww_meww.minesteeper.recipes;

import by.meww_meww.minesteeper.items.MinesteeperItems;
import com.google.gson.JsonObject;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import static by.meww_meww.minesteeper.Minesteeper.MODID;

public class MinesteeperRecipeProvider extends RecipeProvider {
    public MinesteeperRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        output.accept(new FinishedTeapotRecipe(new ResourceLocation(MODID, "tea"), Ingredient.of(MinesteeperItems.LOOSE_LEAF_TEA.get()), new FluidStack(Fluids.WATER, 1000), new FluidStack(Fluids.WATER, 1000)));
    }

    protected static class FinishedTeapotRecipe implements FinishedRecipe {

        private final ResourceLocation id;
        private final Ingredient ingredient;
        private final FluidStack inputFluid;
        private final FluidStack outputFluid;

        protected FinishedTeapotRecipe(ResourceLocation id, Ingredient ingredient, FluidStack inputFluid, FluidStack outputFluid) {
            this.id = id;
            this.ingredient = ingredient;
            this.inputFluid = inputFluid;
            this.outputFluid = outputFluid;
        }

        public void serializeRecipeData(JsonObject json) {
            json.add("input", ingredient.toJson(true));

            JsonObject fluidJson = new JsonObject();
            fluidJson.addProperty("inputFluid", ForgeRegistries.FLUIDS.getKey(inputFluid.getFluid()).toString());
            fluidJson.addProperty("outputFluid", ForgeRegistries.FLUIDS.getKey(outputFluid.getFluid()).toString());
            fluidJson.addProperty("amount", inputFluid.getAmount());

            json.add("fluid", fluidJson);
        }

        @Override
        public ResourceLocation id() {
            return id;
        }

        @Override
        public RecipeSerializer<?> type() {
            return null;
        }

        @Nullable
        @Override
        public AdvancementHolder advancement() {
            return null;
        }
    }
}
