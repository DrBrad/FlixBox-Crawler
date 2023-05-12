package unet.fcrawler.handlers;

import unet.fcrawler.libs.fengine.FRequest;
import unet.fcrawler.libs.fengine.FResponse;
import unet.fcrawler.libs.fengine.FSocket;
import unet.fcrawler.libs.fengine.FSocketCallback;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UrlImage {

    private String url;
    private BufferedImage i;

    public UrlImage(String url){
        this.url = url;
    }

    public BufferedImage get(){
        new FSocket(url, new FSocketCallback(){
            @Override
            public void onResponse(FRequest request, FResponse response, InputStream in)throws IOException {
                if(response.getStatusCode() == 200){
                    i = ImageIO.read(in);
                }
            }

            @Override
            public void onException(Exception e){
                e.printStackTrace();
            }
        }).connect();
        return i;
    }
}
