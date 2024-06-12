package xyz.tbvns.ghostTrainer.lwjgl3;

import xyz.tbvns.ghostTrainer.Main;

import javax.swing.*;
import java.awt.*;

public class ColorChooser {
    public static void open() {
        JFrame frame = new JFrame("Select a color");
        frame.setIconImage(null);
        frame.setResizable(false);
        frame.setSize(620, 400);
        JPanel panel = new JPanel();
        JColorChooser chooser = new JColorChooser(Color.cyan);
        chooser.setBounds(0, 0, 620, 380);

        JButton select = new JButton("Select");
        select.addActionListener(a -> {
            Main.color = chooser.getColor();
            System.out.println(Main.color);
            frame.dispose();
        });

        panel.add(chooser);
        panel.add(select);
        frame.add(panel);
        frame.setVisible(true);
        Utils.center(frame);
    }
}
