package net.thecorgi.masks.init.masks;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import dev.emi.trinkets.api.SlotReference;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
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

public class DevilsMask extends SimpleMask {
    public DevilsMask(Item.Settings settings) {
        super(settings);
        this.getDefaultStack().addHideFlag(ItemStack.TooltipSection.MODIFIERS);
    }

    public static final UUID DEVILS_MASK_UUID_ATTACK_DAMAGE = UUID.fromString("ddcdc333-9de4-4645-8534-28bd248078a4");

//    @Override
//    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack,
//                                                                           SlotReference slot, LivingEntity entity, UUID uuid) {
//        super.getModifiers(stack, slot, entity, uuid);
//        Multimap<EntityAttribute, EntityAttributeModifier> map = Multimaps.newMultimap(Maps.newLinkedHashMap(), ArrayList::new);
//
//        map.put(
//                EntityAttributes.GENERIC_ATTACK_DAMAGE,
//                new EntityAttributeModifier(
//                        uuid,
//                        "masks:devils_mask_attack_damage",
//                        0.3,
//                        EntityAttributeModifier.Operation.MULTIPLY_BASE
//                ));
//        map.put(
//                EntityAttributes.GENERIC_MAX_HEALTH,
//                new EntityAttributeModifier(
//                        uuid,
//                        "masks:devils_mask_max_health",
//                        -0.1,
//                        EntityAttributeModifier.Operation.MULTIPLY_TOTAL
//                ));
//        return map;
//    }

//    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
//        Multimap<EntityAttribute, EntityAttributeModifier> modifiers = super.getModifiers(stack, slot, entity, uuid);
//
//        // +25% movement speed
//        modifiers.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(uuid, "spectrum:movement_speed", 0.25, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
////        modifiers.put(StepHeightEntityAttributeMain.STEP_HEIGHT, STEP_BOOST_MODIFIER);
//
//        return modifiers;
//    }
    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.onUnequip(stack, slot, entity);
        if(entity.getAttributes().hasModifierForAttribute(EntityAttributes.GENERIC_ATTACK_DAMAGE, DEVILS_MASK_UUID_ATTACK_DAMAGE)) {
            Multimap<EntityAttribute, EntityAttributeModifier> map = Multimaps.newMultimap(Maps.newLinkedHashMap(), ArrayList::new);
            EntityAttributeModifier modifier = new EntityAttributeModifier(DEVILS_MASK_UUID_ATTACK_DAMAGE, "spectrum:jeopardant", 0.3, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
            map.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, modifier);
            entity.getAttributes().removeModifiers(map);
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(new TranslatableText("item.masks.devils_mask.tooltip").formatted(Formatting.GRAY));
    }

}