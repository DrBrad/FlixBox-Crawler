package unet.fcrawler.libs.fengine;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ChunkedInputStream extends FilterInputStream {

    private int pos, chunk;

    public ChunkedInputStream(InputStream in){
        super(in);
    }

    @Override
    public int read()throws IOException {
        byte[] b = new byte[1];
        int l = read(b, 0, 1);

        if(l == -1){
            return -1;
        }

        return b[0];
    }

    @Override
    public int read(byte[] b, int off, int len)throws IOException {
        if(pos == chunk){
            chunk = startChunk();

            if(chunk == 0){
                in.close();
                return -1;
            }

            pos = 0;
        }

        if(chunk == 0){
            return -1;
        }

        int r = in.read(b, off, (chunk-pos < len) ? chunk-pos : len);
        pos += r;

        return r;
    }

    @Override
    public int available(){
        return chunk-pos;
    }

    @Override
    public long skip(long n)throws IOException {
        long s;

        if(pos+n >= chunk){
            s = in.skip(chunk-pos);
            pos = (int)n-(chunk-pos);
            chunk = startChunk();

            if(chunk == 0){
                return -1;
            }

            s += in.skip(pos);

        }else{
            pos += n;
            s = in.skip(n);
        }

        return s;
    }

    private int startChunk()throws IOException {
        byte[] buf = new byte[6];
        byte b;
        int i = 0;

        while((b = (byte) in.read()) != '\n'){
            if(b == '\r'){
                in.read();
                break;
            }
            buf[i] = b;
            i++;
            break;
        }

        while((b = (byte) in.read()) != '\n'){
            if(b == '\r'){
                in.read();
                break;
            }
            buf[i] = b;
            i++;
        }

        try{
            return Integer.parseInt(new String(buf, 0, i),16);
        }catch(NumberFormatException e){
            throw new IOException("malformed chunk ("+new String(buf, 0, i)+")");
        }
    }
}
