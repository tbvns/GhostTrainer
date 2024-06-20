package xyz.tbvns.ghostTrainer.lwjgl3;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import xyz.tbvns.ghostTrainer.Main;
import xyz.tbvns.ghostTrainer.MouseMovement;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Launcher {
    public static void open() throws InterruptedException, IOException {
        FlatMacDarkLaf.setup();
        Settings.load();

        JFrame frame = new JFrame("Ghost Trainer launcher");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(180, 250);
        frame.setResizable(false);

        JTextField sensitivity = new JTextField(String.valueOf(MouseMovement.sensitivity));
        JTextField fov = new JTextField(String.valueOf(Main.fov));
        JButton color = new JButton("Select color");
        JButton targetSettings = new JButton("Spawn settings");
        JTextField transparency = new JTextField(String.valueOf(Main.color.getAlpha()));
        JButton launch = new JButton("Launch");

        targetSettings.addActionListener(a -> {
            try {
                TargetSettings.open();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        color.addActionListener(a -> {
            try {
                ColorChooser.open();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        launch.addActionListener(a -> {
            try {
                MouseMovement.sensitivity = Float.parseFloat(sensitivity.getText());
                Main.fov = Integer.parseInt(fov.getText());
                Main.color = new Color(Main.color.getRed(), Main.color.getGreen(), Main.color.getBlue(), Integer.parseInt(transparency.getText()));
                frame.dispose();
                System.out.printf(String.valueOf(MouseMovement.sensitivity));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Could not get number " + e.getMessage().toLowerCase(), "Error: invalid value", JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Something went wrong:\n" + e.getMessage().toLowerCase(), "Unhandled error:", JOptionPane.ERROR_MESSAGE);
            }

            Settings.save();
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

        JFrame newFrame = new JFrame("Ghost trainer");
        newFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        newFrame.add(new JLabel("   Ghost trainer is running !"));
        Utils.center(newFrame);
        Utils.setIcon(newFrame);
        newFrame.setVisible(true);
        newFrame.setSize(180, 80);

    }
}
