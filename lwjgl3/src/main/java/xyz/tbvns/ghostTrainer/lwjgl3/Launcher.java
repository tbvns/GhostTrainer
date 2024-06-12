package xyz.tbvns.ghostTrainer.lwjgl3;

import xyz.tbvns.ghostTrainer.Main;
import xyz.tbvns.ghostTrainer.MouseMovement;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Launcher {
    public static void open() throws InterruptedException, IOException {
        JFrame frame = new JFrame("Ghost Trainer launcher");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(180, 230);
        frame.setResizable(false);

        JTextField sensitivity = new JTextField("1");
        JTextField fov = new JTextField("75");
        JButton color = new JButton("Select color");
        JButton targetSettings = new JButton("Spawn settings");
        JTextField transparency = new JTextField("255");
        JButton launch = new JButton("Launch");

        targetSettings.addActionListener(a -> {
            TargetSettings.open();
        });

        color.addActionListener(a -> {
            ColorChooser.open();
        });


        launch.addActionListener(a -> {
            try {
                MouseMovement.sensitivity = Float.parseFloat(sensitivity.getText());
                Main.fov = Integer.parseInt(fov.getText());
                Main.color = new Color(Main.color.getRed(), Main.color.getGreen(), Main.color.getBlue(), Integer.parseInt(transparency.getText()));
                frame.dispose();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Could not get number " + e.getMessage().toLowerCase(), "Error: invalid value", JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Something went wrong:\n" + e.getMessage().toLowerCase(), "Unhandled error:", JOptionPane.ERROR_MESSAGE);
            }
        });
        launch.setBackground(new Color(255, 45, 45));

        panel.add(new JLabel("In game sensitivity:"));
        panel.add(sensitivity);
        panel.add(new JLabel("Field of view:"));
        panel.add(fov);
        panel.add(new JLabel("Targets transparency:"));
        panel.add(transparency);
        panel.add(targetSettings);
        panel.add(color);
        panel.add(launch);

        Utils.setIcon(frame);

        frame.add(panel);
        frame.setVisible(true);
        Utils.center(frame);

        while (frame.isVisible()) {
            Thread.sleep(100);
        }
    }
}
