package com.chaosbuffalo.spartanfire.integrations;

import com.chaosbuffalo.spartanfire.ForgeConfigHandler;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;

@JEIPlugin
public class JeiPlugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        if(ForgeConfigHandler.general.registerFlamedIcedShockedThrowingRecipes) registry.addRecipes(FireIceLightningThrowingRecipeMaker.getFireIceLightningThrowingRecipes(), VanillaRecipeCategoryUid.CRAFTING);
        if(ForgeConfigHandler.general.registerVenomThrowingRecipes) registry.addRecipes(VenomThrowingRecipeMaker.getVenomThrowingRecipes(), VanillaRecipeCategoryUid.CRAFTING);
    }
}