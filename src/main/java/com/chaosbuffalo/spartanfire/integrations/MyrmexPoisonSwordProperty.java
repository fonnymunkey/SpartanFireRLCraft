package com.chaosbuffalo.spartanfire.integrations;

import com.oblivioussp.spartanweaponry.api.ToolMaterialEx;
import com.oblivioussp.spartanweaponry.api.weaponproperty.WeaponPropertyWithCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;


public class MyrmexPoisonSwordProperty extends WeaponPropertyWithCallback {

    public MyrmexPoisonSwordProperty(String propType, String propModId) {
        super(propType, propModId);
    }

    public float modifyDamageDealt(ToolMaterialEx material, float baseDamage, float initialDamage, DamageSource source, EntityLivingBase attacker, EntityLivingBase target) {
        target.addPotionEffect(new PotionEffect(MobEffects.POISON, 200, 2));
        return baseDamage;
    }
}

