package xyz.tbvns.ghostTrainer.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class LogoRenderer {
    private static SpriteBatch spriteBatch;
    private static Sprite crossair;
    private static Sprite ghost;
    private static BitmapFont titleFont;
    private static BitmapFont versionFont;
    private static int ghostRotation = 1;

    public static void create() {
        spriteBatch = new SpriteBatch();
        crossair = new Sprite(new Texture(Gdx.files.internal("GhostTrainerCrossair.png")));
        ghost = new Sprite(new Texture(Gdx.files.internal("GhostTrainerGhost.png")));
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
    }

    public static void render() {
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

    public static void dispose() {
        spriteBatch.dispose();
        ghost.getTexture().dispose();
        crossair.getTexture().dispose();
        titleFont.dispose();
        versionFont.dispose();
    }
}
