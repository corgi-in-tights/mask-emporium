package net.thecorgi.masks.mixins.entity;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.thecorgi.masks.init.MaskRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.util.math.MathHelper.lerp;

@Mixin(ArrowEntity.class)
public abstract class ArrowEntityMixin extends PersistentProjectileEntity {
    protected ArrowEntityMixin(EntityType<? extends PersistentProjectileEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo info) {
        if (this.getOwner() != null && this.isCritical()) {
            if (this.getOwner().isPlayer()) {
                PlayerEntity player = (PlayerEntity) this.getOwner();
                TrinketComponent component = TrinketsApi.getTrinketComponent(player).get();

                if (component.isEquipped(MaskRegistry.CYCLOPS_EYE_MASK)) {
                    Box box = this.getBoundingBox().expand(5, 5, 5);
                    LivingEntity target = world.getClosestEntity(LivingEntity.class, TargetPredicate.DEFAULT, player, player.getX(), player.getY(), player.getZ(), box);
                    if (target != null) {
                        Vec3d f = new Vec3d(target.getX(), target.getBodyY(0.33), target.getZ());
                        Vec3d nor = (f.subtract(this.getPos()).normalize()).multiply(0.3);
                        this.addVelocity(nor.x, nor.y, nor.z);
                    }
                }
            }
        }
    }
}