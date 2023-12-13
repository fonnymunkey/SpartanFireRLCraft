package com.chaosbuffalo.spartanfire.integrations;

import com.github.alexthe666.iceandfire.core.ModItems;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public final class FireIceLightningThrowingRecipeMaker {

    public static List<ShapelessCatalystRecipeWrapper> getFireIceLightningThrowingRecipes() {
        List<ShapelessCatalystRecipeWrapper> recipes = new ArrayList<>();

        // Flamed Dragonbone Recipes
        recipes.add(new ShapelessCatalystRecipeWrapper(Item.getByNameOrId("spartanfire:throwing_axe_dragonbone"), Item.getByNameOrId("spartanfire:throwing_axe_fire_dragonbone"), ModItems.fire_dragon_blood));
        recipes.add(new ShapelessCatalystRecipeWrapper(Item.getByNameOrId("spartanfire:throwing_knife_dragonbone"), Item.getByNameOrId("spartanfire:throwing_knife_fire_dragonbone"), ModItems.fire_dragon_blood));
        recipes.add(new ShapelessCatalystRecipeWrapper(Item.getByNameOrId("spartanfire:javelin_dragonbone"), Item.getByNameOrId("spartanfire:javelin_fire_dragonbone"), ModItems.fire_dragon_blood));
        recipes.add(new ShapelessCatalystRecipeWrapper(Item.getByNameOrId("spartanfire:boomerang_dragonbone"), Item.getByNameOrId("spartanfire:boomerang_fire_dragonbone"), ModItems.fire_dragon_blood));

        // Iced Dragonbone Recipes
        recipes.add(new ShapelessCatalystRecipeWrapper(Item.getByNameOrId("spartanfire:throwing_axe_dragonbone"), Item.getByNameOrId("spartanfire:throwing_axe_ice_dragonbone"), ModItems.ice_dragon_blood));
        recipes.add(new ShapelessCatalystRecipeWrapper(Item.getByNameOrId("spartanfire:throwing_knife_dragonbone"), Item.getByNameOrId("spartanfire:throwing_knife_ice_dragonbone"), ModItems.ice_dragon_blood));
        recipes.add(new ShapelessCatalystRecipeWrapper(Item.getByNameOrId("spartanfire:javelin_dragonbone"), Item.getByNameOrId("spartanfire:javelin_ice_dragonbone"), ModItems.ice_dragon_blood));
        recipes.add(new ShapelessCatalystRecipeWrapper(Item.getByNameOrId("spartanfire:boomerang_dragonbone"), Item.getByNameOrId("spartanfire:boomerang_ice_dragonbone"), ModItems.ice_dragon_blood));

        // Shocked Dragonbone Recipes
        recipes.add(new ShapelessCatalystRecipeWrapper(Item.getByNameOrId("spartanfire:throwing_axe_dragonbone"), Item.getByNameOrId("spartanfire:throwing_axe_lightning_dragonbone"), ModItems.lightning_dragon_blood));
        recipes.add(new ShapelessCatalystRecipeWrapper(Item.getByNameOrId("spartanfire:throwing_knife_dragonbone"), Item.getByNameOrId("spartanfire:throwing_knife_lightning_dragonbone"), ModItems.lightning_dragon_blood));
        recipes.add(new ShapelessCatalystRecipeWrapper(Item.getByNameOrId("spartanfire:javelin_dragonbone"), Item.getByNameOrId("spartanfire:javelin_lightning_dragonbone"), ModItems.lightning_dragon_blood));
        recipes.add(new ShapelessCatalystRecipeWrapper(Item.getByNameOrId("spartanfire:boomerang_dragonbone"), Item.getByNameOrId("spartanfire:boomerang_lightning_dragonbone"), ModItems.lightning_dragon_blood));

        return recipes;
    }
}
