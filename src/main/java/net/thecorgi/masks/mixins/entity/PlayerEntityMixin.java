package net.thecorgi.masks.mixins.entity;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.thecorgi.masks.MaskEmporium;
import net.thecorgi.masks.init.MaskRegistry;
import net.thecorgi.masks.init.masks.DevilsMask;
import org.apache.commons.lang3.RandomUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

import static net.thecorgi.masks.init.masks.DevilsMask.DEVILS_MASK_UUID_ATTACK_DAMAGE;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow public abstract boolean isPlayer();
    @Shadow public abstract void addExperience(int experience);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "onKilledOther", at = @At("TAIL"))
    public void onKilledOther(ServerWorld world, LivingEntity other, CallbackInfo info) {
        if (this.isPlayer()) {
            TrinketComponent component = TrinketsApi.getTrinketComponent(this).get();
            if (component.isEquipped(MaskRegistry.BUTCHERS_MASK) && other instanceof HostileEntity) {
                this.addExperience(3);
            }
        }
    }

    @Inject(method = "attack", at = @At("HEAD"))
    protected void attack(Entity target, CallbackInfo ci) {
        TrinketComponent component = TrinketsApi.getTrinketComponent(this).get();
        LivingEntity entity = (LivingEntity) target;
        if (target.isLiving()) {
            if (component.isEquipped(MaskRegistry.DEVILS_MASK)) {
                Multimap<EntityAttribute, EntityAttributeModifier> map = Multimaps.newMultimap(Maps.newLinkedHashMap(), ArrayList::new);
                EntityAttributeModifier modifier = new EntityAttributeModifier(DEVILS_MASK_UUID_ATTACK_DAMAGE, "masks:devils_mask", 0.3, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
                map.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, modifier);
                this.getAttributes().addTemporaryModifiers(map);
            }
            if (component.isEquipped(MaskRegistry.BLOODHOUND_MASK)) {
                if (RandomUtils.nextInt(0, 30) == 1) {
                    entity.addStatusEffect(new StatusEffectInstance(MaskEmporium.BLEEDING, 100, 0, false, false));
                }
            }
        }
    }
}
