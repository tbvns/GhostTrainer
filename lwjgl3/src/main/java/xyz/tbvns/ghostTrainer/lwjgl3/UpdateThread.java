package xyz.tbvns.ghostTrainer.lwjgl3;

import com.badlogic.gdx.Gdx;
import org.lwjgl.glfw.GLFW;
import xyz.tbvns.ghostTrainer.Constant;
import xyz.tbvns.ghostTrainer.Main;

public class UpdateThread implements Runnable {
    public static boolean close = false;
    public static boolean oldInMenu = false;
    @Override
    public void run() {
        while (!close) {
            if (oldInMenu != Constant.inMenu) {
                if (Constant.inMenu) {
                    GLFW.glfwSetWindowAttrib(Lwjgl3Launcher.window.getWindowHandle(), GLFW.GLFW_MOUSE_PASSTHROUGH, GLFW.GLFW_FALSE);
                } else {
                    GLFW.glfwSetWindowAttrib(Lwjgl3Launcher.window.getWindowHandle(), GLFW.GLFW_MOUSE_PASSTHROUGH, GLFW.GLFW_TRUE);
                    GLFW.glfwFocusWindow(Lwjgl3Launcher.window.getWindowHandle());
                }
                oldInMenu = Constant.inMenu;
            }
            try {
                Thread.sleep(200);
            } catch (Exception e) {}
        }
    }
}
