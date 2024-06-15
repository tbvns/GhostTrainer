package xyz.tbvns.ghostTrainer;

import com.badlogic.gdx.math.Vector3;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.DirectAndRawInputEnvironmentPlugin;
import net.java.games.input.Mouse;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MouseController {
    static Controller[] ca;
    public static float[] poll() {
        float[] xy = new float[2];

        ca = DirectAndRawInputEnvironmentPlugin.getDefaultEnvironment().getControllers();
        for (int i = 0; i < ca.length; i++) {
            Controller controller = ca[i];
            if (controller instanceof Mouse) {
                Mouse mouse = (Mouse) controller;
                mouse.poll();
                xy[0] += mouse.getX().getPollData();
                xy[1] += mouse.getY().getPollData();
            }

        }

        return xy;
    }

    public static void updateCamera() {
        float[] xy = MouseController.poll();
        Main.camera.rotate(new Vector3(0, -1, 0), xy[0]);
        Main.camera.rotate(new Vector3(1, 0, 0), xy[1]);
        Main.camera.update();
    }

    public static JComboBox createComboBox() {
        ca = ControllerEnvironment.getDefaultEnvironment().getControllers();
        List<String> names = new ArrayList<>();
        for (int i = 0; i < ca.length; i++) {
            Controller controller = ca[i];
            if (controller.getType().equals(Controller.Type.MOUSE)) {
                names.add(controller.getName());
                System.out.println(controller.getName());
            }
        }
        return new JComboBox<>(names.toArray());
    }
}
