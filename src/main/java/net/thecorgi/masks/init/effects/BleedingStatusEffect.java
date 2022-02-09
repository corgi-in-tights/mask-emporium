package net.thecorgi.masks.init.effects;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;

import static java.lang.Math.round;

public class BleedingStatusEffect extends StatusEffect {

    public BleedingStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0xA8372F);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i;
        i = 25 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.getHealth() > 2.0F) {
            entity.damage(DamageSource.MAGIC, 1);

            if (!entity.world.isClient()) {
                entity.getServer().getWorld(entity.world.getRegistryKey()).spawnParticles(
                        new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.REDSTONE_BLOCK.getDefaultState()),
                        entity.getX(), entity.getBodyY(0.5), entity.getZ(), round(50 * entity.getScaleFactor()), entity.getScaleFactor(), entity.getScaleFactor(), entity.getScaleFactor(), 1);
            }
        }
    }
}