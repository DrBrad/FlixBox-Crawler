package unet.fcrawler.commands;

import unet.fcrawler.handlers.Srt2Vtt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.List;

public class Subtitles {

    public Subtitles(List<String> cmd)throws Exception {
        File f = new File(cmd.get(0));
        if(f.isDirectory()){
            for(File n : f.listFiles()){
                new Srt2Vtt().convert(new FileReader(n),
                        new PrintWriter(new FileOutputStream(new File(n.getParentFile(), n.getName().substring(0, n.getName().lastIndexOf("."))+".vtt"))));
                System.out.println(n.getName()+" converted");
            }
        }else{
            new Srt2Vtt().convert(new FileReader(f),
                    new PrintWriter(new FileOutputStream(new File(f.getParentFile(), f.getName().substring(0, f.getName().lastIndexOf("."))+".vtt"))));
            System.out.println(f.getName()+" converted");
        }
    }
}
