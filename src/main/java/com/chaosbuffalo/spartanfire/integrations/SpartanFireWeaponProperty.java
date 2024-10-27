package com.chaosbuffalo.spartanfire.integrations;

import com.oblivioussp.spartanweaponry.api.ToolMaterialEx;
import com.oblivioussp.spartanweaponry.api.weaponproperty.WeaponPropertyWithCallback;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public abstract class SpartanFireWeaponProperty extends WeaponPropertyWithCallback {

    public SpartanFireWeaponProperty(String propType, String propModId) {
        super(propType, propModId);
    }

    public float modifyDamageDealt(ToolMaterialEx material, float baseDamage, float initialDamage, DamageSource source, EntityLivingBase attacker, EntityLivingBase target) {
        if (!source.isProjectile() && CompatLoadUtil.isRLCombatLoaded()) return baseDamage;
        return baseDamage + getHitEffectModifier(target, attacker);
    }

    public abstract float getHitEffectModifier(EntityLivingBase target, EntityLivingBase attacker);
}
