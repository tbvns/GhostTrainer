package xyz.tbvns.ghostTrainer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    ModelBatch batch;
    static Camera camera;
    Environment environment;
    ModelInstance sphereInstance;
    ModelCache cache;
    public static boolean reset = false;
    public static boolean show = false;
    public static boolean updateCache = false;
    public static Model sphere;
    public static List<ModelInstance> models = new ArrayList<>();
    public static int fov = 75;
    public static java.awt.Color color = java.awt.Color.CYAN;
    public static int ballCount = 10;

    btDynamicsWorld world;
    DebugDrawer debugDrawer;

    @Override
    public void create() {
        cache = new ModelCache();

        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        batch = new ModelBatch();

        ModelBuilder modelBuilder = new ModelBuilder();

        sphere = modelBuilder.createSphere(1f, 1f, 1f, 20, 20,
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
            cache.end();
            reset = false;
        }

        if (show) {
            batch.begin(camera);
            batch.render(cache, environment);
            batch.end();
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
