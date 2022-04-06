package webServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable{
    Socket socket;
    public ClientHandler(Socket socket){
        this.socket = socket;
    }
    public void run(){
        try {
            InputStream is = socket.getInputStream();
            int d;
            while ((d = is.read())!=-1){
                System.out.print((char)d);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
