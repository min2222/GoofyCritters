package com.min01.goofy.misc;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class GoofyArmorMaterials
{
	public static final ArmorMaterial GOOFY = new GoofyArmorMaterial("goofy", new int[]{350, 350, 350, 350}, new int[]{8, 8, 8, 8}, 20, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.025F, () -> Ingredient.of(Items.ROTTEN_FLESH));
}
