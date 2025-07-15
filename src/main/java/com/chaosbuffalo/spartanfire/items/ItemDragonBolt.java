package com.chaosbuffalo.spartanfire.items;

import com.chaosbuffalo.spartanfire.init.ItemRegistrySFire;
import com.github.alexthe666.iceandfire.entity.projectile.EntityDragonArrow;
import com.oblivioussp.spartanweaponry.item.ItemBolt;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

public class ItemDragonBolt extends SFItem {
    
    private final EntityDragonArrow.Type type;

    public ItemDragonBolt(ResourceLocation registryName, CreativeTabs tab, EntityDragonArrow.Type type) {
        super(registryName, tab);
        this.type = type;
    }

    public EntityDragonArrow.Type getType() {
        return this.type;
    }

    public static ItemStack findDragonBoneBolt(EntityPlayer player) {
        if(isDragonboneBolt(player.getHeldItem(EnumHand.OFF_HAND))) {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if(isDragonboneBolt(player.getHeldItem(EnumHand.MAIN_HAND))) {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else if(isBolt(player.getHeldItem(EnumHand.OFF_HAND))) {
            return ItemStack.EMPTY;
        }
        else if(isBolt(player.getHeldItem(EnumHand.MAIN_HAND))) {
            return ItemStack.EMPTY;
        }
        for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if(isDragonboneBolt(stack)) {
                return stack;
            }
            else if(isBolt(stack)) {
                return ItemStack.EMPTY;
            }
        }
        return ItemStack.EMPTY;
    }

    public static boolean isDragonboneBolt(ItemStack stack) {
        if(stack.isEmpty()) return false;
        return stack.getItem() == ItemRegistrySFire.dragonbone_bolt
                || stack.getItem() == ItemRegistrySFire.dragonbone_bolt_fire
                || stack.getItem() == ItemRegistrySFire.dragonbone_bolt_ice
                || stack.getItem() == ItemRegistrySFire.dragonbone_bolt_lightning;
    }

    private static boolean isBolt(ItemStack stack) {
        if(stack.isEmpty()) return false;
        return stack.getItem() instanceof ItemBolt;
    }
}