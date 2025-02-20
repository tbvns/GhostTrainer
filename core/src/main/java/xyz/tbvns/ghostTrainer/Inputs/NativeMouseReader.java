package xyz.tbvns.ghostTrainer.Inputs;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.formdev.flatlaf.FlatDarculaLaf;
import xyz.tbvns.ghostTrainer.Game.AimeTrainerRenderer;
import xyz.tbvns.ghostTrainer.Main;
import xyz.tbvns.ghostTrainer.Ui.ErrorHandler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class NativeMouseReader {
    public static native void hook();
    public static native void unhook();

    public static float fixFactor = 14f;
    public static float sensitivity = 1f;

    public static void update(int x, int y, boolean left, boolean right) {
//        System.out.println(x + "  " + y + "  " + left + "   " + right);
        float fx = (x / fixFactor) * sensitivity / Toolkit.getDefaultToolkit().getScreenSize().width * 1920;
        float fy = (y / fixFactor) * sensitivity / Toolkit.getDefaultToolkit().getScreenSize().height * 1080;

        Camera camera = AimeTrainerRenderer.getCamera();

        Vector3 tmp = new Vector3();
        camera.direction.rotate(camera.up, -fx);
        tmp.set(camera.direction).crs(camera.up).nor();
        camera.direction.rotate(tmp, -fy);
    };

    static {
        try {
            InputStream in = NativeMouseReader.class.getResourceAsStream("/MouseEventDLL.dll");
            Path tempDll = Files.createTempFile("MouseEventDLL", ".dll");
            System.out.println(Files.copy(in, tempDll, StandardCopyOption.REPLACE_EXISTING));
            while (true) {
                try {
                    System.load(tempDll.toString());
                    break;
                } catch (UnsatisfiedLinkError e) {
                    ErrorHandler.handleWithRetryAndUrl(
                        new Exception(e),
                        false,
                        "https://developer.microsoft.com/en-us/windows/downloads/windows-sdk/",
                        "You may need to download the Windows SDK to fix this issue. Click the Open URL button to go to the download page."

                    );
                }
            }
            tempDll.toFile().deleteOnExit();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load DLL", e);
        }
    }
}
