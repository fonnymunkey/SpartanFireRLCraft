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

    public void onHitEntity(ToolMaterialEx material, ItemStack stack, EntityLivingBase target, EntityLivingBase attacker, Entity projectile) {
        if (target instanceof EntityFireDragon) {
            target.attackEntityFrom(DamageSource.DROWN, 13.5F);
        }
        IEntityEffectCapability capability = InFCapabilities.getEntityEffectCapability(target);
        capability.setFrozen(200);
        target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 2));
        target.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 100, 2));
        target.knockBack(target, 1F, attacker.posX - target.posX, attacker.posZ - target.posZ);
    }
}