package xyz.tbvns.ghostTrainer.Ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import xyz.tbvns.ghostTrainer.Config.Config;
import xyz.tbvns.ghostTrainer.Main;

public class Ui {
    public static VerticalGroup getMain() {
        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.addActor(new Label("In game sensitivity:", new Label.LabelStyle(Main.titleFont, Color.BLACK)));
        //verticalGroup.addActor(new TextField("", new TextField.TextFieldStyle()));
        verticalGroup.addActor(new Label("Field of view: " + Config.fov, new Label.LabelStyle(Main.titleFont, Color.BLACK)));
        //verticalGroup.addActor(new TextField("", new TextField.TextFieldStyle()));
        verticalGroup.center();
        verticalGroup.setBounds(500, 500, 500, 500);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        NinePatch patch1 = new NinePatch(new Texture("UI/button1-9p.png"), 6, 6, 6, 6);
        NinePatch patch2 = new NinePatch(new Texture("UI/button2-9p.png"), 6, 6, 6, 6);
        textButtonStyle.font = new BitmapFont();
        textButtonStyle.over = new NinePatchDrawable(patch1);
        textButtonStyle.up = new NinePatchDrawable(patch2);
        TextButton button = new TextButton("Button1Button1Button1Button1Button1Button1u", textButtonStyle);
        verticalGroup.addActor(button);
        button.addListener(event -> {
            System.out.println("Hello worldu");
           return true;
        });
        return verticalGroup;
    }
}
