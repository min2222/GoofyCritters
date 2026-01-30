package com.min01.goofy.entity.model;

import com.min01.goofy.GoofyCritters;
import com.min01.goofy.entity.living.EntityGestaltHand;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class ModelGestaltHand extends EntityModel<EntityGestaltHand> 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(GoofyCritters.MODID, "gestalt_hand"), "main");
	private final ModelPart root;
	private final ModelPart hand;

	public ModelGestaltHand(ModelPart root) 
	{
		this.root = root.getChild("root");
		this.hand = this.root.getChild("hand");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();


		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition hand = root.addOrReplaceChild("hand", CubeListBuilder.create().texOffs(0, 20).addBox(-3.0F, -1.0F, -2.5F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -0.5F));

		hand.addOrReplaceChild("finger1", CubeListBuilder.create().texOffs(24, 20).addBox(-0.5F, -1.0F, -4.5F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.817F, 0.0F, 2.817F, 0.0F, -0.5236F, 0.0F));

		hand.addOrReplaceChild("finger2", CubeListBuilder.create().texOffs(0, 28).addBox(-1.0F, -0.5F, -4.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, -2.2F, 0.0F, -0.2618F, 0.0F));

		hand.addOrReplaceChild("finger3", CubeListBuilder.create().texOffs(0, 28).addBox(-1.0F, -0.5F, -4.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.5F));

		hand.addOrReplaceChild("finger4", CubeListBuilder.create().texOffs(0, 28).addBox(-1.0F, -0.5F, -4.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, -2.2F, 0.0F, 0.2618F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(EntityGestaltHand entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.hand.yRot = (float) Math.toRadians(netHeadYaw);
		this.hand.xRot = (float) Math.toRadians(headPitch);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}