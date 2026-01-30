package com.min01.goofy.entity.model;

import com.min01.goofy.GoofyCritters;
import com.min01.goofy.entity.animation.EyesAnimation;
import com.min01.goofy.entity.living.EntityEyes;
import com.min01.goofy.util.GoofyClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class ModelEyes extends HierarchicalModel<EntityEyes> 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(GoofyCritters.MODID, "eyes"), "main");
	private final ModelPart root;

	public ModelEyes(ModelPart root) 
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition eyes = root.addOrReplaceChild("eyes", CubeListBuilder.create(), PartPose.offset(-0.764F, -10.4343F, -0.5628F));

		eyes.addOrReplaceChild("hand", CubeListBuilder.create().texOffs(24, 26).addBox(-0.6667F, -2.5F, -3.5F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(-1.6667F, -3.5F, 1.5F, 5.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(32, 0).addBox(-2.6667F, -2.5F, -3.5F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.4306F, 6.9343F, 0.0628F));

		eyes.addOrReplaceChild("eye1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(2.764F, -7.5657F, 5.0628F));

		eyes.addOrReplaceChild("eye2", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.236F, -3.5657F, -0.4372F));

		eyes.addOrReplaceChild("eye3", CubeListBuilder.create().texOffs(24, 16).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(1.264F, -1.0657F, -3.9372F));

		eyes.addOrReplaceChild("leash1", CubeListBuilder.create().texOffs(14, 28).addBox(-0.5F, -6.0F, 0.0F, 1.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.8122F, 0.3684F, 1.8227F, -1.0063F, -0.7081F, 1.0857F));

		eyes.addOrReplaceChild("leash2", CubeListBuilder.create().texOffs(16, 28).addBox(-0.5F, -5.0F, 0.0F, 1.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0487F, 1.727F, -0.5366F, -0.3449F, -0.6369F, -0.6145F));

		eyes.addOrReplaceChild("leash3", CubeListBuilder.create().texOffs(18, 28).addBox(-0.5F, -3.0F, 0.0F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.014F, 3.1673F, -2.0372F, 0.9425F, 0.3491F, 0.5236F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(EntityEyes entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		entity.idleAnimationState.animate(this, EyesAnimation.IDLE, ageInTicks);
		GoofyClientUtil.animateHead(this.root, netHeadYaw, 0.0F);
	}
	
	@Override
	public ModelPart root() 
	{
		return this.root;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}