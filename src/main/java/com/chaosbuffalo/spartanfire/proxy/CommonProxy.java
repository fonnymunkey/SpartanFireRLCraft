package com.chaosbuffalo.spartanfire.proxy;

import com.chaosbuffalo.spartanfire.SpartanFire;
import com.chaosbuffalo.spartanfire.entity.EntityDragonBolt;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod.EventBusSubscriber
public class CommonProxy
{
	public void init(FMLInitializationEvent event) {
	}

	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
		EntityRegistry.registerModEntity(
				new ResourceLocation(SpartanFire.MODID, "bolt_dragonbone"),
				EntityDragonBolt.class,
				SpartanFire.MODID + ":bolt_dragonbone",
				0,
				SpartanFire.INSTANCE,
				64,
				1,
				true
		);
	}
}
