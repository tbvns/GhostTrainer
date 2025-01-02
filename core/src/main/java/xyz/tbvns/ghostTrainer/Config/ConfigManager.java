package xyz.tbvns.ghostTrainer.Config;

import xyz.tbvns.EZConfig;
import xyz.tbvns.EZConfigUtils;

public class ConfigManager {
    public static void setUp() throws Exception {
        EZConfig.registerClassPath("xyz.tbvns.ghostTrainer");
        EZConfig.load();
    }
}
