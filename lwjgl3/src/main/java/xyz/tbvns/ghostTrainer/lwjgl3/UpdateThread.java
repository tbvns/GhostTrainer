package xyz.tbvns.ghostTrainer.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import org.lwjgl.glfw.GLFW;
import xyz.tbvns.ghostTrainer.Constant;

public class UpdateThread implements Runnable {
    public static boolean close = false;
    public static boolean oldInMenu = false;
    @Override
    public void run() {
        while (!close) {
            if (oldInMenu != Constant.inMenu) {
                if (Constant.inMenu) {
                    GLFW.glfwSetWindowAttrib(Lwjgl3Launcher.window.getWindowHandle(), GLFW.GLFW_MOUSE_PASSTHROUGH, GLFW.GLFW_FALSE);
                    GLFW.glfwFocusWindow(Lwjgl3Launcher.window.getWindowHandle());
                    Gdx.graphics.setCursor(Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("cursor.png")), 0, 0));
                } else {
                    GLFW.glfwSetWindowAttrib(Lwjgl3Launcher.window.getWindowHandle(), GLFW.GLFW_MOUSE_PASSTHROUGH, GLFW.GLFW_TRUE);
                    Gdx.input.setCursorCatched(false);
                }
                oldInMenu = Constant.inMenu;
            }
            try {
                Thread.sleep(200);
            } catch (Exception e) {}
        }
    }
}
