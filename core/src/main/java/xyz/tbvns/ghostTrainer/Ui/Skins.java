package xyz.tbvns.ghostTrainer.Ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Skins {
    public static Color textColor = new Color(1, 1, 1, 1);

    //FONT:
    //Generate font
    public static BitmapFont labelFont;
    static {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Aero Matics Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20; // font size

        labelFont = generator.generateFont(parameter);
    }
    public static BitmapFont noteFont;
    static {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Aero Matics Light.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 10; // font size

        noteFont = generator.generateFont(parameter);
    }

    public static Label.LabelStyle labelStyle = new Label.LabelStyle(labelFont, textColor);
    //public static TextField.TextFieldStyle textAreaStyle = new TextField.TextFieldStyle(labelFont, textColor, )
}
