package webServer.Http;

import sun.text.normalizer.UBiDiProps;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
请求对象
该类的实例用于表示客户端发送过来的一个HTTP请求
每个请求由三部分组成
请求行，消息头，消息正文
 */
public class HttpServletRequest {
    private Socket socket;
    String method;//请求方式
    String uri;//抽象路径
    String protocol;//协议版本
    //消息头相关信息
    Map<String,String> map = new HashMap<>();
    //消息正文相关信息
    //
    //
    public HttpServletRequest(Socket socket) throws IOException {
        /*
        实例化请求对象也是解析请求的过程
         */
        this.socket = socket;
        //解析请求行
        getRequestLine();
        //解析消息行
        getMap();
        System.out.println("map:"+map);
        //解析消息正文
        parseContent();
    }
    public void getRequestLine() throws IOException {
        String line = null;
        line = readLine();
        String[] data = line.split("\\s");
//        System.out.println(Arrays.toString(data));
        method = data[0];
        uri = data[1];
        protocol = data[2];
        System.out.println("method:"+method);
        System.out.println("uri:"+uri);
        System.out.println("protocol:"+protocol);
    }
    public void getMap() throws IOException {
        String line;
        String[] data;
        while (true) {
             line = readLine();
             if (line.isEmpty()){
                 break;
             }
             System.out.println("消息头:"+line);
            data = line.split(":\\s");
            map.put(data[0],data[1]);
        }
    }
    public void parseContent(){

    }

    public String readLine() throws IOException {
        InputStream is = socket.getInputStream();
        StringBuilder stringBuilder = new StringBuilder();
        char cur = 'a';
        char pre = 'a';
        int d;
        while ((d = is.read()) != -1) {
            cur = (char) d;
            if (pre == 13 && cur == 10) {//判断当前字符是换行，上一个字符是回车
                break;
            }
            stringBuilder.append(cur);
            pre = cur;//保存这一次读取到的字符
        }
        String line = stringBuilder.toString().trim();
//        System.out.println(line);
        return line;
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getProtocol() {
        return protocol;
    }

    public Socket getSocket() {
        return socket;
    }
    public String getMap(String name){
        return map.get(name);
    }
}
