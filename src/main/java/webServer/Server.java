package webServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket server;
    public Server(){
        try {
            System.out.println("开始连接...");
            server = new ServerSocket(8088);
            System.out.println("连接成功...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void start(){
        try {
            System.out.println("等待客户端连接...");
            Socket socket1 = server.accept();
            System.out.println("连接成功");
            Thread t1 = new Thread(new ClientHandler(socket1));
            t1.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
