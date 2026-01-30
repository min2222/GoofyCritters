package com.min01.goofy.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;

public class GoofyConfig 
{
	public static final GoofyConfig CONFIG;
	public static final ForgeConfigSpec CONFIG_SPEC;

	public static ForgeConfigSpec.BooleanValue cameraShakes;
	
    static 
    {
    	Pair<GoofyConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(GoofyConfig::new);
    	CONFIG = pair.getLeft();
    	CONFIG_SPEC = pair.getRight();
    }
	
    public GoofyConfig(ForgeConfigSpec.Builder config) 
    {
    	config.push("Client Settings");
    	cameraShakes = config.comment("disable/enable camera shakes in various place").define("cameraShakes", true);
        config.pop();
    }
}
