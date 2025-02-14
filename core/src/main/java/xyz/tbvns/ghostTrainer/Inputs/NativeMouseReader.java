package xyz.tbvns.ghostTrainer.Inputs;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import xyz.tbvns.ghostTrainer.Main;

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
        float fx = (x / fixFactor) * sensitivity;
        float fy = (y / fixFactor) * sensitivity;

        Camera camera = Main.camera;

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
            System.load(tempDll.toString());
            tempDll.toFile().deleteOnExit();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load DLL", e);
        }
    }
}
