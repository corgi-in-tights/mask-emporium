package net.thecorgi.masks.init.masks;

import dev.emi.trinkets.api.SlotReference;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.thecorgi.masks.MaskEmporium;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ThiefMask extends SimpleMask {
    protected boolean started_sneaking = false;

    public ThiefMask(Item.Settings settings) {
        super(settings);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity.isSneaking()) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 2, 0, false, false));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 2, 1, false, false));
            if (!this.started_sneaking && !entity.world.isClient()) {
                this.started_sneaking = true;
                entity.getServer().getWorld(entity.world.getRegistryKey()).spawnParticles(
                        ParticleTypes.LARGE_SMOKE,
                        entity.getX(), entity.getY() + 0.7, entity.getZ(), 15, 0, 0.45, 0, 0.05);
            }
        } else if (!entity.isSneaking()) {
            this.started_sneaking = false;
        }
    }

    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (!entity.isSneaking()) {
            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            MaskEmporium.translateToFace(matrixStack, contextModel, entity, headYaw, headPitch);
            matrixStack.scale(-0.7f, -0.7f, 0.7f);
            itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrixStack, vertexConsumers, 0);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        stack.addHideFlag(ItemStack.TooltipSection.MODIFIERS);
        tooltip.add(new TranslatableText("item.masks.thief_mask.tooltip").formatted(Formatting.GRAY) );
    }
}