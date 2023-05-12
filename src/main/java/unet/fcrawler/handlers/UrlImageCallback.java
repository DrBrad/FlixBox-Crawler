package unet.fcrawler.handlers;

import java.awt.image.BufferedImage;

public interface UrlImageCallback {

    void onImageResponse(BufferedImage image);
}
