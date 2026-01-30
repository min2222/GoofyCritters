package com.min01.goofy.entity.renderer.layer;

import com.min01.goofy.entity.living.EntityGestalt;
import com.min01.goofy.entity.model.ModelGestalt;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.RenderLayerParent;

public class GestaltRiderLayer extends RiderLayer<EntityGestalt, ModelGestalt>
{
	public GestaltRiderLayer(RenderLayerParent<EntityGestalt, ModelGestalt> pRenderer) 
	{
		super(pRenderer);
	}
	
	@Override
	public void transform(PoseStack stack)
	{
		this.getParentModel().root.translateAndRotate(stack);
	}
}
