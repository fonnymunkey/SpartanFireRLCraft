package com.chaosbuffalo.spartanfire.recipes;

import com.github.alexthe666.iceandfire.core.ModItems;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.*;
public class FireIceThrowingRecipes extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    private static final Map<Item, Item> weaponMapFire;
    private static final Map<Item, Item> weaponMapIce;

    // :(
    static {
        Map<Item, Item> tempFireMap = new HashMap<>();
        Map<Item, Item> tempIceMap = new HashMap<>();

        tempFireMap.put(Item.getByNameOrId("spartanfire:throwing_axe_dragonbone"), Item.getByNameOrId("spartanfire:throwing_axe_fire_dragonbone"));
        tempFireMap.put(Item.getByNameOrId("spartanfire:throwing_knife_dragonbone"), Item.getByNameOrId("spartanfire:throwing_knife_fire_dragonbone"));
        tempFireMap.put(Item.getByNameOrId("spartanfire:javelin_dragonbone"), Item.getByNameOrId("spartanfire:javelin_fire_dragonbone"));
        tempFireMap.put(Item.getByNameOrId("spartanfire:boomerang_dragonbone"), Item.getByNameOrId("spartanfire:boomerang_fire_dragonbone"));

        tempIceMap.put(Item.getByNameOrId("spartanfire:throwing_axe_dragonbone"), Item.getByNameOrId("spartanfire:throwing_axe_ice_dragonbone"));
        tempIceMap.put(Item.getByNameOrId("spartanfire:throwing_knife_dragonbone"), Item.getByNameOrId("spartanfire:throwing_knife_ice_dragonbone"));
        tempIceMap.put(Item.getByNameOrId("spartanfire:javelin_dragonbone"), Item.getByNameOrId("spartanfire:javelin_ice_dragonbone"));
        tempIceMap.put(Item.getByNameOrId("spartanfire:boomerang_dragonbone"), Item.getByNameOrId("spartanfire:boomerang_ice_dragonbone"));

        weaponMapFire = Collections.unmodifiableMap(tempFireMap);
        weaponMapIce = Collections.unmodifiableMap(tempIceMap);
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) { return validInput(inv) != null; }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        Integer[] slots = validInput(inv);
        if(slots==null) return ItemStack.EMPTY;

        ItemStack stackInput = inv.getStackInSlot(slots[1]);
        ItemStack output;
        if(inv.getStackInSlot(slots[0]).getItem() == ModItems.fire_dragon_blood) output = new ItemStack(weaponMapFire.get(stackInput.getItem()));
        else output = new ItemStack(weaponMapIce.get(stackInput.getItem()));

        return output;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Nullable
    private Integer[] validInput(InventoryCrafting inv) {
        int numStacks = 0;
        int bloodSlot = -1;
        int weaponSlot = -1;
        List<Integer> occupiedSlots = new ArrayList<>();

        for(int i = 0; i < inv.getSizeInventory(); i++) {
            if (!inv.getStackInSlot(i).isEmpty()) {
                numStacks++;
                occupiedSlots.add(i);
            }
        }
        if(numStacks != 2) return null;

        for(int i : occupiedSlots) {
            ItemStack itemStack = inv.getStackInSlot(i);

            if(itemStack.getItem() == ModItems.fire_dragon_blood || itemStack.getItem() == ModItems.ice_dragon_blood) bloodSlot = i;
            else if(weaponMapFire.containsKey(itemStack.getItem()) && !itemStack.isItemDamaged()){//Shouldn't really need to check both maps, since everything will be in both maps
                NBTTagCompound compound = itemStack.getTagCompound();
                if(compound == null || compound.getByte("Original") != 1) return null;//Fix duping ammo
                weaponSlot = i;
            }
            else return null;
        }
        Integer[] slots = new Integer[2];
        slots[0] = bloodSlot;
        slots[1] = weaponSlot;
        return (bloodSlot != -1 && weaponSlot != -1) ? slots : null;
    }
}
