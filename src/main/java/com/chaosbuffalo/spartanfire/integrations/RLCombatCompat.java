package com.chaosbuffalo.spartanfire.integrations;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.oblivioussp.spartanweaponry.api.weaponproperty.WeaponProperty;
import com.oblivioussp.spartanweaponry.item.ItemSwordBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class RLCombatCompat {

    @SubscribeEvent
    public static void modifyAttackDamagePre(RLCombatModifyDamageEvent.Pre event) {
        EntityPlayer player = event.getEntityPlayer();
        Entity target = event.getTarget();
        if (player == null || !(target instanceof EntityLivingBase) || event.getStack().isEmpty()) return;
        Item item = event.getStack().getItem();
        if (item instanceof ItemSwordBase) {
            float mod = 0F;

            List<WeaponProperty> properties = ((ItemSwordBase) item).getAllWeaponProperties();
            for (WeaponProperty property : properties) {
                if (property instanceof SpartanFireWeaponProperty) {
                    mod += ((SpartanFireWeaponProperty) property).getHitEffectModifier((EntityLivingBase)target, player);
                }
            }

            event.setDamageModifier(event.getDamageModifier() + mod);
        }
    }
}
