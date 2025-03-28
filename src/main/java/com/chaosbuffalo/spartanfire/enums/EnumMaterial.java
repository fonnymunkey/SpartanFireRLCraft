package com.chaosbuffalo.spartanfire.enums;

import com.chaosbuffalo.spartanfire.SpartanFire;
import com.chaosbuffalo.spartanfire.Utils;
import com.chaosbuffalo.spartanfire.integrations.FireSwordWeaponProperty;
import com.chaosbuffalo.spartanfire.integrations.IceSwordWeaponProperty;
import com.chaosbuffalo.spartanfire.integrations.LightningSwordWeaponProperty;
import com.chaosbuffalo.spartanfire.integrations.MyrmexPoisonSwordProperty;
import com.chaosbuffalo.spartanfire.integrations.MyrmexSwordProperty;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.core.ModItems;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.oblivioussp.spartanweaponry.api.SpartanWeaponryAPI;
import com.oblivioussp.spartanweaponry.api.ToolMaterialEx;
import com.oblivioussp.spartanweaponry.api.weaponproperty.WeaponProperty;
import com.oblivioussp.spartanweaponry.util.ConfigHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public enum EnumMaterial {
    DRAGONBONE("dragonbone", ModItems.boneTools,9867904,14999238,"ingotDragonbone"),
    FIRE_DRAGONBONE("fire_dragonbone", ModItems.fireBoneTools,9867904,14999238, "ingotDragonbone"),
    ICE_DRAGONBONE("ice_dragonbone", ModItems.iceBoneTools,9867904, 14999238, "ingotDragonbone"),
    LIGHTNING_DRAGONBONE("lightning_dragonbone", ModItems.lightningBoneTools,9867904, 14999238, "ingotDragonbone"),
    JUNGLE("jungle", ModItems.myrmexChitin,9867904,14999238,"ingotJungleChitin"),
    DESERT("desert", ModItems.myrmexChitin,9867904,14999238,"ingotDesertChitin"),
    JUNGLE_VENOM("jungle_venom", ModItems.myrmexChitin,9867904,14999238,"ingotJungleChitin"),
    DESERT_VENOM("desert_venom", ModItems.myrmexChitin,9867904,14999238,"ingotDesertChitin");

    EnumMaterial(String name, Item.ToolMaterial material, int color1, int color2, String repairName) {
        this.name = name;
        this.material = Utils.spartanMatFromToolMat(name, material, color1, color2, repairName);

        CreativeTabs tab = IceAndFire.TAB_ITEMS;
        WeaponProperty[] properties = getProperties(name);

        this.katana = SpartanWeaponryAPI.createKatana(this.material, SpartanFire.MODID, tab, properties);
        this.scythe = SpartanWeaponryAPI.createScythe(this.material, SpartanFire.MODID, tab, properties);
        this.greatsword = SpartanWeaponryAPI.createGreatsword(this.material, SpartanFire.MODID, tab, properties);
        this.longsword = SpartanWeaponryAPI.createLongsword(this.material, SpartanFire.MODID, tab, properties);
        this.saber = SpartanWeaponryAPI.createSaber(this.material, SpartanFire.MODID, tab, properties);
        this.rapier = SpartanWeaponryAPI.createRapier(this.material, SpartanFire.MODID, tab, properties);
        this.dagger = SpartanWeaponryAPI.createDagger(this.material, SpartanFire.MODID, tab, properties);
        this.spear = SpartanWeaponryAPI.createSpear(this.material, SpartanFire.MODID, tab, properties);
        this.pike = SpartanWeaponryAPI.createPike(this.material, SpartanFire.MODID, tab, properties);
        this.lance = SpartanWeaponryAPI.createLance(this.material, SpartanFire.MODID, tab, properties);
        this.halberd = SpartanWeaponryAPI.createHalberd(this.material, SpartanFire.MODID, tab, properties);
        this.warhammer = SpartanWeaponryAPI.createWarhammer(this.material, SpartanFire.MODID, tab, properties);
        this.hammer = SpartanWeaponryAPI.createHammer(this.material, SpartanFire.MODID, tab, properties);
        this.throwing_axe = SpartanWeaponryAPI.createThrowingAxe(this.material, SpartanFire.MODID, tab, properties);
        this.throwing_knife = SpartanWeaponryAPI.createThrowingKnife(this.material, SpartanFire.MODID, tab, properties);
        if (!ConfigHandler.woodenLongbowOnly){
            this.longbow = SpartanWeaponryAPI.createLongbow(this.material, SpartanFire.MODID, tab, null);
        } else{
            this.longbow = null;
        }
        if (!ConfigHandler.woodenCrossbowOnly){
            this.crossbow = SpartanWeaponryAPI.createCrossbow(this.material, SpartanFire.MODID, tab, null);
        } else{
            this.crossbow = null;
        }
        this.javelin = SpartanWeaponryAPI.createJavelin(this.material, SpartanFire.MODID, tab, properties);
        this.battleaxe = SpartanWeaponryAPI.createBattleaxe(this.material, SpartanFire.MODID, tab, properties);
        if (!ConfigHandler.woodenBoomerangOnly){
            this.boomerang = SpartanWeaponryAPI.createBoomerang(this.material, SpartanFire.MODID, tab, properties);
        } else{
            this.boomerang = null;
        }
        this.mace = SpartanWeaponryAPI.createMace(this.material, SpartanFire.MODID, tab, properties);
        this.quarterstaff = SpartanWeaponryAPI.createQuarterstaff(this.material, SpartanFire.MODID, tab, properties);
        this.glaive = SpartanWeaponryAPI.createGlaive(this.material, SpartanFire.MODID, tab, properties);
    }

    public final String name;
    public final ToolMaterialEx material;
    public final Item katana;
    public final Item scythe;
    public final Item greatsword;
    public final Item longsword;
    public final Item saber;
    public final Item rapier;
    public final Item dagger;
    public final Item spear;
    public final Item pike;
    public final Item lance;
    public final Item halberd;
    public final Item warhammer;
    public final Item hammer;
    public final Item throwing_axe;
    public final Item throwing_knife;
    public final Item longbow;
    public final Item crossbow;
    public final Item javelin;
    public final Item battleaxe;
    public final Item boomerang;
    public final Item mace;
    public final Item quarterstaff;
    public final Item glaive;

    private WeaponProperty[] getProperties(String name) {
        List<WeaponProperty> properties = new ArrayList<>();
        switch (name){
            case "fire_dragonbone":
                properties.add(new FireSwordWeaponProperty(this.name, SpartanFire.MODID));
                break;
            case "ice_dragonbone":
                properties.add(new IceSwordWeaponProperty(this.name, SpartanFire.MODID));
                break;
            case "lightning_dragonbone":
                properties.add(new LightningSwordWeaponProperty(this.name, SpartanFire.MODID));
                break;
            case "desert":
            case "desert_venom":
                properties.add(new MyrmexSwordProperty(this.name, SpartanFire.MODID));
                if (name.equals("desert_venom")){
                    properties.add(new MyrmexPoisonSwordProperty(this.name, SpartanFire.MODID));
                }
                break;
            case "jungle":
            case "jungle_venom":
                properties.add(new MyrmexSwordProperty(this.name, SpartanFire.MODID));
                if (name.equals("jungle_venom")){
                    properties.add(new MyrmexPoisonSwordProperty(this.name, SpartanFire.MODID));
                }
        }
        return properties.toArray(new WeaponProperty[0]);
    }
}
