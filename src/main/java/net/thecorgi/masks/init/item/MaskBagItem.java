package net.thecorgi.masks.init.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.thecorgi.masks.MaskEmporium;
import net.thecorgi.masks.init.MaskRegistry;
import org.apache.commons.lang3.RandomUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.thecorgi.masks.MaskEmporium.config;

public class MaskBagItem extends Item {
    public MaskBagItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient) {
//            user.setCurrentHand(hand);
            System.out.println(stack);
            if (!user.getAbilities().creativeMode) {
                stack.decrement(1);
            }
            user.incrementStat(Stats.USED.getOrCreateStat(this));

            if (stack.isEmpty()) {
                int e = RandomUtils.nextInt(1, 13);
                if (config.mask_controller.bloodhound_mask && e == 1) {
                    user.getInventory().insertStack(new ItemStack(MaskRegistry.BLOODHOUND_MASK));
                }
                if (config.mask_controller.butchers_mask && e == 2) {
                    user.getInventory().insertStack(new ItemStack(MaskRegistry.BUTCHERS_MASK));
                }
                if (config.mask_controller.causative_mask && e == 3) {
                    user.getInventory().insertStack(new ItemStack(MaskRegistry.CAUSATIVE_MASK));
                }
                if (config.mask_controller.cowardly_mask && e == 4) {
                    user.getInventory().insertStack(new ItemStack(MaskRegistry.COWARDLY_MASK));
                }
                if (config.mask_controller.cyclops_eye_mask && e == 5) {
                    user.getInventory().insertStack(new ItemStack(MaskRegistry.CYCLOPS_EYE_MASK));
                }
                if (config.mask_controller.devils_mask && e == 6) {
                    user.getInventory().insertStack(new ItemStack(MaskRegistry.DEVILS_MASK));
                }
                if (config.mask_controller.filter_mask && e == 7) {
                    user.getInventory().insertStack(new ItemStack(MaskRegistry.FILTER_MASK));
                }
                if (config.mask_controller.fortunas_ballet && e == 8) {
                    user.getInventory().insertStack(new ItemStack(MaskRegistry.FORTUNAS_BALLET));
                }
                if (config.mask_controller.jagged_mask && e == 9) {
                    user.getInventory().insertStack(new ItemStack(MaskRegistry.JAGGED_MASK));
                }
                if (config.mask_controller.mask_of_sol && e == 10) {
                    user.getInventory().insertStack(new ItemStack(MaskRegistry.MASK_OF_SOL));
                }
                if (config.mask_controller.thief_mask && e == 11) {
                    user.getInventory().insertStack(new ItemStack(MaskRegistry.THIEF_MASK));
                }
                if (config.mask_controller.mustache && e == 12) {
                    user.getInventory().insertStack(new ItemStack(MaskRegistry.MUSTACHE));
                }

                System.out.println(stack);
                return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
            }
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(new TranslatableText("item.masks.mask_bag.tooltip").formatted(Formatting.GRAY));
    }
}
