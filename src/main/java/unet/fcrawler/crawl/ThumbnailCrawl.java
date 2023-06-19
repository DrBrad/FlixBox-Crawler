package unet.fcrawler.crawl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import unet.fcrawler.handlers.CheckSum;
import unet.fcrawler.handlers.UrlImage;
import unet.fcrawler.libs.json.variables.JsonArray;
import unet.fcrawler.libs.json.variables.JsonObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;

public class ThumbnailCrawl {

    private static final String[] months = {
            "Jan.",
            "Feb.",
            "Mar.",
            "Apr.",
            "May",
            "June",
            "July",
            "Aug.",
            "Sept.",
            "Oct.",
            "Nov.",
            "Dec."
    };

    private URI url;
    private File folder;

    public ThumbnailCrawl(URI url, File folder){
        this.url = url;
        this.folder = folder;
    }

    public void getThumbnails()throws IOException {
        Document doc = Jsoup.connect(url.toString()).get();
        Elements episodes = doc.getElementsByClass("list detail eplist").get(0).select("a[itemprop=url]");

        for(int i = 0; i < episodes.size(); i++){
            BufferedImage bi = new UrlImage(episodes.get(i).getElementsByTag("img").get(0).attr("src")).get();
            File tmp = new File(folder, "thumb-"+(i+1)+".jpg");
            ImageIO.write(bi, "jpg", tmp);
            System.out.println("Saved episode "+(i+1)+" thumbnail");
        }
    }
}
