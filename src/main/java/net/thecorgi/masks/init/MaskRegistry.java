package net.thecorgi.masks.init;

import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.item.*;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.thecorgi.masks.MaskEmporium;
import net.thecorgi.masks.init.masks.*;

public class MaskRegistry {
    public static final SimpleMask RABBIT_MASK = new SimpleMask(new Item.Settings());
    public static final SimpleMask MUSTACHE = new SimpleMask(new Item.Settings());
    public static final FilterMask FILTER_MASK = new FilterMask(new Item.Settings().rarity(Rarity.RARE), 1200);
    public static final CowardlyMask COWARDLY_MASK = new CowardlyMask(new Item.Settings().rarity(Rarity.RARE));
    public static final CausativeMask CAUSATIVE_MASK = new CausativeMask(new Item.Settings().rarity(Rarity.RARE));
    public static final MaskOfSol MASK_OF_SOL = new MaskOfSol(new Item.Settings().rarity(Rarity.RARE));
    public static final FortunasBallet FORTUNAS_BALLET = new FortunasBallet(new Item.Settings().rarity(Rarity.RARE));
    public static final JaggedMask JAGGED_MASK = new JaggedMask(new Item.Settings().rarity(Rarity.RARE));
    public static final DevilsMask DEVILS_MASK = new DevilsMask(new Item.Settings().rarity(Rarity.RARE));
    public static final ButchersMask BUTCHERS_MASK = new ButchersMask(new Item.Settings().rarity(Rarity.RARE));
    public static final CyclopsEyeMask CYCLOPS_EYE_MASK = new CyclopsEyeMask(new Item.Settings().rarity(Rarity.RARE));
    public static final ThiefMask THIEF_MASK = new ThiefMask(new Item.Settings().rarity(Rarity.RARE));
    public static final BloodhoundMask BLOODHOUND_MASK = new BloodhoundMask(new Item.Settings().rarity(Rarity.RARE));

    public static void init() {
        register("rabbit_mask", RABBIT_MASK);
        register("mustache", MUSTACHE);
        register("filter_mask", FILTER_MASK);
        register("cyclops_eye_mask", CYCLOPS_EYE_MASK);
        register("thief_mask", THIEF_MASK);
        register("bloodhound_mask", BLOODHOUND_MASK);
        register("cowardly_mask", COWARDLY_MASK);
        register("devils_mask", DEVILS_MASK);
        register("butchers_mask", BUTCHERS_MASK);
        register("causative_mask", CAUSATIVE_MASK);
        register("mask_of_sol", MASK_OF_SOL);
        register("jagged_mask", JAGGED_MASK);
        register("fortunas_ballet", FORTUNAS_BALLET);
    }

    private static void register(String path, SimpleMask mask) {
        Registry.register(Registry.ITEM, MaskEmporium.id(path), mask);
        TrinketRendererRegistry.registerRenderer(mask, mask);
    }
}
