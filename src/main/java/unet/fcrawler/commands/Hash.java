package unet.fcrawler.commands;

import unet.fcrawler.handlers.CheckSum;

import java.io.File;
import java.util.List;

public class Hash {

    public Hash(List<String> cmd)throws Exception {
        File f = new File(cmd.get(0));
        if(f.isDirectory()){
            for(File n : f.listFiles()){
                System.out.println(n.getName()+" > "+new CheckSum(n).execute());
            }
        }else{
            System.out.println(f.getName()+" > "+new CheckSum(f).execute());
        }
    }
}
