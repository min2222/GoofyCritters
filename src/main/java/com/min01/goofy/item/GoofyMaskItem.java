package com.min01.goofy.item;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.min01.goofy.misc.GoofyArmorMaterials;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class GoofyMaskItem extends ArmorItem
{
	public GoofyMaskItem(Properties pProperties)
	{
		super(GoofyArmorMaterials.GOOFY, Type.HELMET, pProperties);
	}
	
	@Override
	public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type)
	{
		return "goofycritters:textures/armor/goofy_mask.png";
	}
	
	@Override
	public void appendHoverText(ItemStack pStack, Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced)
	{
		pTooltipComponents.add(Component.translatable("item.goofycritters.goofy_mask.desc").withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY));
	}
}
