package xyz.tbvns.ghostTrainer.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.*;
import org.lwjgl.glfw.GLFW;
import xyz.tbvns.ghostTrainer.Main;

import java.awt.*;
import java.io.IOException;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static Lwjgl3Window window;
    public static void main(String[] args) throws Exception {
        //LibGDX stuff
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Main(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("GhostTrainer");
        configuration.useVsync(true);
        configuration.setTransparentFramebuffer(true);
        configuration.setDecorated(false);
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
        configuration.setWindowedMode(Toolkit.getDefaultToolkit().getScreenSize().width - 1, Toolkit.getDefaultToolkit().getScreenSize().height - 1);
        configuration.setWindowIcon("GhostTrainer.png");
        configuration.setBackBufferConfig(0, 0, 0, 255, 0, 16, 16);
        configuration.setWindowListener(new Lwjgl3WindowAdapter() {
            @Override
            public void created(Lwjgl3Window window) {
                super.created(window);
                Lwjgl3Launcher.window = window;
                new Thread(new UpdateThread()).start();
                GLFW.glfwSetWindowAttrib(window.getWindowHandle(), GLFW.GLFW_FLOATING, GLFW.GLFW_TRUE);
                GLFW.glfwSetWindowAttrib(window.getWindowHandle(), GLFW.GLFW_MOUSE_PASSTHROUGH, GLFW.GLFW_TRUE);
            }

            @Override
            public boolean closeRequested() {
                UpdateThread.close = true;
                return true;
            }
        });

        return configuration;
    }
}
