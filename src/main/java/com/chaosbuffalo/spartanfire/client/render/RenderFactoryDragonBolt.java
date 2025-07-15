package com.chaosbuffalo.spartanfire.client.render;

import com.chaosbuffalo.spartanfire.entity.EntityDragonBolt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFactoryDragonBolt<T extends EntityDragonBolt> implements IRenderFactory<T> {
	
	public RenderFactoryDragonBolt() {
	
	}
	
	@Override
	public Render<? super T> createRenderFor(RenderManager manager) {
		return new RenderDragonBolt(manager, Minecraft.getMinecraft().getRenderItem());
	}
}