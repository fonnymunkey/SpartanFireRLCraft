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

public class VenomThrowingRecipes extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    private static final Map<Item, Item> weaponMapVenom;

    // :(
    static {
        Map<Item, Item> tempVenomMap = new HashMap<>();

        tempVenomMap.put(Item.getByNameOrId("spartanfire:throwing_knife_desert"), Item.getByNameOrId("spartanfire:throwing_knife_desert_venom"));
        tempVenomMap.put(Item.getByNameOrId("spartanfire:javelin_desert"), Item.getByNameOrId("spartanfire:javelin_desert_venom"));
        tempVenomMap.put(Item.getByNameOrId("spartanfire:boomerang_desert"), Item.getByNameOrId("spartanfire:boomerang_desert_venom"));

        tempVenomMap.put(Item.getByNameOrId("spartanfire:throwing_knife_jungle"), Item.getByNameOrId("spartanfire:throwing_knife_jungle_venom"));
        tempVenomMap.put(Item.getByNameOrId("spartanfire:javelin_jungle"), Item.getByNameOrId("spartanfire:javelin_jungle_venom"));
        tempVenomMap.put(Item.getByNameOrId("spartanfire:boomerang_jungle"), Item.getByNameOrId("spartanfire:boomerang_jungle_venom"));

        weaponMapVenom = Collections.unmodifiableMap(tempVenomMap);
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) { return validInput(inv) != null; }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        Integer[] slots = validInput(inv);
        if(slots==null) return ItemStack.EMPTY;

        return new ItemStack(weaponMapVenom.get(inv.getStackInSlot(slots[1]).getItem()));
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

            if(itemStack.getItem() == ModItems.myrmex_stinger) bloodSlot = i;
            else if(weaponMapVenom.containsKey(itemStack.getItem()) && !itemStack.isItemDamaged()){//Shouldn't really need to check both maps, since everything will be in both maps
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