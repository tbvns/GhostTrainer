package xyz.tbvns.ghostTrainer.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3WindowAdapter;
import org.lwjgl.glfw.GLFW;
import xyz.tbvns.ghostTrainer.Main;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        if (args.length == 0) {
            Resources.load();
            Resources.start();
            return;
        }

        Settings.load();
        Launcher.open();

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
        //// Limits FPS to the refresh rate of the currently active monitor.
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
        //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
        //// useful for testing performance, but can also be very stressful to some hardware.
        //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.
        configuration.setWindowedMode(Toolkit.getDefaultToolkit().getScreenSize().width - 1, Toolkit.getDefaultToolkit().getScreenSize().height - 1);
        configuration.setWindowIcon("GhostTrainer.png");

        configuration.setWindowListener(new Lwjgl3WindowAdapter() {
            @Override
            public void created(Lwjgl3Window window) {
                super.created(window);
                GLFW.glfwSetWindowAttrib(window.getWindowHandle(), GLFW.GLFW_FLOATING, GLFW.GLFW_TRUE);
                GLFW.glfwSetWindowAttrib(window.getWindowHandle(), GLFW.GLFW_MOUSE_PASSTHROUGH, GLFW.GLFW_TRUE);
            }
        });

        return configuration;
    }
}
