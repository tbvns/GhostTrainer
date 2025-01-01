package xyz.tbvns.ghostTrainer.Ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import xyz.tbvns.ghostTrainer.Main;

public class Ui {
    public static VerticalGroup getMain() {
        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.addActor(new Label("In game sensitivity:", new Label.LabelStyle(Main.titleFont, Color.BLACK)));
        //verticalGroup.addActor(new TextField("", new TextField.TextFieldStyle()));
        verticalGroup.addActor(new Label("Field of view:", new Label.LabelStyle(Main.titleFont, Color.BLACK)));
        //verticalGroup.addActor(new TextField("", new TextField.TextFieldStyle()));
        verticalGroup.center();
        verticalGroup.setBounds(500, 500, 500, 500);
        return verticalGroup;
    }
}
