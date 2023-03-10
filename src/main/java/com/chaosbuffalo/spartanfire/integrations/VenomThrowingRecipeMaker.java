package com.chaosbuffalo.spartanfire.integrations;

import com.github.alexthe666.iceandfire.core.ModItems;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public final class VenomThrowingRecipeMaker {

    public static List<ShapelessCatalystRecipeWrapper> getVenomThrowingRecipes() {
        List<ShapelessCatalystRecipeWrapper> recipes = new ArrayList<>();
        recipes.add(new ShapelessCatalystRecipeWrapper(Item.getByNameOrId("spartanfire:throwing_knife_desert"), Item.getByNameOrId("spartanfire:throwing_knife_desert_venom"), ModItems.myrmex_stinger));
        recipes.add(new ShapelessCatalystRecipeWrapper(Item.getByNameOrId("spartanfire:javelin_desert"), Item.getByNameOrId("spartanfire:javelin_desert_venom"), ModItems.myrmex_stinger));
        recipes.add(new ShapelessCatalystRecipeWrapper(Item.getByNameOrId("spartanfire:boomerang_desert"), Item.getByNameOrId("spartanfire:boomerang_desert_venom"), ModItems.myrmex_stinger));
        recipes.add(new ShapelessCatalystRecipeWrapper(Item.getByNameOrId("spartanfire:throwing_knife_jungle"), Item.getByNameOrId("spartanfire:throwing_knife_jungle_venom"), ModItems.myrmex_stinger));
        recipes.add(new ShapelessCatalystRecipeWrapper(Item.getByNameOrId("spartanfire:javelin_jungle"), Item.getByNameOrId("spartanfire:javelin_jungle_venom"), ModItems.myrmex_stinger));
        recipes.add(new ShapelessCatalystRecipeWrapper(Item.getByNameOrId("spartanfire:boomerang_jungle"), Item.getByNameOrId("spartanfire:boomerang_jungle_venom"), ModItems.myrmex_stinger));
        return recipes;
    }
}