package xyz.tbvns.ghostTrainer.Config;

import lombok.SneakyThrows;
import xyz.tbvns.EZConfig;

public class ConfigManager {
    public static void setUp() throws Exception {
        EZConfig.registerClassPath("xyz.tbvns.ghostTrainer");
        EZConfig.load();
    }

    @SneakyThrows
    public static void save() {
        EZConfig.save();
    }
}
