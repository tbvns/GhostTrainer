package xyz.tbvns.ghostTrainer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import org.jnativehook.GlobalScreen;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;

import java.awt.*;

public class MouseMovement implements NativeMouseMotionListener {

    public static int OldMousePosX = 0;
    public static int OldMousePosY = 0;

    public static float fixFactor = 13.7f;
    public static float sensitivity = 1f;

    @Override
    public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {
        float MouseMoveX = nativeMouseEvent.getX() - OldMousePosX;
        float MouseMoveY = nativeMouseEvent.getY() - OldMousePosY;

        OldMousePosX = nativeMouseEvent.getX();
        OldMousePosY = nativeMouseEvent.getY();
        Camera camera = Main.camera;

        MouseMoveX = (MouseMoveX / fixFactor) * sensitivity;
        MouseMoveY = (MouseMoveY / fixFactor) * sensitivity;

        if (nativeMouseEvent.getX() < 0) {
            MouseMoveX = (nativeMouseEvent.getX() / fixFactor) * sensitivity;
        } else
        if (nativeMouseEvent.getX() > Toolkit.getDefaultToolkit().getScreenSize().width) {
            MouseMoveX = (((nativeMouseEvent.getX() - Toolkit.getDefaultToolkit().getScreenSize().width)  / fixFactor) * sensitivity);
        }

        if (nativeMouseEvent.getY() < 0) {
            MouseMoveY = (nativeMouseEvent.getY() / fixFactor) * sensitivity;
        } else
        if (nativeMouseEvent.getY() > Toolkit.getDefaultToolkit().getScreenSize().height) {
            MouseMoveY = (((nativeMouseEvent.getY() - Toolkit.getDefaultToolkit().getScreenSize().height)  / fixFactor) * sensitivity);
        }

        Vector3 tmp = new Vector3();
        camera.direction.rotate(camera.up, -MouseMoveX);
        tmp.set(camera.direction).crs(camera.up).nor();
        camera.direction.rotate(tmp, -MouseMoveY);
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {
        float MouseMoveX = nativeMouseEvent.getX() - OldMousePosX;
        float MouseMoveY = nativeMouseEvent.getY() - OldMousePosY;

        OldMousePosX = nativeMouseEvent.getX();
        OldMousePosY = nativeMouseEvent.getY();
        Camera camera = Main.camera;

        MouseMoveX = (MouseMoveX / fixFactor) * sensitivity;
        MouseMoveY = (MouseMoveY / fixFactor) * sensitivity;

        if (nativeMouseEvent.getX() < 0) {
            MouseMoveX = (nativeMouseEvent.getX() / fixFactor) * sensitivity;
        } else
        if (nativeMouseEvent.getX() > Toolkit.getDefaultToolkit().getScreenSize().width) {
            MouseMoveX = (((nativeMouseEvent.getX() - Toolkit.getDefaultToolkit().getScreenSize().width)  / fixFactor) * sensitivity);
        }

        if (nativeMouseEvent.getY() < 0) {
            MouseMoveY = (nativeMouseEvent.getY() / fixFactor) * sensitivity;
        } else
        if (nativeMouseEvent.getY() > Toolkit.getDefaultToolkit().getScreenSize().height) {
            MouseMoveY = (((nativeMouseEvent.getY() - Toolkit.getDefaultToolkit().getScreenSize().height)  / fixFactor) * sensitivity);
        }

        Vector3 tmp = new Vector3();
        camera.direction.rotate(camera.up, -MouseMoveX);
        tmp.set(camera.direction).crs(camera.up).nor();
        camera.direction.rotate(tmp, -MouseMoveY);
    }

    public static class createRunnable implements Runnable {

        @Override
        public void run() {
            GlobalScreen.addNativeMouseMotionListener(new MouseMovement());
        }
    }

    public static void create() {
        Thread thread = new Thread(new createRunnable());
        thread.start();
    }
}
