package unet.fcrawler;

import unet.fcrawler.commands.Crawl;
import unet.fcrawler.commands.Hash;
import unet.fcrawler.commands.Resize;
import unet.fcrawler.commands.Subtitles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    //https://jsoup.org/cookbook/extracting-data/selector-syntax

    //crawl episodes https://www.imdb.com/title/tt0370194/episodes?season=3 /home/brad/Downloads/im/coming
    //crawl episodes https://www.imdb.com/title/tt1305826/episodes?season=3 /home/brad/Downloads/thumbs
    //crawl cast https://www.imdb.com/title/tt0370194/?ref_=ttep_ep_tt /home/brad/Downloads/im/coming
    //crawl thumbnails https://www.imdb.com/title/tt0805663/episodes?season=1 "/home/brad/Downloads/Jericho/S1"

    public static void main(String[] args){
        System.out.println("Running FlixBox Crawler - Please type a command");

        Scanner scan = new Scanner(System.in);

        while(true){
            try{
                List<String> cmd = parseCommand(scan.nextLine());

                switch(cmd.get(0).toLowerCase()){
                    case "resize":
                        cmd.remove(0);
                        new Resize(cmd);
                        break;

                    case "sub":
                        cmd.remove(0);
                        new Subtitles(cmd);
                        break;

                    case "crawl":
                        cmd.remove(0);
                        new Crawl(cmd);
                        break;

                    case "hash":
                        cmd.remove(0);
                        new Hash(cmd);
                        break;
                }
            }catch(Exception e){
                System.err.println("Fatal exception...");
                //e.printStackTrace();
            }
        }
    }

    private static List<String> parseCommand(String cmd){
        List<String> command = new ArrayList<>();

        int s = 0;
        for(int i = 0; i < cmd.length(); i++){
            if(isTrimmable(cmd.charAt(i))){
                if(cmd.charAt(s) != '"'){
                    command.add(cmd.substring(s, i));
                    s = i+1;

                }else if(cmd.charAt(i-1) == '\"'){
                    command.add(cmd.substring(s+1, i-1));
                    s = i+1;
                }
            }
        }

        if(s < cmd.length()){
            if(cmd.charAt(s) != '"'){
                command.add(cmd.substring(s, cmd.length()));

            }else if(cmd.charAt(cmd.length()-1) == '\"'){
                command.add(cmd.substring(s+1, cmd.length()-1));
            }
        }

        return command;
    }

    private static boolean isTrimmable(char c){
        return (c == 0x20 ||
                c == '\t');
    }
}