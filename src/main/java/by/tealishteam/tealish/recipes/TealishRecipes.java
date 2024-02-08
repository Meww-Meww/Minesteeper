package by.tealishteam.tealish.recipes;

import net.minecraft.world.item.crafting.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static by.tealishteam.tealish.Tealish.MODID;

public class TealishRecipes {
    private static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);

    public static final RegistryObject<RecipeSerializer<LooseLeafTeaRecipe>> LOOSE_LEAF_TEA = RECIPES.register("loose_leaf_tea_recipe",
            () -> new SimpleCraftingRecipeSerializer<>(LooseLeafTeaRecipe::new)
    );

    public static void register(IEventBus modEventBus){
        RECIPES.register(modEventBus);
    }
}
