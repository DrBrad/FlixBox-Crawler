package unet.fcrawler.crawl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import unet.fcrawler.libs.json.variables.JsonArray;
import unet.fcrawler.libs.json.variables.JsonObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;

public class CastCrawl {

    private URI url;
    private File folder;

    public CastCrawl(URI url, File folder){
        this.url = url;
        this.folder = folder;
    }

    public JsonArray getCast()throws NoSuchAlgorithmException, IOException {
        Document doc = Jsoup.connect(url.toString()).get();

        String script = doc.getElementById("__NEXT_DATA__").html();

        JsonArray cast = new JsonObject(script.getBytes())
                .getJsonObject("props")
                .getJsonObject("pageProps")
                .getJsonObject("mainColumnData")
                .getJsonObject("cast")
                .getJsonArray("edges");

        for(int i = 0; i < cast.size(); i++){
            JsonObject c = cast.getJsonObject(i).getJsonObject("node").getJsonObject("name");
            String name = c.getJsonObject("nameText").getString("text");
            String img = c.getJsonObject("primaryImage").getString("url");
            System.out.println(name);
            System.out.println(img);
            System.out.println();
        }

        /*
        - SAME LEVEL AS name
        				"characters":[
					{
						"name":"Lieutenant Jim Dangle",
						"__typename":"Character",
					},
					{
						"name":"Man Shot by Wiegel",
						"__typename":"Character",
					},
					{
						"name":"RV Driver",
						"__typename":"Character",
					},
				],
        */

        return null;
        /*
        Elements episodes = doc.getElementsByClass("list detail eplist").get(0).select("a[itemprop=url]");

        JsonArray j = new JsonArray();

        for(int i = 0; i < episodes.size(); i++){

            /*
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

            j.add(z);*/
        //}

        //return j;
    }
}
