package unet.fcrawler.handlers;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FImage {

    public static BufferedImage resize(BufferedImage img, int width, int height){
        /*
        BufferedImage resized = new BufferedImage(width, height, img.getType());

        double scalex = (double) width / img.getWidth();
        double scaley = (double) height / img.getHeight();

        Image tmp = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        */

        double scalex = (double) width / img.getWidth();
        double scaley = (double) height / img.getHeight();
        double scale = Math.max(scalex, scaley);

        int w = (int) (img.getWidth() * scale);
        int h = (int) (img.getHeight() * scale);

        Image tmp = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);

        BufferedImage resized = new BufferedImage(width, height, img.getType());
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, (width-w)/2, (height-h)/2, null);
        g2d.dispose();

        return resized;
    }
}
