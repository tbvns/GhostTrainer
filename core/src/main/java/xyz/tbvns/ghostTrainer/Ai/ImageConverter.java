package xyz.tbvns.ghostTrainer.Ai;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageConverter {
    public static BufferedImage convertToBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            BufferedImage bufferedImage = (BufferedImage) image;
            if (bufferedImage.getTransparency() == BufferedImage.OPAQUE) {
                return bufferedImage;
            }
        }

        // Ensure the image is fully loaded
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Image dimensions are invalid. Ensure the image is fully loaded.");
        }

        // Create a new opaque BufferedImage (TYPE_INT_RGB)
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Draw the original image onto the new BufferedImage
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return bufferedImage;
    }
}
