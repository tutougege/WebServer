package webServer.Core;

import webServer.Http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable{
    static Socket socket;
    public ClientHandler(Socket socket){
        this.socket = socket;
    }
    public void run() {
        try {
            //解析请求
            HttpServletRequest http = new HttpServletRequest(socket);
            //处理请求

            //发送响应
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
