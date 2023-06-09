package unet.fcrawler.handlers;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CheckSum {

    private File f;

    public CheckSum(File f){
        this.f = f;
    }

    public String execute()throws NoSuchAlgorithmException, IOException {
        String z = getFileChecksum(f);
        f.renameTo(new File(f.getParent(), z+f.getName().substring(f.getName().lastIndexOf("."))));
        return z;
    }

    public String getFileChecksum(File file)throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        //Get file input stream for reading the file content
        FileInputStream fis = new FileInputStream(file);

        //Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        //Read file data and update in message digest
        while((bytesCount = fis.read(byteArray)) != -1){
            digest.update(byteArray, 0, bytesCount);
        }

        //close the stream; We don't need it now.
        fis.close();

        //Get the hash's bytes
        byte[] bytes = digest.digest();

        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < bytes.length; i++){
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        //return complete hash
        return sb.toString();
    }
}
