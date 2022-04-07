package webServer.Core;

import webServer.Http.HttpServletRequest;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
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
            File file = new File("./webApps/MyWeb.index.html");
            //发送状态行
            String line = "HTTP/1.1 200 OK";
            printLine(line);
            //发送响应头
            line = "Content-Type: Text/html";
            printLine(line);
            line = "Content-Length"+file.length();
            printLine(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void printLine(String line) throws IOException {
            OutputStream os = socket.getOutputStream();
            byte[] bytes = line.getBytes(StandardCharsets.ISO_8859_1);
            os.write(bytes);
            os.write((char)13);//发送回车符
            os.write((char)10);//发送换行符
    }
}
