package xyz.tbvns.ghostTrainer.Inputs;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import xyz.tbvns.ghostTrainer.Config.Config;
import xyz.tbvns.ghostTrainer.Constant;
import xyz.tbvns.ghostTrainer.Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class KeyBoard implements NativeKeyListener {
    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }
    static long last = 0;

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
//        if (nativeKeyEvent.getKeyCode() == 22) {
//            Constant.inMenu = !Constant.inMenu;
//            Main.show = false;
//        }


        if (nativeKeyEvent.getKeyCode() == 1) {
            Constant.inMenu = false;
            if (Main.show) {
                Main.show = false;
            } else if (System.currentTimeMillis() - last < 200) {
                Main.show = true;
                Main.reset = true;
            }
            last = System.currentTimeMillis();
        }
        List<ModelInstance> toRemove = new ArrayList<>();

        if (nativeKeyEvent.getKeyCode() == 56) {
            AtomicBoolean lhit = new AtomicBoolean(false);
            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
            Ray ray = Main.camera.getPickRay((float) size.width / 2, (float) size.height / 2);
            Main.models.forEach(m -> {
                boolean hit = Intersector.intersectRaySphere(ray, m.transform.getTranslation(new Vector3()), Config.size / 2, new Vector3());
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
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }

    public static class createRunnable implements Runnable {

        @Override
        public void run() {
            GlobalScreen.addNativeKeyListener(new KeyBoard());
        }
    }

    public static void create() {
        Thread thread = new Thread(new KeyBoard.createRunnable());
        thread.start();
    }
}
