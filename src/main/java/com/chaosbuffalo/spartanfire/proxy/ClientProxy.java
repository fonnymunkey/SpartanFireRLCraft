package com.chaosbuffalo.spartanfire.proxy;

import com.chaosbuffalo.spartanfire.client.render.RenderFactoryDragonBolt;
import com.chaosbuffalo.spartanfire.entity.EntityDragonBolt;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit() {
		RenderingRegistry.registerEntityRenderingHandler(
				EntityDragonBolt.class, new RenderFactoryDragonBolt<>());
	}
}