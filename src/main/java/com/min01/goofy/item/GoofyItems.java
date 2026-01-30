package com.min01.goofy.item;

import java.util.function.Supplier;

import com.min01.goofy.GoofyCritters;
import com.min01.goofy.entity.GoofyEntities;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GoofyItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GoofyCritters.MODID);

	public static final RegistryObject<Item> BUCKET_OF_GESTALT = ITEMS.register("bucket_of_gestalt", () -> new GoofyMobBucketItem(() -> GoofyEntities.GESTALT.get(), () -> SoundEvents.MUD_PLACE, new Item.Properties()));
	public static final RegistryObject<Item> GESTALT_SPAWN_EGG = registerSpawnEgg("gestalt_spawn_egg", () -> GoofyEntities.GESTALT.get(), 394758, 2368548);
	
	public static final RegistryObject<Item> GOOFY_MASK = ITEMS.register("goofy_mask", () -> new GoofyMaskItem(new Item.Properties()));
	
	public static RegistryObject<Item> registerBlockItem(String name, Supplier<Block> block, Item.Properties properties)
	{
		return ITEMS.register(name, () -> new BlockItem(block.get(), properties));
	}
	
	public static <T extends Mob> RegistryObject<Item> registerSpawnEgg(String name, Supplier<EntityType<T>> entity, int color1, int color2) 
	{
		return ITEMS.register(name, () -> new ForgeSpawnEggItem(entity, color1, color2, new Item.Properties()));
	}
}
