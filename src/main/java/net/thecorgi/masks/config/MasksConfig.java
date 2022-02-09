package net.thecorgi.masks.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.thecorgi.masks.MaskEmporium;

import java.util.Dictionary;
import java.util.Hashtable;

@Config(name = "masks")
public class MasksConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public ChestLootChance chest_loot_chance = new ChestLootChance();

    @ConfigEntry.Gui.CollapsibleObject
    public MaskController mask_controller = new MaskController();

    public static class ChestLootChance {
        @ConfigEntry.Gui.RequiresRestart
        public int bag_chance = 1;
        public int void_chance = 4;
    }

    public static class MaskController {
        @ConfigEntry.Gui.RequiresRestart
        public boolean mustache = true;
        public boolean bloodhound_mask = true;
        public boolean butchers_mask = true;
        public boolean causative_mask = true;
        public boolean cowardly_mask = true;
        public boolean cyclops_eye_mask = true;
        public boolean devils_mask = true;
        public boolean filter_mask = true;
        public boolean fortunas_ballet = true;
        public boolean jagged_mask = true;
        public boolean mask_of_sol = true;
        public boolean thief_mask = true;
    }
}