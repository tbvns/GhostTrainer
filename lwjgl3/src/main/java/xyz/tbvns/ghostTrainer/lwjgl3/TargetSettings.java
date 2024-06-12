package xyz.tbvns.ghostTrainer.lwjgl3;

import xyz.tbvns.ghostTrainer.Main;
import xyz.tbvns.ghostTrainer.MouseMovement;

import javax.swing.*;
import java.awt.*;

public class TargetSettings {
    public static void open() {
        JFrame frame = new JFrame("Targets settings");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setSize(180, 160);
        frame.setResizable(false);
        Utils.center(frame);

        JTextField targetCount = new JTextField("10");
        JTextField targetSize = new JTextField("1");
        JButton save = new JButton("Save");

        save.addActionListener(a -> {
            try {
                Main.ballCount = Integer.parseInt(targetCount.getText());
                Main.size = Float.parseFloat(targetSize.getText());
                frame.dispose();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Could not get number " + e.getMessage().toLowerCase(), "Error: invalid value", JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Something went wrong:\n" + e.getMessage().toLowerCase(), "Unhandled error:", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JLabel("Targets count:"));
        panel.add(targetCount);
        panel.add(new JLabel("Target size:"));
        panel.add(targetSize);
        panel.add(save);
        frame.add(panel);

        frame.setVisible(true);
    }
}
