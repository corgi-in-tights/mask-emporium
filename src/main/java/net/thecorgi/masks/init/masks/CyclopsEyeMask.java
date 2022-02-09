package net.thecorgi.masks.init.masks;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CyclopsEyeMask extends SimpleMask {
    public CyclopsEyeMask(Item.Settings settings) {
        super(settings);
    }

//    @Override
//    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
//        Box box = entity.getBoundingBox().expand(10, 10, 10);
//        List<CowEntity> cows = entity.world.getEntitiesByType(EntityType.COW, box, EntityPredicates.canBePushedBy(entity));
//
//        System.out.println(cows);
//        if (!cows.isEmpty() && !entity.world.isClient()) {
//            for (CowEntity cow : cows) {
//                    startBeam(entity, cow);
//            }
//        }
//    }


    // Raycast particles between player and target with stunning effect
//    public static void startBeam(LivingEntity user, LivingEntity target) {
//        Vec3d start = user.getPos();
//        Vec3d end = target.getPos();
//        double dist = end.distanceTo(start);
//        for (int i = 0; i < dist; i++) {
//            double progress = i / dist;
//
//            double currentX = lerp(start.x, end.x, progress);
//            double currentY = lerp(start.y + 1.2, end.y  + 0.6, progress);
//            double currentZ = lerp(start.z, end.z, progress);
//            Vec3d current = new Vec3d(currentX, currentY, currentZ);
//            System.out.println(current);
//            if (!user.world.isClient()) {
//                user.getServer().getWorld(user.world.getRegistryKey()).spawnParticles(
//                        ParticleTypes.CLOUD,
//                        currentX, currentY, currentZ, 1, 0.1, 0.1, 0.1, 0.01);
//            }
//        }
//    }
//
//    public static double lerp(double i, double o, double p) {
//        return (i + (o - i) * MathHelper.clamp(p, 0, 1));
//    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        stack.addHideFlag(ItemStack.TooltipSection.MODIFIERS);
        tooltip.add(new TranslatableText("item.masks.cyclops_eye_mask.tooltip").formatted(Formatting.GRAY) );
    }

}
