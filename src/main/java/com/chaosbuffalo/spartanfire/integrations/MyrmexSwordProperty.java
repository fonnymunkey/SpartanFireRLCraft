package com.chaosbuffalo.spartanfire.integrations;

import com.github.alexthe666.iceandfire.entity.EntityDeathWorm;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;

public class MyrmexSwordProperty extends SpartanFireWeaponProperty {

    public MyrmexSwordProperty(String propType, String propModId) {
        super(propType, propModId);
    }

    public float getHitEffectModifier(EntityLivingBase target, EntityLivingBase attacker) {
        if (target.getCreatureAttribute() != EnumCreatureAttribute.ARTHROPOD) {
            return 4F;
        }
        if (target instanceof EntityDeathWorm) {
            return 4F;
        }
        return 0F;
    }
}
