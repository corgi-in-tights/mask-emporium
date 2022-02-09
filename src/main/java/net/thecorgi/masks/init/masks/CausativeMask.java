package net.thecorgi.masks.init.masks;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CausativeMask extends SimpleMask {
    public CausativeMask(Settings settings) {
        super(settings);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack,
                                                                           SlotReference slot, LivingEntity entity, UUID uuid) {
        super.getModifiers(stack, slot, entity, uuid);
        Multimap<EntityAttribute, EntityAttributeModifier> map = Multimaps.newMultimap(Maps.newLinkedHashMap(), ArrayList::new);

        map.put(
                EntityAttributes.GENERIC_FOLLOW_RANGE,
                new EntityAttributeModifier(
                        uuid,
                        "masks:causative_mask",
                        0.4,
                        EntityAttributeModifier.Operation.MULTIPLY_BASE
                ));
        return map;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        stack.addHideFlag(ItemStack.TooltipSection.MODIFIERS);
        tooltip.add(new TranslatableText("item.masks.causative_mask.tooltip").formatted(Formatting.GRAY) );
    }
}