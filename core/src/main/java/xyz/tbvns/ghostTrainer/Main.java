package xyz.tbvns.ghostTrainer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import lombok.SneakyThrows;
import xyz.tbvns.ghostTrainer.Ai.ScreenGuesser;
import xyz.tbvns.ghostTrainer.Config.Config;
import xyz.tbvns.ghostTrainer.Config.ConfigManager;
import xyz.tbvns.ghostTrainer.Config.ValorantConfigReader;
import xyz.tbvns.ghostTrainer.Game.AimeTrainerRenderer;
import xyz.tbvns.ghostTrainer.Game.LogoRenderer;
import xyz.tbvns.ghostTrainer.Inputs.KeyBoard;
import xyz.tbvns.ghostTrainer.Inputs.MouseClick;
import xyz.tbvns.ghostTrainer.Inputs.NativeMouseReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    public static boolean show = false;

    @SneakyThrows
    @Override
    public void create() {
        ValorantConfigReader.start();
        ConfigManager.setUp();
        FlatDarculaLaf.setup();
        ScreenGuesser.start();

        //2D
        LogoRenderer.create();

        //3D
        AimeTrainerRenderer.create();

        //MouseMovement.create();
        new Thread(() -> {
            KeyBoard.create();
            MouseClick.create();
            NativeMouseReader.hook();
        }).start();

        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
    }

    @Override
    public void render() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT | Gdx.gl20.GL_DEPTH_BUFFER_BIT);

        if (show && ScreenGuesser.getScreen().equals("inGame")) {
            LogoRenderer.render();
            AimeTrainerRenderer.render();
        } else {
            show = false;
        }
    }

    @Override
    public void dispose() {
        AimeTrainerRenderer.dispose();
        LogoRenderer.dispose();

        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        }

        NativeMouseReader.unhook();
    }

    @Override
    public void resize(int width, int height) {

    }



    @Override
    public void pause() {

    }



    @Override
    public void resume() {

    }
}
