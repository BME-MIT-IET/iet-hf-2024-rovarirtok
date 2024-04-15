package GUI.init;

import java.net.URL;

public class ImageLoader {

    private static final String assetsPath = "/assets/";

    public static URL loadImage(String imageName) {
        final var imagePath = assetsPath + imageName;
        final var imageUrl = ImageLoader.class.getResource(imagePath);

        if (imageUrl == null) {
            throw new RuntimeException("Image not found: " + imagePath);
        }

        return imageUrl;
    }

}
