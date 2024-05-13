package gui.init;

import java.net.URL;

public class ImageLoader {

    private static final String ASSETS_PATH = "/assets/";

    public static URL loadImage(String imageName) {
        final var imagePath = ASSETS_PATH + imageName;
        final var imageUrl = ImageLoader.class.getResource(imagePath);

        if (imageUrl == null) {
            throw new IllegalArgumentException("Image not found: " + imagePath);
        }

        return imageUrl;
    }

}
