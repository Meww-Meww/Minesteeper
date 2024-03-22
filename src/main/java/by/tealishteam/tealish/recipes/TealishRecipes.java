package by.tealishteam.tealish.recipes;

import by.tealishteam.tealish.recipes.serializers.TeapotSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static by.tealishteam.tealish.Tealish.MODID;

public class TealishRecipes {
    private static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, MODID);

    public static final RegistryObject<RecipeSerializer<LooseLeafTeaRecipe>> LOOSE_LEAF_TEA = RECIPES.register("loose_leaf_tea_recipe",
            () -> new SimpleCraftingRecipeSerializer<>(LooseLeafTeaRecipe::new)
    );

    public static final RegistryObject<TeapotSerializer<TeapotRecipe>> TEAPOT_RECIPES = RECIPES.register("teapot", () -> new TeapotSerializer<>(TeapotRecipe::new));
    public static final RegistryObject<RecipeType<TeapotRecipe>> TEAPOT_RECIPE_TYPE = RECIPE_TYPES.register("teapot", () -> RecipeType.simple(new ResourceLocation(MODID, "teapot")));

    public static final RegistryObject<TeapotSerializer<MilkyTeaRecipe>> MILKY_TEA_RECIPE = RECIPES.register("milky_tea_recipe", () -> new TeapotSerializer<>(MilkyTeaRecipe::new));

    public static void register(IEventBus modEventBus){
        RECIPES.register(modEventBus);
        RECIPE_TYPES.register(modEventBus);
    }
}
