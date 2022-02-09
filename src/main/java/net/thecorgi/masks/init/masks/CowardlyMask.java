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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CowardlyMask extends SimpleMask {
    public static final UUID COWARDLY_MASK_UUID_ATTACK_DAMAGE = UUID.fromString("b419cefe-5fce-4de4-ac13-8fe5144c152d");
    public static final UUID COWARDLY_MASK_UUID_SPEED = UUID.fromString("54d832a0-5889-46c2-93f6-8862db2f18d2");

    public CowardlyMask(Item.Settings settings) {
        super(settings);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.onUnequip(stack, slot, entity);
        if(entity.getAttributes().hasModifierForAttribute(EntityAttributes.GENERIC_ATTACK_DAMAGE, COWARDLY_MASK_UUID_ATTACK_DAMAGE) || entity.getAttributes().hasModifierForAttribute(EntityAttributes.GENERIC_MOVEMENT_SPEED, COWARDLY_MASK_UUID_SPEED)) {
            Multimap<EntityAttribute, EntityAttributeModifier> map = Multimaps.newMultimap(Maps.newLinkedHashMap(), ArrayList::new);
            EntityAttributeModifier modifier = new EntityAttributeModifier(COWARDLY_MASK_UUID_ATTACK_DAMAGE, "masks:cowardly_mask_damage", getCowardlyDamageModifier(entity), EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
            EntityAttributeModifier modifier2 = new EntityAttributeModifier(COWARDLY_MASK_UUID_SPEED, "masks:cowardly_mask_speed", getCowardlySpeedModifier(entity), EntityAttributeModifier.Operation.MULTIPLY_TOTAL);

            map.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, modifier);
            map.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, modifier2);
            entity.getAttributes().removeModifiers(map);
        }
    }

    public static double getCowardlySpeedModifier(LivingEntity entity) {
        if (entity != null) {
            double mod = (entity.getMaxHealth() - 3) / (entity.getHealth() * entity.getHealth() + 1);
            return Math.max(0, 1 + Math.log10(mod) / 4);
        }
        else { return 0; }
    }

    public static double getCowardlyDamageModifier(LivingEntity entity) {
        if (entity != null) {
            double mod = (entity.getMaxHealth() - 3) / (entity.getHealth() * entity.getHealth() + 1);
            return -Math.max(0, (1 + Math.log10(mod)) / 2.5);
        }
        else { return 0; }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        stack.addHideFlag(ItemStack.TooltipSection.MODIFIERS);
        tooltip.add(new TranslatableText("item.masks.cowardly_mask.tooltip").formatted(Formatting.GRAY));
    }
}