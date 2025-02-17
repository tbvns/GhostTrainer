package xyz.tbvns.ghostTrainer.Inputs;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import xyz.tbvns.ghostTrainer.Ai.ScreenGuesser;
import xyz.tbvns.ghostTrainer.Game.AimeTrainerRenderer;
import xyz.tbvns.ghostTrainer.Game.BallManager;
import xyz.tbvns.ghostTrainer.Main;
import xyz.tbvns.ghostTrainer.Ui.Ui;

public class KeyBoard implements NativeKeyListener {
    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if (nativeKeyEvent.getKeyCode() == 60) {
            if (ScreenGuesser.getScreen().equals("inGame")) {
                AimeTrainerRenderer.setReset(true);
                Main.show = !Main.show;
            } else {
                Ui.showSettings();
            }
        }

        if (nativeKeyEvent.getKeyCode() == 56) {
            BallManager.hit();
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
