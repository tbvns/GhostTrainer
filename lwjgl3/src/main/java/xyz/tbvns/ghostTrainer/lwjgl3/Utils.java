package xyz.tbvns.ghostTrainer.lwjgl3;

import xyz.tbvns.ghostTrainer.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static void center(JFrame frame) {
        frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - frame.getWidth() / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - frame.getHeight() / 2);
    }

    public static void setIcon(JFrame frame) throws IOException {
        Image image = ImageIO.read(Main.class.getResourceAsStream("/GhostTrainer.png"));
        frame.setIconImage(image);
    }
}
