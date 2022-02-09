package net.thecorgi.masks.init;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;
import net.thecorgi.masks.MaskEmporium;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import static net.thecorgi.masks.MaskEmporium.config;

public class LootTableRegistry {
    public static final Collection<Identifier> WORLD_CHESTS =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                    LootTables.ABANDONED_MINESHAFT_CHEST, LootTables.SIMPLE_DUNGEON_CHEST
            )));

    public static void init(){
        LootTableLoadingCallback.EVENT.register(((resourceManager, manager, id, supplier, setter) -> {
            if (!id.getNamespace().equals("minecraft"))
                return;

            FabricLootPoolBuilder poolBuilder;

            if (WORLD_CHESTS.contains(id)){
                poolBuilder = FabricLootPoolBuilder.builder();
                poolBuilder.rolls(ConstantLootNumberProvider.create(1));

//                if (config.mask_controller.mustache) {
//                    registerLootEntry(poolBuilder, MaskRegistry.MUSTACHE, config.loot_chance.mustache);
//                }
//                if (config.mask_controller.bloodhound_mask) {
//                    registerLootEntry(poolBuilder, MaskRegistry.BLOODHOUND_MASK, config.loot_chance.bloodhound_mask);
//                }
//                if (config.mask_controller.butchers_mask) {
//                    registerLootEntry(poolBuilder, MaskRegistry.BUTCHERS_MASK, config.loot_chance.butchers_mask);
//                }
//                if (config.mask_controller.causative_mask) {
//                    registerLootEntry(poolBuilder, MaskRegistry.CAUSATIVE_MASK, config.loot_chance.causative_mask);
//                }
//                if (config.mask_controller.cowards_mask) {
//                    registerLootEntry(poolBuilder, MaskRegistry.COWARDS_MASK, config.loot_chance.cowards_mask);
//                }
//                if (config.mask_controller.cyclops_eye_mask) {
//                    registerLootEntry(poolBuilder, MaskRegistry.CYCLOPS_EYE_MASK, config.loot_chance.cyclops_eye_mask);
//                }
//                if (config.mask_controller.devils_mask) {
//                    registerLootEntry(poolBuilder, MaskRegistry.DEVILS_MASK, config.loot_chance.devils_mask);
//                }
//                if (config.mask_controller.filter_mask) {
//                    registerLootEntry(poolBuilder, MaskRegistry.FILTER_MASK, config.loot_chance.filter_mask);
//                }
//                if (config.mask_controller.fortunas_ballet) {
//                    registerLootEntry(poolBuilder, MaskRegistry.FORTUNAS_BALLET, config.loot_chance.fortunas_ballet);
//                }
//                if (config.mask_controller.jagged_mask) {
//                    registerLootEntry(poolBuilder, MaskRegistry.JAGGED_MASK, config.loot_chance.jagged_mask);
//                }
//                if (config.mask_controller.mask_of_sol) {
//                    registerLootEntry(poolBuilder, MaskRegistry.MASK_OF_SOL, config.loot_chance.mask_of_sol);
//                }
//                if (config.mask_controller.thief_mask) {
//                    registerLootEntry(poolBuilder, MaskRegistry.THIEF_MASK, config.loot_chance.thief_mask);
//                }
//
                registerLootEntry(poolBuilder, MaskEmporium.MASK_BAG, config.chest_loot_chance.bag_chance);
                poolBuilder.withEntry(net.minecraft.loot.entry.EmptyEntry.Serializer().weight(config.chest_loot_chance.void_chance).build());
                supplier.pool(poolBuilder);
            }
        }));
    }

    public static void registerLootEntry(FabricLootPoolBuilder poolBuilder, Item item, int p){
        poolBuilder.withEntry(ItemEntry.builder(item).weight(p).build());
    }
}
