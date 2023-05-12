package unet.fcrawler.commands;

import unet.fcrawler.crawl.EpisodeCrawl;
import unet.fcrawler.handlers.CheckSum;
import unet.fcrawler.handlers.UrlImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.util.List;

import static unet.fcrawler.handlers.FImage.resize;

public class Resize {

    public Resize(List<String> cmd)throws Exception {
        int w;
        int h;
        File f;

        switch(cmd.get(0).toLowerCase()){
            case "landscape":
                w = 960;
                h = 540;
                f = new File(cmd.get(1));
                break;

            case "portrait":
                w = 480;
                h = 720;
                f = new File(cmd.get(1));
                break;

            default:
                System.err.println("No valid type entered.");
                return;
        }

        BufferedImage i = ImageIO.read(f);
        i = resize(i, w, h);
        ImageIO.write(i, "jpg", new File(f.getParent(), f.getName().substring(0, f.getName().lastIndexOf("."))+".jpg"));

        System.out.println("Image resized.");
    }
}
