package unet.fcrawler.handlers;

import java.io.*;

public class Srt2Vtt {
    enum State {
        Number,
        TimeStamp,
        Text
    };

    public void convert(Reader srt, Writer vtt)throws IOException {
        BufferedReader br = new BufferedReader(srt);
        String line;
        vtt.write("WEBVTT\r\n");
        State state = State.Number;

        int i = 1;
        while((line = br.readLine()) != null){
            switch(state){
                case Number:
                    state = State.TimeStamp;
                    break;

                case TimeStamp:
                    vtt.append("\r\n"+i);
                    vtt.append("\r\n"+line.replace(',', '.'));
                    state = State.Text;
                    i++;
                    break;

                case Text:
                    vtt.append("\r\n"+line);
                    if(line.length() == 0){
                        state = State.Number;
                    }
                    break;
            }
        }
        vtt.close();
        srt.close();
    }
}
