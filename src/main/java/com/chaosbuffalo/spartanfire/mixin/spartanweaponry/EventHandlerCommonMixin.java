package com.chaosbuffalo.spartanfire.mixin.spartanweaponry;

import com.chaosbuffalo.spartanfire.enums.EnumMaterial;
import com.chaosbuffalo.spartanfire.items.ItemDragonBolt;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemDragonBow;
import com.oblivioussp.spartanweaponry.event.EventHandlerCommon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EventHandlerCommon.class)
public class EventHandlerCommonMixin {

    @Inject(
            method = "takeAmmoFromQuiver",
            at = @At("HEAD"),
            remap = false,
            cancellable = true)
    private static void takeAmmoFromQuiver(EntityPlayer player, ItemStack quiver, EntityEquipmentSlot oppositeHandSlot, CallbackInfo ci) {
        if (!quiver.isEmpty() && quiver.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)){
            IItemHandler quiverHandler = quiver.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            ItemStack arrowStack;
            for(int j = 0; j < quiverHandler.getSlots(); j++){
                arrowStack = quiverHandler.extractItem(j, 64, false);
                if (ItemDragonBow.isDragonboneArrow(arrowStack)){
                    ItemStack bow;
                    if (oppositeHandSlot.equals(EntityEquipmentSlot.MAINHAND)){
                        bow = player.getHeldItemOffhand();
                    } else{
                        bow = player.getHeldItemMainhand();
                    }
                    if (bow.getItem().equals(IafItemRegistry.dragonbone_bow)
                            || bow.getItem().equals(EnumMaterial.DRAGONBONE.longbow)
                            || bow.getItem().equals(EnumMaterial.FIRE_DRAGONBONE.longbow)
                            || bow.getItem().equals(EnumMaterial.ICE_DRAGONBONE.longbow)
                            || bow.getItem().equals(EnumMaterial.LIGHTNING_DRAGONBONE.longbow)){
                        player.setItemStackToSlot(oppositeHandSlot, arrowStack);
                        break;
                    } else{
                        quiverHandler.insertItem(j, arrowStack, false);
                    }
                } else if (ItemDragonBolt.isDragonboneBolt(arrowStack)){
                    ItemStack crossbow;
                    if (oppositeHandSlot.equals(EntityEquipmentSlot.MAINHAND)){
                        crossbow = player.getHeldItemOffhand();
                    } else{
                        crossbow = player.getHeldItemMainhand();
                    }
                    if (crossbow.getItem().equals(EnumMaterial.DRAGONBONE.crossbow)
                            || crossbow.getItem().equals(EnumMaterial.FIRE_DRAGONBONE.crossbow)
                            || crossbow.getItem().equals(EnumMaterial.ICE_DRAGONBONE.crossbow)
                            || crossbow.getItem().equals(EnumMaterial.LIGHTNING_DRAGONBONE.crossbow)){
                        player.setItemStackToSlot(oppositeHandSlot, arrowStack);
                        break;
                    } else{
                        quiverHandler.insertItem(j, arrowStack, false);
                    }
                } else if(!arrowStack.isEmpty()){
                    player.setItemStackToSlot(oppositeHandSlot, arrowStack);
                    break;
                }
            }
        }
        ci.cancel();
    }
}
