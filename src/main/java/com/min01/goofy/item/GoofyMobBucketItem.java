package com.min01.goofy.item;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class GoofyMobBucketItem extends MobBucketItem
{
	public GoofyMobBucketItem(Supplier<? extends EntityType<?>> entitySupplier, Supplier<? extends SoundEvent> soundSupplier, Properties properties) 
	{
		super(entitySupplier, () -> Fluids.EMPTY, soundSupplier, properties);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand)
	{
		ItemStack stack = pPlayer.getItemInHand(pHand);
		BlockHitResult blockHit = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.SOURCE_ONLY);
        BlockPos blockPos = blockHit.getBlockPos();
        Direction direction = blockHit.getDirection();
        BlockPos blockPos1 = blockPos.relative(direction);
        if(blockHit.getType() == HitResult.Type.MISS) 
        {
        	return InteractionResultHolder.pass(stack);
        } 
        else if (blockHit.getType() != HitResult.Type.BLOCK)
        {
        	return InteractionResultHolder.pass(stack);
        }
        else if(pLevel.mayInteract(pPlayer, blockPos) && pPlayer.mayUseItemAt(blockPos1, direction, stack)) 
        {
            this.checkExtraContent(pPlayer, pLevel, stack, blockPos1);
            pPlayer.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(getEmptySuccessItem(stack, pPlayer), pLevel.isClientSide);
        }
        return InteractionResultHolder.fail(stack);
	}
}
