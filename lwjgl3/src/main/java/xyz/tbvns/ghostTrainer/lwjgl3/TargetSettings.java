package xyz.tbvns.ghostTrainer.lwjgl3;

import xyz.tbvns.ghostTrainer.Config.Config;
import xyz.tbvns.ghostTrainer.Main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TargetSettings {
    public static void open() throws IOException {
        JFrame frame = new JFrame("Targets settings");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setSize(180, 180);
        frame.setResizable(false);
        Utils.center(frame);

        JTextField targetCount = new JTextField(String.valueOf(Config.ballCount));
        JTextField targetSize = new JTextField(String.valueOf(Config.size));
        JButton save = new JButton("Save");

        save.addActionListener(a -> {
            try {
                Config.ballCount = Integer.parseInt(targetCount.getText());
                Config.size = Float.parseFloat(targetSize.getText());
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

        Utils.setIcon(frame);
        frame.setVisible(true);
    }
}
