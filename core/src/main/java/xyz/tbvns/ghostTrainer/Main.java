package xyz.tbvns.ghostTrainer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    ModelBatch batch;
    SpriteBatch menuBatch;
    static Camera camera;
    Environment environment;
    ModelCache cache;
    public static boolean reset = false;
    public static boolean show = false;
    public static boolean updateCache = false;
    public static Model sphere;
    public static List<ModelInstance> models = new ArrayList<>();
    public static int fov = 75;
    public static java.awt.Color color = java.awt.Color.CYAN;
    public static int ballCount = 10;
    public static float size = 1f;
    SpriteBatch spriteBatch;
    Sprite crossair;
    Sprite ghost;
    BitmapFont titleFont;
    BitmapFont versionFont;
    int ghostRotation = 1;

    @Override
    public void create() {
        //2D
        spriteBatch = new SpriteBatch();
        menuBatch = new SpriteBatch();
        crossair = new Sprite(new Texture(Gdx.files.internal("GhostTrainerCrossair.png")));
        ghost = new Sprite(new Texture(Gdx.files.internal("GhostTrainerGhost.png")));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        crossair.setBounds(0, 0, crossair.getTexture().getWidth() / 2, crossair.getTexture().getHeight() / 2);
        crossair.setOrigin(crossair.getWidth() / 2, crossair.getHeight() / 2);

        ghost.setBounds(0, 0, ghost.getTexture().getWidth() / 2, ghost.getTexture().getHeight() / 2);
        ghost.setOrigin(ghost.getWidth() / 2, ghost.getHeight() / 2);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Aero Matics Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40; // font size
        titleFont = generator.generateFont(parameter);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Aero Matics Light.ttf"));
        parameter.size = 13;
        versionFont = generator.generateFont(parameter);


        //3D
        cache = new ModelCache();

        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        batch = new ModelBatch();

        ModelBuilder modelBuilder = new ModelBuilder();

        sphere = modelBuilder.createSphere(size, size, size, 20, 20,
            new Material(ColorAttribute.createDiffuse(new Color((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, (float) color.getAlpha() / 255))),
            VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
        );

        cache.begin(camera);
        for (int i = 0; i <= ballCount; i++) {
            Random random = new Random();
            int x = random.nextInt(-10, 10);
            int y = random.nextInt(-5, 5);
            int z = random.nextInt(20, 40);
            ModelInstance instance = new ModelInstance(sphere, x, y, z);
            cache.add(instance);
            models.add(instance);
        }
        cache.end();

        camera = new PerspectiveCamera(fov,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight());

        camera.position.set(0f,0f,3f);
        camera.lookAt(0f,0f,0f);
        camera.projection.set(camera.projection);

        camera.near = 0.1f;
        camera.far = 300.0f;

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        }
        MouseMovement.create();
        KeyBoard.create();
        MouseClick.create();
    }

    @Override
    public void render() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT | Gdx.gl20.GL_DEPTH_BUFFER_BIT);

        camera.update();

        if (reset) {
            camera.direction.set(0, -30, 360);
            cache.dispose();
            models = new ArrayList<>();
            cache = new ModelCache();
            cache.begin(camera);
            for (int i = 0; i <= ballCount; i++) {
                Random random = new Random();
                int x = random.nextInt(-10, 10);
                int y = random.nextInt(-5, 5);
                int z = random.nextInt(20, 40);
                ModelInstance instance = new ModelInstance(sphere, x, y, z);
                cache.add(instance);
                models.add(instance);
            }
            MouseMovement.OldMousePosX = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
            MouseMovement.OldMousePosY = Toolkit.getDefaultToolkit().getScreenSize().height / 2;

            cache.end();
            reset = false;
        }

        if (Constant.inMenu) {
            menuBatch.begin();
            titleFont.draw(menuBatch, "Menu \\o/", 200, 200);
            menuBatch.end();
        }

        if (show) {
            batch.begin(camera);
            batch.render(cache, environment);
            batch.end();

            spriteBatch.begin();
            ghost.draw(spriteBatch);
            crossair.draw(spriteBatch);
            titleFont.draw(spriteBatch, "Ghost Trainer", crossair.getWidth() + 10, crossair.getHeight() - titleFont.getLineHeight());
            versionFont.draw(spriteBatch, "Beta version", crossair.getWidth() + 10, crossair.getHeight() - titleFont.getLineHeight() - 30);
            spriteBatch.end();

            crossair.rotate(10f * Gdx.graphics.getDeltaTime());
            ghost.rotate(5f * ghostRotation * Gdx.graphics.getDeltaTime());

            if (ghost.getRotation() > 10) {
                ghostRotation = -1;
            } else if (ghost.getRotation() < 0) {
                ghostRotation = 1;
            }
        }

        if (updateCache) {
            cache.dispose();
            cache = new ModelCache();
            cache.begin(camera);
            cache.add(models);
            cache.end();
            updateCache = false;
        }

    }

    @Override

    public void dispose() {
        batch.dispose();
        spriteBatch.dispose();
        ghost.getTexture().dispose();
        crossair.getTexture().dispose();

        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        }
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
