package xyz.tbvns.ghostTrainer.lwjgl3;

import xyz.tbvns.EZConfig;
import xyz.tbvns.ghostTrainer.Config.Config;
import xyz.tbvns.ghostTrainer.Inputs.NativeMouseReader;
import xyz.tbvns.ghostTrainer.Main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Settings {
    public static void save() throws Exception {
        EZConfig.save();
    }

    public static void load() throws Exception {
        EZConfig.load();
    }
}
