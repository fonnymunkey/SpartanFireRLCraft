package com.chaosbuffalo.spartanfire.mixin.spartanweaponry;

import com.chaosbuffalo.spartanfire.enums.EnumMaterial;
import com.chaosbuffalo.spartanfire.items.ItemDragonBolt;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemDragonArrow;
import com.github.alexthe666.iceandfire.item.ItemDragonBow;
import com.llamalad7.mixinextras.sugar.Local;
import com.oblivioussp.spartanweaponry.event.EventHandlerCommon;
import com.oblivioussp.spartanweaponry.item.ItemBolt;
import com.oblivioussp.spartanweaponry.item.ItemCrossbow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EventHandlerCommon.class)
public abstract class EventHandlerCommonMixin {
    
    @Inject(
            method = "takeAmmoFromQuiver",
            at = @At(value = "INVOKE", target = "Lnet/minecraftforge/items/IItemHandler;getSlots()I"),
            cancellable = true,
            remap = false
    )
    private static void spartanFire_spartanWeaponryEventHandlerCommon_takeAmmoFromQuiver(EntityPlayer player, ItemStack quiver, EntityEquipmentSlot oppositeHandSlot, CallbackInfo ci, @Local IItemHandler quiverHandler) {
        ItemStack bowStack = oppositeHandSlot.equals(EntityEquipmentSlot.MAINHAND) ? player.getHeldItemOffhand() : player.getHeldItemMainhand();
        if(bowStack.getItem() instanceof ItemCrossbow) {
            boolean dragonCrossbow = bowStack.getItem().equals(EnumMaterial.DRAGONBONE.crossbow)
                    || bowStack.getItem().equals(EnumMaterial.FIRE_DRAGONBONE.crossbow)
                    || bowStack.getItem().equals(EnumMaterial.ICE_DRAGONBONE.crossbow)
                    || bowStack.getItem().equals(EnumMaterial.LIGHTNING_DRAGONBONE.crossbow);
            for(int j = 0; j < quiverHandler.getSlots(); j++) {
                ItemStack boltStack = quiverHandler.extractItem(j, 64, false);
                if(boltStack.getItem() instanceof ItemBolt || boltStack.getItem() instanceof ItemDragonBolt) {
                    if(dragonCrossbow || !ItemDragonBolt.isDragonboneBolt(boltStack)) {
                        player.setItemStackToSlot(oppositeHandSlot, boltStack);
                        break;
                    }
                }
            }
        }
        else if(bowStack.getItem() instanceof ItemBow) {
            boolean dragonBow = bowStack.getItem().equals(IafItemRegistry.dragonbone_bow)
                    || bowStack.getItem().equals(EnumMaterial.DRAGONBONE.longbow)
                    || bowStack.getItem().equals(EnumMaterial.FIRE_DRAGONBONE.longbow)
                    || bowStack.getItem().equals(EnumMaterial.ICE_DRAGONBONE.longbow)
                    || bowStack.getItem().equals(EnumMaterial.LIGHTNING_DRAGONBONE.longbow);
            for(int j = 0; j < quiverHandler.getSlots(); j++) {
                ItemStack arrowStack = quiverHandler.extractItem(j, 64, false);
                if(arrowStack.getItem() instanceof ItemArrow || arrowStack.getItem() instanceof ItemDragonArrow) {
                    if(dragonBow || !ItemDragonBow.isDragonboneArrow(arrowStack)) {
                        player.setItemStackToSlot(oppositeHandSlot, arrowStack);
                        break;
                    }
                }
            }
        }
        ci.cancel();
    }
}