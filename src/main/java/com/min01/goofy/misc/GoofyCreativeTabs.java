package com.min01.goofy.misc;

import com.min01.goofy.GoofyCritters;
import com.min01.goofy.item.GoofyItems;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class GoofyCreativeTabs 
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GoofyCritters.MODID);

    public static final RegistryObject<CreativeModeTab> GOOFY_CRITTERS = CREATIVE_MODE_TAB.register(GoofyCritters.MODID, () -> CreativeModeTab.builder()
    		.title(Component.translatable("itemGroup.goofycritters"))
    		.icon(() -> new ItemStack(GoofyItems.GOOFY_MASK.get()))
    		.displayItems((enabledFeatures, output) -> 
    		{
    			for(RegistryObject<Item> item : GoofyItems.ITEMS.getEntries())
    			{
    				output.accept(item.get());
    			}
    		}).build());
}
