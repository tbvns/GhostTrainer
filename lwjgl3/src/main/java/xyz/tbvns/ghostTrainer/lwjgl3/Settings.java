package xyz.tbvns.ghostTrainer.lwjgl3;

import xyz.tbvns.EZConfig;

public class Settings {
    public static void save() throws Exception {
        EZConfig.save();
    }

    public static void load() throws Exception {
        EZConfig.load();
    }
}
