package io.github.darealturtywurty.ancientology.core.init;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.common.recipes.JumprasherRecipe;
import io.github.darealturtywurty.ancientology.core.util.registry.recipe.RecipeDeferredRegister;
import io.github.darealturtywurty.ancientology.core.util.registry.recipe.RecipeRegistryObject;

public final class RecipeInit {

    public static final RecipeDeferredRegister RECIPES = RecipeDeferredRegister.create(Ancientology.MODID);

    public static final RecipeRegistryObject<JumprasherRecipe> JUMPRASHER = RECIPES.register("jumprasher",
            JumprasherRecipe.Serializer::new);

}
