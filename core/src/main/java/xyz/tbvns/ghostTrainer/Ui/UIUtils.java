package xyz.tbvns.ghostTrainer.Ui;

import java.awt.*;

public class UIUtils {
    public static void center(Frame frame) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screen.width / 2 - frame.getWidth() / 2, screen.height / 2 - frame.getHeight() / 2);
    }
}
