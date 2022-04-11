package webServer.Core;

import webServer.Http.HttpServletRequest;
import webServer.Http.HttpServletResponse;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

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
            File root  = new File(ClientHandler.class.getClassLoader().getResource(".").toURI());
            File staticDir = new File(root, "static/");
            File file = new File(staticDir,http.getUri());
//            System.out.println(file.getName());
            if(file.exists()&&file.isFile()){
                //如果请求的是文件 并且路径正确 则发送响应
                httpServletResponse.setFile(file);
            }else {
                httpServletResponse.setStatusCode(404);
                httpServletResponse.setStatusReason("NotFound");
                httpServletResponse.setFile(new File(root, "static/root/404.html"));
            }
            httpServletResponse.setResponse();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
