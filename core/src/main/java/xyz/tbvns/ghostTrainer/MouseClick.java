package xyz.tbvns.ghostTrainer;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import org.jnativehook.GlobalScreen;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class MouseClick implements NativeMouseInputListener {
    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
        List<ModelInstance> toRemove = new ArrayList<>();

        AtomicBoolean lhit = new AtomicBoolean(false);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        Ray ray = Main.camera.getPickRay((float) size.width / 2, (float) size.height / 2);
        Main.models.forEach(m -> {
            boolean hit = Intersector.intersectRaySphere(ray, m.transform.getTranslation(new Vector3()), Main.size / 2, new Vector3());
            if (hit) {
                toRemove.add(m);
                lhit.set(true);
            }
        });
        toRemove.forEach(m -> {
            Main.models.remove(m);
        });

        if (lhit.get()) {
            Random random = new Random();
            int x = random.nextInt(-10, 10);
            int y = random.nextInt(-5, 5);
            int z = random.nextInt(20, 40);
            ModelInstance instance = new ModelInstance(Main.sphere, x, y, z);
            Main.models.add(instance);
        }

        Main.updateCache = true;
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {

    }

    public static class createRunnable implements Runnable {

        @Override
        public void run() {
            GlobalScreen.addNativeMouseMotionListener(new MouseClick());
        }
    }

    public static void create() {
        Thread thread = new Thread(new MouseClick.createRunnable());
        thread.start();
    }

}
