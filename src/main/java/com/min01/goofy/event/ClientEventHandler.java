package com.min01.goofy.event;

import com.min01.goofy.GoofyCritters;
import com.min01.goofy.entity.GoofyEntities;
import com.min01.goofy.entity.model.ModelEyes;
import com.min01.goofy.entity.model.ModelGestalt;
import com.min01.goofy.entity.model.ModelGestaltArm;
import com.min01.goofy.entity.model.ModelGestaltHand;
import com.min01.goofy.entity.renderer.EyesRenderer;
import com.min01.goofy.entity.renderer.GestaltHandRenderer;
import com.min01.goofy.entity.renderer.GestaltRenderer;
import com.min01.goofy.entity.renderer.NoneRenderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GoofyCritters.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler 
{
    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
    	event.registerLayerDefinition(ModelGestalt.LAYER_LOCATION, ModelGestalt::createBodyLayer);
    	event.registerLayerDefinition(ModelGestaltArm.LAYER_LOCATION, ModelGestaltArm::createBodyLayer);
    	event.registerLayerDefinition(ModelGestaltHand.LAYER_LOCATION, ModelGestaltHand::createBodyLayer);
    	event.registerLayerDefinition(ModelEyes.LAYER_LOCATION, ModelEyes::createBodyLayer);
    }
    
    @SubscribeEvent
    public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
    	event.registerEntityRenderer(GoofyEntities.CAMERA_SHAKE.get(), NoneRenderer::new);
    	
    	event.registerEntityRenderer(GoofyEntities.GESTALT.get(), GestaltRenderer::new);
    	event.registerEntityRenderer(GoofyEntities.GESTALT_HAND.get(), GestaltHandRenderer::new);
    	
    	event.registerEntityRenderer(GoofyEntities.EYES.get(), EyesRenderer::new);
    }
}
