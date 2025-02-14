package xyz.tbvns.ghostTrainer.lwjgl3;

import xyz.tbvns.ghostTrainer.Config.Config;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ColorChooser {
    public static void open() throws IOException {
        JFrame frame = new JFrame("Select a color");
        frame.setIconImage(null);
        frame.setResizable(false);
        frame.setSize(700, 430);
        JPanel panel = new JPanel();
        JColorChooser chooser = new JColorChooser(new Color(Config.r, Config.g, Config.b, Config.a));
        chooser.setBounds(0, 0, 620, 380);

        JButton select = new JButton("Select");
        select.addActionListener(a -> {
            Config.r = chooser.getColor().getRed();
            Config.g = chooser.getColor().getGreen();
            Config.b = chooser.getColor().getBlue();
            Config.a = chooser.getColor().getAlpha();
            frame.dispose();
        });

        panel.add(chooser);
        panel.add(select);
        frame.add(panel);
        Utils.setIcon(frame);
        frame.setVisible(true);
        Utils.center(frame);
    }
}
