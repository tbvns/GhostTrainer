package xyz.tbvns.ghostTrainer.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import lombok.Getter;
import lombok.Setter;
import xyz.tbvns.ghostTrainer.Config.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AimeTrainerRenderer {
    private static ModelBatch batch;
    private static ModelCache cache;
    @Getter
    private static Model sphere;
    @Getter
    private static List<ModelInstance> models = new ArrayList<>();

    private static Environment environment;
    @Getter
    private static Camera camera;
    @Getter @Setter
    private static boolean reset = false;
    @Getter @Setter
    private static boolean updateCache = false;

    public static void create() {
        cache = new ModelCache();
        batch = new ModelBatch();

        camera = new PerspectiveCamera(Config.fov,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight());

        ModelBuilder modelBuilder = new ModelBuilder();

        sphere = modelBuilder.createSphere(Config.size, Config.size, Config.size, 20, 20,
            new Material(ColorAttribute.createDiffuse(new Color(Config.r / 255, Config.g / 255, Config.b / 255, Config.a / 255))),
            VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
        );

        cache.begin(camera);
        for (int i = 0; i <= Config.ballCount; i++) {
            Random random = new Random();
            int x = random.nextInt(-10, 10);
            int y = random.nextInt(-5, 5);
            int z = random.nextInt(20, 40);
            ModelInstance instance = new ModelInstance(sphere, x, y, z);
            cache.add(instance);
            models.add(instance);
        }
        cache.end();

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
    }

    public static void render() {
        if (reset) {
            camera.direction.set(0, -30, 360);
            cache.dispose();
            models = new ArrayList<>();
            cache = new ModelCache();
            cache.begin(camera);
            for (int i = 0; i <= Config.ballCount; i++) {
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

        if (updateCache) {
            cache.dispose();
            cache = new ModelCache();
            cache.begin(camera);
            cache.add(models);
            cache.end();
            updateCache = false;
        }

        camera.update();

        batch.begin(camera);
        batch.render(cache, environment);
        batch.end();
    }

    public static void dispose() {
        batch.dispose();
        sphere.dispose();
        cache.dispose();
        models.clear();
    }

    public static void reload() {
        Gdx.app.postRunnable(() -> {
            sphere.dispose();
            ModelBuilder modelBuilder = new ModelBuilder();
            sphere = modelBuilder.createSphere(Config.size, Config.size, Config.size, 20, 20,
                new Material(ColorAttribute.createDiffuse(new Color(Config.r / 255, Config.g / 255, Config.b / 255, Config.a / 255))),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
            );

            reset = true;
            updateCache = true;
        });
    }
}
