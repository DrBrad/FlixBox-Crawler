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

public class EpisodeCrawl {

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

    public EpisodeCrawl(URI url, File folder){
        this.url = url;
        this.folder = folder;
    }

    public JsonArray getEpisodes()throws NoSuchAlgorithmException, IOException {
        Document doc = Jsoup.connect(url.toString()).get();
        Elements episodes = doc.getElementsByClass("list detail eplist").get(0).select("a[itemprop=url]");

        JsonArray j = new JsonArray();

        for(int i = 0; i < episodes.size(); i++){
            BufferedImage bi = new UrlImage(episodes.get(i).getElementsByTag("img").get(0).attr("src")).get();
            File tmp = new File(folder, "tmp.jpg");
            ImageIO.write(bi, "jpg", tmp);

            String checksum = new CheckSum(tmp).execute();

            String location = episodes.get(i).attr("href");

            if(!location.startsWith("http")){
                location = url.getScheme()+"://"+url.getHost()+((location.charAt(0) == '/') ? "" : "/")+location;
            }

            JsonObject z = getEpisode(location);
            z.put("thumbnail", checksum);

            System.err.println("("+(i+1)+"/"+episodes.size()+") "+z.getString("title")+" : COMPLETE");

            j.add(z);
        }

        return j;
    }

    public JsonObject getEpisode(String url)throws IOException {
        Document doc = Jsoup.connect(url).get();
        String script = doc.getElementById("__NEXT_DATA__").html();

        JsonObject response = new JsonObject(script.getBytes());

        //props pageProps aboveTheFoldData series titleText
        String title = response.getJsonObject("props")
                .getJsonObject("pageProps")
                .getJsonObject("aboveTheFoldData")
                .getJsonObject("titleText")
                .getString("text");

        String description = response.getJsonObject("props")
                .getJsonObject("pageProps")
                .getJsonObject("aboveTheFoldData")
                .getJsonObject("plot")
                .getJsonObject("plotText")
                .getString("plainText");

        String time = response.getJsonObject("props")
                .getJsonObject("pageProps")
                .getJsonObject("aboveTheFoldData")
                .getJsonObject("runtime")
                .getJsonObject("displayableProperty")
                .getJsonObject("value")
                .getString("plainText");

        JsonObject release = response.getJsonObject("props")
                .getJsonObject("pageProps")
                .getJsonObject("aboveTheFoldData")
                .getJsonObject("releaseDate");
/*
        String thumbnail = response.getJsonObject("props")
                .getJsonObject("pageProps")
                .getJsonObject("mainColumnData")
                .getJsonObject("titleMainImages")
                .getJsonArray("edges")
                .getJsonObject(1)
                .getJsonObject("node")
                .getString("url");
*/


        String year = release.getInteger("day")+" "+months[release.getInteger("month")]+" "+release.getInteger("year");

        JsonObject j = new JsonObject();
        j.put("title", title);
        j.put("description", description);
        j.put("time", time);
        j.put("year", year);

        return j;
    }
}
