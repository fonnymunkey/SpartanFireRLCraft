package com.chaosbuffalo.spartanfire.integrations;

import com.github.alexthe666.iceandfire.api.IEntityEffectCapability;
import com.github.alexthe666.iceandfire.api.InFCapabilities;
import com.github.alexthe666.iceandfire.entity.EntityFireDragon;
import com.oblivioussp.spartanweaponry.api.ToolMaterialEx;
import com.oblivioussp.spartanweaponry.api.weaponproperty.WeaponPropertyWithCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class IceSwordWeaponProperty extends WeaponPropertyWithCallback {

    public IceSwordWeaponProperty(String propType, String propModId) {
        super(propType, propModId);
    }

    public float modifyDamageDealt(ToolMaterialEx material, float baseDamage, float initialDamage, DamageSource source, EntityLivingBase attacker, EntityLivingBase target) {
        float mod = 0.0F;
        if (target instanceof EntityFireDragon) {
            mod += 13.5F;
        }
        IEntityEffectCapability capability = InFCapabilities.getEntityEffectCapability(target);
        capability.setFrozen(200);
        target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 2));
        target.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 100, 2));
        target.knockBack(target, 1F, attacker.posX - target.posX, attacker.posZ - target.posZ);
        return baseDamage + mod;
    }
}