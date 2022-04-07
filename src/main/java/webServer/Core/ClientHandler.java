package webServer.Core;

import webServer.Http.HttpServletRequest;
import webServer.Http.HttpServletResponse;

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
            HttpServletResponse httpServletResponse = new HttpServletResponse(socket);
            //发送响应
//            System.out.println(http.getUri());
            File file = new File("./webApps/MyWeb"+http.getUri());
            if(file.exists()&&file.isFile()){
                //如果请求的是文件 并且路径正确 则发送响应
                httpServletResponse.setFile(file);
                httpServletResponse.setResponse();
            }else {
                httpServletResponse.setStatusCode(404);
                httpServletResponse.setStatusReason("NotFound");
                httpServletResponse.setFile(new File("./webApps/root/404.html"));
                httpServletResponse.setResponse();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
