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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.thecorgi.masks.MaskEmporium;
import net.thecorgi.masks.init.MaskRegistry;
import net.thecorgi.masks.init.masks.CowardlyMask;
import org.apache.commons.lang3.RandomUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "damage", at = @At("TAIL"))
    private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        // if target of damage is wearing mask & source is not null
        if (this.isPlayer()) {
            PlayerEntity target = ((PlayerEntity) (Object) this);
            TrinketComponent component = TrinketsApi.getTrinketComponent(target).get();
            if (component.isEquipped(MaskRegistry.DEVILS_MASK)) {
                target.damage(DamageSource.MAGIC, amount / 10);
            }
            if (component.isEquipped(MaskRegistry.COWARDLY_MASK)) {
                Multimap<EntityAttribute, EntityAttributeModifier> map = Multimaps.newMultimap(Maps.newLinkedHashMap(), ArrayList::new);
                EntityAttributeModifier modifier = new EntityAttributeModifier(CowardlyMask.COWARDLY_MASK_UUID_ATTACK_DAMAGE, "masks:cowards_mask_damage", CowardlyMask.getCowardlyDamageModifier(target), EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
                EntityAttributeModifier modifier2 = new EntityAttributeModifier(CowardlyMask.COWARDLY_MASK_UUID_SPEED, "masks:cowards_mask_speed", CowardlyMask.getCowardlySpeedModifier(target), EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
                map.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, modifier);
                map.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, modifier2);
                target.getAttributes().addTemporaryModifiers(map);
            }
        }

        // if source of damage is wearing mask & source is player
        if (source.getAttacker() instanceof ServerPlayerEntity) {
            LivingEntity target = ((LivingEntity) (Object) this);
            PlayerEntity attacker = (PlayerEntity) source.getAttacker();
            TrinketComponent component = TrinketsApi.getTrinketComponent(attacker).get();

            if (component.isEquipped(MaskRegistry.JAGGED_MASK)) {
                target.damage(DamageSource.MAGIC, amount / 15);
            }
        }
    }
}

