package net.thecorgi.masks.init.blocks;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.ArmorStandEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.ArmorStandArmorEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3f;
import net.thecorgi.masks.init.MaskRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class HeadDisplayBlockRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {
    public HeadDisplayBlockRenderer(BlockEntityRendererFactory.Context ctx) {
//        this.addFeature(new ArmorFeatureRenderer<ArmorStandEntity, ArmorStandArmorEntityModel, ArmorStandArmorEntityModel>(this, new ArmorStandArmorEntityModel(context.getPart(EntityModelLayers.ARMOR_STAND_INNER_ARMOR)), new ArmorStandArmorEntityModel(context.getPart(EntityModelLayers.ARMOR_STAND_OUTER_ARMOR))));
    }
    private static ItemStack stack = new ItemStack(MaskRegistry.DEVILS_MASK, 1);

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
//        Inventory blockEntity = (Inventory) Objects.requireNonNull(entity.getWorld()).getBlockEntity(entity.getPos());
//        ItemStack stack = blockEntity.getStack(0);
//        System.out.println(stack);
        matrices.push();

//        double offset = Math.sin((entity.getWorld().getTime() + tickDelta) / 8.0) / 4.0;
        matrices.translate(1.15, 0.35, 0.5);
        matrices.scale(1.7F, 1.7F, 1.7F);

        // Rotate the item
//        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));
        matrices.multiply(Vec3f.NEGATIVE_Z.getDegreesQuaternion(22.5F));

//        entity.renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.HEAD, i, this.getArmor(EquipmentSlot.HEAD));

        int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
        MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
        matrices.pop();
    }

}