package net.thecorgi.masks.init.masks;

import dev.emi.trinkets.api.SlotReference;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FilterMask extends SimpleMask {
    private int stored_breath = 100;
    private int max_breath;

    public FilterMask(Item.Settings settings, int max_breath) {
        super(settings);
        this.max_breath = max_breath;
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity.isSubmergedInWater() && this.stored_breath > 0) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 2, 0, true, false));
            this.stored_breath--;
        } else if (!entity.isSubmergedInWater() && !entity.hasStatusEffect(StatusEffects.WATER_BREATHING) && this.stored_breath <= this.max_breath) {
            this.stored_breath++;
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        stack.addHideFlag(ItemStack.TooltipSection.MODIFIERS);
        tooltip.add(new TranslatableText("item.masks.filter_mask.tooltip").formatted(Formatting.GRAY) );
    }
}