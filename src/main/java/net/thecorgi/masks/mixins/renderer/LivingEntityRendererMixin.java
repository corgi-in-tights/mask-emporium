package net.thecorgi.masks.mixins.renderer;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.thecorgi.masks.init.MaskRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin extends EntityRenderer {
    protected LivingEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(at = @At("HEAD"), method = "hasLabel", cancellable = true)
    public void renderNameTag(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> info) {
        if (livingEntity.isPlayer()) {
            TrinketComponent component = TrinketsApi.getTrinketComponent(livingEntity).get();
            if (component.isEquipped(MaskRegistry.THIEF_MASK)) {
                double d = this.dispatcher.getSquaredDistanceToCamera(livingEntity);
                float f = livingEntity.isSneaky() ? 8.0F : 16.0F;
                if (d >= (double)(f * f)) {
                    info.setReturnValue(false);
                }
            }
        }
    }
}