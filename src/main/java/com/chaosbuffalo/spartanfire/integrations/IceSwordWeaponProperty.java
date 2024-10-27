package com.chaosbuffalo.spartanfire.integrations;

import com.github.alexthe666.iceandfire.api.IEntityEffectCapability;
import com.github.alexthe666.iceandfire.api.InFCapabilities;
import com.github.alexthe666.iceandfire.entity.EntityFireDragon;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class IceSwordWeaponProperty extends SpartanFireWeaponProperty {

    public IceSwordWeaponProperty(String propType, String propModId) {
        super(propType, propModId);
    }

    public float getHitEffectModifier(EntityLivingBase target, EntityLivingBase attacker) {
        float mod = 0F;
        if (target instanceof EntityFireDragon) {
            mod += 13.5F;
        }
        IEntityEffectCapability capability = InFCapabilities.getEntityEffectCapability(target);
        capability.setFrozen(200);
        target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 2));
        target.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 100, 2));
        target.knockBack(target, 1F, attacker.posX - target.posX, attacker.posZ - target.posZ);
        return mod;
    }
}