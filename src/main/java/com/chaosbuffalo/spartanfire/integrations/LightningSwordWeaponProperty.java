package com.chaosbuffalo.spartanfire.integrations;

import com.github.alexthe666.iceandfire.api.ChainLightningUtils;
import com.github.alexthe666.iceandfire.entity.EntityFireDragon;
import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import com.oblivioussp.spartanweaponry.api.ToolMaterialEx;
import com.oblivioussp.spartanweaponry.api.weaponproperty.WeaponPropertyWithCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class LightningSwordWeaponProperty extends WeaponPropertyWithCallback {

    public LightningSwordWeaponProperty(String propType, String propModId) {
        super(propType, propModId);
    }

    public float modifyDamageDealt(ToolMaterialEx material, float baseDamage, float initialDamage, DamageSource source, EntityLivingBase attacker, EntityLivingBase target) {
        float mod = 0.0F;
        if (target instanceof EntityFireDragon || target instanceof EntityIceDragon) {
            mod += 6.75F;
        }
        ChainLightningUtils.createChainLightningFromTarget(target.world, target, attacker);
        target.knockBack(target, 1F, attacker.posX - target.posX, attacker.posZ - target.posZ);
        return baseDamage + mod;
    }
}