package unet.fcrawler.commands;

import unet.fcrawler.crawl.CastCrawl;
import unet.fcrawler.crawl.EpisodeCrawl;
import unet.fcrawler.crawl.ThumbnailCrawl;

import java.io.File;
import java.net.URI;
import java.util.List;

public class Crawl {

    public Crawl(List<String> cmd)throws Exception {

        switch(cmd.get(0).toLowerCase()){
            case "episodes":
                //if(args[2].equalsIgnoreCase("-r")){
                //    new EpisodeCrawl(new URI(args[3]), new File(args[4]), true);
                //}else{
                    System.out.println(new EpisodeCrawl(new URI(cmd.get(1)), new File(cmd.get(2))).getEpisodes());
                //}
                break;

            case "cast":
                System.out.println(new CastCrawl(new URI(cmd.get(1)), new File(cmd.get(2))).getCast());
                break;

            case "episode":

                break;


            case "thumbnails":
                new ThumbnailCrawl(new URI(cmd.get(1)), new File(cmd.get(2))).getThumbnails();
                System.out.println("All thumbnails downloaded.");
                break;


            case "calender":

                break;


            case "movie":

                break;
        }
    }
}
