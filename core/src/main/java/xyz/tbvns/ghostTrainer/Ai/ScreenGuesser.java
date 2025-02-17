package xyz.tbvns.ghostTrainer.Ai;

import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.transform.Resize;
import ai.djl.modality.cv.translator.ImageClassificationTranslator;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import lombok.Getter;
import xyz.tbvns.ghostTrainer.Config.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenGuesser {
    @Getter
    private static String screen = "null";
    private static boolean active = false;
    private static Thread thread;
    public static Runnable runnable = () -> {
        Path modelDir = Paths.get(AIManager.extract());

        JFrame frame = new JFrame("Image viewer");
        JPanel panel = new JPanel();
        frame.setContentPane(panel);
        JLabel label = new JLabel();
        panel.add(label);
        frame.setVisible(true);
        frame.setSize(400, 300);

        Criteria<Image, Classifications> criteria = Criteria.builder()
            .setTypes(Image.class, Classifications.class)
            .optModelUrls(modelDir.toUri().toString())
            .optEngine("TensorFlow")
            .optOption("Tags", "serve")
            .optOption("SignatureDefKey", "serving_default")
            .optTranslator(ImageClassificationTranslator.builder()
                .addTransform(new Resize(224, 224))
                .addTransform(array -> array.div(255f))
                .build())
            .build();

        try (ZooModel<Image, Classifications> model = ModelZoo.loadModel(criteria);
             Predictor<Image, Classifications> predictor = model.newPredictor()) {
            while (active) {
                Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                BufferedImage capture = new Robot().createScreenCapture(screenRect);
                java.awt.Image captureImg = capture.getScaledInstance(224, 244, java.awt.Image.SCALE_FAST);

                label.setIcon(new ImageIcon(ImageConverter.convertToBufferedImage(captureImg)));
                frame.invalidate();

                Image image = ImageFactory.getInstance().fromImage(capture);

                Classifications result = predictor.predict(image);
                screen = result.best().getClassName();

                Thread.sleep(Config.AIDelay);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

    public static void start(){
        active = true;
        thread = new Thread(runnable){{
            setName("AiThread");
            setDaemon(true);
            start();
        }};
    }

    public static void stop() {
        active = false;
    }
}
