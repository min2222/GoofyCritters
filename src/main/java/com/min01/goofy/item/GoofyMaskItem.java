package com.min01.goofy.item;

import java.util.List;

import com.min01.goofy.misc.GoofyArmorMaterials;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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
	public void appendHoverText(ItemStack pStack, Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced)
	{
		pTooltipComponents.add(Component.translatable("item.goofycritters.goofy_mask.desc").withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY));
	}
}
