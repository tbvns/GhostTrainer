package xyz.tbvns.ghostTrainer.Inputs;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;
import xyz.tbvns.ghostTrainer.Config.Config;
import xyz.tbvns.ghostTrainer.Game.AimeTrainerRenderer;
import xyz.tbvns.ghostTrainer.Game.BallManager;
import xyz.tbvns.ghostTrainer.Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class MouseClick implements NativeMouseInputListener {
    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {
        BallManager.hit();
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
            GlobalScreen.addNativeMouseListener(new MouseClick());
        }
    }

    public static void create() {
        Thread thread = new Thread(new MouseClick.createRunnable());
        thread.start();
    }

}
