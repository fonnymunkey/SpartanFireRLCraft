package com.chaosbuffalo.spartanfire.integrations;

import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import net.minecraft.entity.EntityLivingBase;

public class FireSwordWeaponProperty extends SpartanFireWeaponProperty {

    public FireSwordWeaponProperty(String propType, String propModId) {
        super(propType, propModId);
    }

    public float getHitEffectModifier(EntityLivingBase target, EntityLivingBase attacker) {
        float mod = 0F;
        if (target instanceof EntityIceDragon) {
            mod += 13.5F;
        }
        target.setFire(5);
        target.knockBack(target, 1F, attacker.posX - target.posX, attacker.posZ - target.posZ);
        return mod;
    }
}
