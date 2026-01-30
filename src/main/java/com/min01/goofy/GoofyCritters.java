package com.min01.goofy;

import com.min01.goofy.block.GoofyBlocks;
import com.min01.goofy.config.GoofyConfig;
import com.min01.goofy.effect.GoofyEffects;
import com.min01.goofy.entity.GoofyEntities;
import com.min01.goofy.item.GoofyItems;
import com.min01.goofy.misc.GoofyCreativeTabs;
import com.min01.goofy.misc.GoofyEntityDataSerializers;
import com.min01.goofy.network.GoofyNetwork;
import com.min01.goofy.sound.GoofySounds;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GoofyCritters.MODID)
public class GoofyCritters
{
	public static final String MODID = "goofycritters";
	
	public GoofyCritters(FMLJavaModLoadingContext ctx) 
	{
		IEventBus bus = ctx.getModEventBus();

		GoofyCreativeTabs.CREATIVE_MODE_TAB.register(bus);
		GoofyItems.ITEMS.register(bus);
		GoofyEntities.ENTITY_TYPES.register(bus);
		GoofyEntityDataSerializers.SERIALIZERS.register(bus);
		GoofyBlocks.BLOCKS.register(bus);
		GoofyBlocks.BLOCK_ENTITIES.register(bus);
		GoofySounds.SOUNDS.register(bus);
		GoofyEffects.EFFECTS.register(bus);
		
		GoofyNetwork.registerMessages();
		ctx.registerConfig(Type.COMMON, GoofyConfig.CONFIG_SPEC, "goofy-critters.toml");
	}
}
