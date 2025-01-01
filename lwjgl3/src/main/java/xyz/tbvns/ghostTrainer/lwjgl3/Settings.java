package xyz.tbvns.ghostTrainer.lwjgl3;

import xyz.tbvns.ghostTrainer.Inputs.NativeMouseReader;
import xyz.tbvns.ghostTrainer.Main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Settings {
    static final String filePath = System.getProperty("user.home") + "/GhostTrainerSave.txt";
    public static void save() {
        String toWrite = NativeMouseReader.sensitivity + "\n" +
            Main.fov + "\n" +
            Main.color.getRed() + "\n" +
            Main.color.getGreen() + "\n" +
            Main.color.getBlue() + "\n" +
            Main.color.getAlpha() + "\n" +
            Main.ballCount + "\n" +
            Main.size;

        try {
            Files.writeString(Path.of(filePath), toWrite);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new Frame(), "An error occurred while saving settings:\n" + e.getMessage(), "An error occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void load() {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                List<String> settings = Files.readAllLines(Path.of(filePath));
                NativeMouseReader.sensitivity = Float.parseFloat(settings.get(0));
                Main.fov = Integer.parseInt(settings.get(1));
                int red = Integer.parseInt(settings.get(2));
                int green = Integer.parseInt(settings.get(3));
                int blue = Integer.parseInt(settings.get(4));
                int alpha = Integer.parseInt(settings.get(5));
                Main.color = new Color(red, green, blue, alpha);
                Main.ballCount = Integer.parseInt(settings.get(6));
                Main.size = Float.parseFloat(settings.get(7));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new Frame(), "An error occurred while loading settings:\n" + e.getMessage(), "An error occurred", JOptionPane.ERROR_MESSAGE);
        }
    }
}
