package com.chaosbuffalo.spartanfire.integrations;

import com.github.alexthe666.iceandfire.api.ChainLightningUtils;
import com.github.alexthe666.iceandfire.entity.EntityFireDragon;
import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import net.minecraft.entity.EntityLivingBase;

public class LightningSwordWeaponProperty extends SpartanFireWeaponProperty {

    public LightningSwordWeaponProperty(String propType, String propModId) {
        super(propType, propModId);
    }

    public float getHitEffectModifier(EntityLivingBase target, EntityLivingBase attacker) {
        float mod = 0F;
        if (target instanceof EntityFireDragon || target instanceof EntityIceDragon) {
            mod += 6.75F;
        }
        ChainLightningUtils.createChainLightningFromTarget(target.world, target, attacker);
        target.knockBack(target, 1F, attacker.posX - target.posX, attacker.posZ - target.posZ);
        return mod;
    }
}