package xyz.tbvns.ghostTrainer.Game;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import xyz.tbvns.ghostTrainer.Config.Config;
import xyz.tbvns.ghostTrainer.Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class BallManager {
    public static void hit() {
        List<ModelInstance> toRemove = new ArrayList<>();

        AtomicBoolean lhit = new AtomicBoolean(false);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        Ray ray = AimeTrainerRenderer.getCamera().getPickRay((float) size.width / 2, (float) size.height / 2);
        AimeTrainerRenderer.getModels().forEach(m -> {
            boolean hit = Intersector.intersectRaySphere(ray, m.transform.getTranslation(new Vector3()), Config.size / 2, new Vector3());
            if (hit) {
                toRemove.add(m);
                lhit.set(true);
            }
        });
        toRemove.forEach(m -> {
            AimeTrainerRenderer.getModels().remove(m);
        });

        if (lhit.get()) {
            Random random = new Random();
            int x = random.nextInt(-10, 10);
            int y = random.nextInt(-5, 5);
            int z = random.nextInt(20, 40);
            ModelInstance instance = new ModelInstance(AimeTrainerRenderer.getSphere(), x, y, z);
            AimeTrainerRenderer.getModels().add(instance);
        }

        AimeTrainerRenderer.setUpdateCache(true);
    }
}
