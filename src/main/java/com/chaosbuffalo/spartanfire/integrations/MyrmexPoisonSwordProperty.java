package com.chaosbuffalo.spartanfire.integrations;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class MyrmexPoisonSwordProperty extends SpartanFireWeaponProperty {

    public MyrmexPoisonSwordProperty(String propType, String propModId) {
        super(propType, propModId);
    }

    public float getHitEffectModifier(EntityLivingBase target, EntityLivingBase attacker) {
        target.addPotionEffect(new PotionEffect(MobEffects.POISON, 200, 2));
        return 0F;
    }
}

