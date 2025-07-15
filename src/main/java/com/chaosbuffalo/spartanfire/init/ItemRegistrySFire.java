package com.chaosbuffalo.spartanfire.init;

import com.chaosbuffalo.spartanfire.ForgeConfigHandler;
import com.chaosbuffalo.spartanfire.SpartanFire;
import com.chaosbuffalo.spartanfire.enums.EnumMaterial;
import com.chaosbuffalo.spartanfire.items.ItemDragonBolt;
import com.chaosbuffalo.spartanfire.items.SFItem;
import com.chaosbuffalo.spartanfire.recipes.VenomThrowingRecipes;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.projectile.EntityDragonArrow;
import com.github.alexthe666.iceandfire.item.IafDragonForgeRecipeRegistry;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.recipe.DragonForgeRecipe;
import com.oblivioussp.spartanweaponry.util.ConfigHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Jacob on 7/20/2018.
 */@Mod.EventBusSubscriber
public class ItemRegistrySFire {
    private static final Set<Item> ALL_ITEMS = new HashSet<>();

    private static final Item witherbone_handle = new SFItem(
            new ResourceLocation(SpartanFire.MODID, "witherbone_handle"),
            IceAndFire.TAB_ITEMS
    );
    private static final Item witherbone_pole = new SFItem(
            new ResourceLocation(SpartanFire.MODID, "witherbone_pole"),
            IceAndFire.TAB_ITEMS
    );
    public static final Item dragonbone_bolt = new ItemDragonBolt(
            new ResourceLocation(SpartanFire.MODID, "dragonbone_bolt"),
            IceAndFire.TAB_ITEMS,
            EntityDragonArrow.Type.DEFAULT
    );
    public static final Item dragonbone_bolt_fire = new ItemDragonBolt(
            new ResourceLocation(SpartanFire.MODID, "dragonbone_bolt_fire"),
            IceAndFire.TAB_ITEMS,
            EntityDragonArrow.Type.FIRE
    );
    public static final Item dragonbone_bolt_ice = new ItemDragonBolt(
            new ResourceLocation(SpartanFire.MODID, "dragonbone_bolt_ice"),
            IceAndFire.TAB_ITEMS,
            EntityDragonArrow.Type.ICE
    );
    public static final Item dragonbone_bolt_lightning = new ItemDragonBolt(
            new ResourceLocation(SpartanFire.MODID, "dragonbone_bolt_lightning"),
            IceAndFire.TAB_ITEMS,
            EntityDragonArrow.Type.LIGHTNING
    );

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> ev) {
        // Don't know why this was left out from ice and fire
        OreDictionary.registerOre("ingotDragonbone", IafItemRegistry.dragonbone);
        OreDictionary.registerOre("ingotJungleChitin", IafItemRegistry.myrmex_jungle_chitin);
        OreDictionary.registerOre("ingotDesertChitin", IafItemRegistry.myrmex_desert_chitin);
        OreDictionary.registerOre("ingotWitherShard", IafItemRegistry.wither_shard);

        Set<Item> item_set = new LinkedHashSet<>();

        ALL_ITEMS.add(witherbone_handle);
        ALL_ITEMS.add(witherbone_pole);

        ALL_ITEMS.add(dragonbone_bolt);
        ALL_ITEMS.add(dragonbone_bolt_fire);
        ALL_ITEMS.add(dragonbone_bolt_ice);
        ALL_ITEMS.add(dragonbone_bolt_lightning);

        for (EnumMaterial mat : EnumMaterial.values()){
            if (mat.katana != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.katana,
                        "katana_" + mat.material.getUnlocName());
                item_set.add(mat.katana);
            }
            if (mat.scythe != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.scythe,
                        "scythe_" + mat.material.getUnlocName());
                item_set.add(mat.scythe);
            }
            if (mat.greatsword != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.greatsword,
                        "greatsword_" + mat.material.getUnlocName());
                item_set.add(mat.greatsword);
            }
            if (mat.longsword != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.longsword,
                        "longsword_" + mat.material.getUnlocName());
                item_set.add(mat.longsword);
            }
            if (mat.saber != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.saber,
                        "saber_" + mat.material.getUnlocName());
                item_set.add(mat.saber);
            }
            if (mat.rapier != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.rapier,
                        "rapier_" + mat.material.getUnlocName());
                item_set.add(mat.rapier);
            }
            if (mat.dagger != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.dagger,
                        "dagger_" + mat.material.getUnlocName());
                item_set.add(mat.dagger);
            }
            if (mat.spear != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.spear,
                        "spear_" + mat.material.getUnlocName());
                item_set.add(mat.spear);
            }
            if (mat.pike != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.pike,
                        "pike_" + mat.material.getUnlocName());
                item_set.add(mat.pike);
            }
            if (mat.lance != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.lance,
                        "lance_" + mat.material.getUnlocName());
                item_set.add(mat.lance);
            }
            if (mat.halberd != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.halberd,
                        "halberd_" + mat.material.getUnlocName());
                item_set.add(mat.halberd);
            }
            if (mat.warhammer != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.warhammer,
                        "warhammer_" + mat.material.getUnlocName());
                item_set.add(mat.warhammer);
            }
            if (mat.hammer != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.hammer,
                        "hammer_" + mat.material.getUnlocName());
                item_set.add(mat.hammer);
            }
            if (mat.throwing_axe != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.throwing_axe,
                        "throwing_axe_" + mat.material.getUnlocName());
                item_set.add(mat.throwing_axe);
            }
            if (mat.throwing_knife != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.throwing_knife,
                        "throwing_knife_" + mat.material.getUnlocName());
                item_set.add(mat.throwing_knife);
            }
            if (mat.longbow != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.longbow,
                        "longbow_" + mat.material.getUnlocName());
                item_set.add(mat.longbow);
            }
            if (mat.crossbow != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.crossbow,
                        "crossbow_" + mat.material.getUnlocName());
                item_set.add(mat.crossbow);
            }
            if (mat.javelin != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.javelin,
                        "javelin_" + mat.material.getUnlocName());
                item_set.add(mat.javelin);
            }
            if (mat.battleaxe != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.battleaxe,
                        "battleaxe_" + mat.material.getUnlocName());
                item_set.add(mat.battleaxe);
            }
            if (mat.boomerang != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.boomerang,
                        "boomerang_" + mat.material.getUnlocName());
                item_set.add(mat.boomerang);
            }
            if (mat.mace != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.mace,
                        "mace_" + mat.material.getUnlocName());
                item_set.add(mat.mace);
            }
            if (mat.quarterstaff != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.quarterstaff,
                        "staff_" + mat.material.getUnlocName());
                item_set.add(mat.quarterstaff);
            }
            if (mat.glaive != null){
                ModelRenderRegistrySFire.addItemToRegistry(mat.glaive,
                        "glaive_" + mat.material.getUnlocName());
                item_set.add(mat.glaive);
            }
            if (ConfigHandler.enableExperimentalWeapons && !ConfigHandler.disableParryingDagger){
                //empty method for now till a create parrying dagger method is created
            }
        }
        for (Item it : item_set){
            ev.getRegistry().register(it);
        }
        ALL_ITEMS.forEach(ev.getRegistry()::register);

        registerAllWeaponRecipes(IafDragonForgeRecipeRegistry.FIRE_FORGE_RECIPES, EnumMaterial.DRAGONBONE, IafItemRegistry.fire_dragon_blood, EnumMaterial.FIRE_DRAGONBONE);
        registerAllWeaponRecipes(IafDragonForgeRecipeRegistry.ICE_FORGE_RECIPES, EnumMaterial.DRAGONBONE, IafItemRegistry.ice_dragon_blood, EnumMaterial.ICE_DRAGONBONE);
        registerAllWeaponRecipes(IafDragonForgeRecipeRegistry.LIGHTNING_FORGE_RECIPES, EnumMaterial.DRAGONBONE, IafItemRegistry.lightning_dragon_blood, EnumMaterial.LIGHTNING_DRAGONBONE);
    }

    private static void registerAllWeaponRecipes(List<DragonForgeRecipe> recipes, EnumMaterial input, Item blood, EnumMaterial output) {
        recipes.add(new DragonForgeRecipe(new ItemStack(input.katana), new ItemStack(blood), new ItemStack(output.katana), true));
        recipes.add(new DragonForgeRecipe(new ItemStack(input.scythe), new ItemStack(blood), new ItemStack(output.scythe), true));
        recipes.add(new DragonForgeRecipe(new ItemStack(input.greatsword), new ItemStack(blood), new ItemStack(output.greatsword), true));
        recipes.add(new DragonForgeRecipe(new ItemStack(input.longsword), new ItemStack(blood), new ItemStack(output.longsword), true));
        recipes.add(new DragonForgeRecipe(new ItemStack(input.saber), new ItemStack(blood), new ItemStack(output.saber), true));
        recipes.add(new DragonForgeRecipe(new ItemStack(input.rapier), new ItemStack(blood), new ItemStack(output.rapier), true));
        recipes.add(new DragonForgeRecipe(new ItemStack(input.dagger), new ItemStack(blood), new ItemStack(output.dagger), true));
        recipes.add(new DragonForgeRecipe(new ItemStack(input.spear), new ItemStack(blood), new ItemStack(output.spear), true));
        recipes.add(new DragonForgeRecipe(new ItemStack(input.pike), new ItemStack(blood), new ItemStack(output.pike), true));
        recipes.add(new DragonForgeRecipe(new ItemStack(input.lance), new ItemStack(blood), new ItemStack(output.lance), true));
        recipes.add(new DragonForgeRecipe(new ItemStack(input.halberd), new ItemStack(blood), new ItemStack(output.halberd), true));
        recipes.add(new DragonForgeRecipe(new ItemStack(input.warhammer), new ItemStack(blood), new ItemStack(output.warhammer), true));
        recipes.add(new DragonForgeRecipe(new ItemStack(input.hammer), new ItemStack(blood), new ItemStack(output.hammer), true));
        recipes.add(new DragonForgeRecipe(new ItemStack(input.throwing_axe), new ItemStack(blood), new ItemStack(output.throwing_axe), true));
        recipes.add(new DragonForgeRecipe(new ItemStack(input.throwing_knife), new ItemStack(blood), new ItemStack(output.throwing_knife), true));
        recipes.add(new DragonForgeRecipe(new ItemStack(input.longbow), new ItemStack(blood), new ItemStack(output.longbow), true));
        recipes.add(new DragonForgeRecipe(new ItemStack(input.crossbow), new ItemStack(blood), new ItemStack(output.crossbow), true));
        recipes.add(new DragonForgeRecipe(new ItemStack(input.javelin), new ItemStack(blood), new ItemStack(output.javelin), true));
        recipes.add(new DragonForgeRecipe(new ItemStack(input.battleaxe), new ItemStack(blood), new ItemStack(output.battleaxe), true));
        recipes.add(new DragonForgeRecipe(new ItemStack(input.boomerang), new ItemStack(blood), new ItemStack(output.boomerang), true));
        recipes.add(new DragonForgeRecipe(new ItemStack(input.mace), new ItemStack(blood), new ItemStack(output.mace), true));
        recipes.add(new DragonForgeRecipe(new ItemStack(input.quarterstaff), new ItemStack(blood), new ItemStack(output.quarterstaff), true));
        recipes.add(new DragonForgeRecipe(new ItemStack(input.glaive), new ItemStack(blood), new ItemStack(output.glaive), true));
    }

    @SubscribeEvent
    public static void registerRecipeEvent(RegistryEvent.Register<IRecipe> event) {
        if(ForgeConfigHandler.general.registerVenomThrowingRecipes) event.getRegistry().register(new VenomThrowingRecipes().setRegistryName(new ResourceLocation(SpartanFire.MODID, "venom_throwing")));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ALL_ITEMS.stream()
                .filter(item -> item.getRegistryName() != null)
                .forEach(item ->
                        ModelLoader.setCustomModelResourceLocation(item, 0,
                                new ModelResourceLocation(item.getRegistryName(), "inventory")));
    }
}
