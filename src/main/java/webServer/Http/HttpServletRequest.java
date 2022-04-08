package webServer.Http;

import org.junit.Test;
import sun.text.normalizer.UBiDiProps;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Level;

/*
请求对象
该类的实例用于表示客户端发送过来的一个HTTP请求
每个请求由三部分组成
请求行，消息头，消息正文
 */
public class HttpServletRequest {
    private Socket socket;
    private String method;//请求方式
    private String uri;//抽象路径
    //uri:/webApps/MyWeb/reg.html?name=123&password=123&nickname=123&age=123
    private String requesURI;//存uri的请求部分，"?"左边的内容
    private String queryString;//存uri的参数部分,"?"右侧内容
    private Map<String,String> parameters = new HashMap<>();
    //存每一组参数
    private String protocol;//协议版本
    //消息头相关信息
    Map<String,String> map = new HashMap<>();

    public String getRequesURI() {
        return requesURI;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getParameters(String name) {
        return parameters.get(name);
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setMap(String name,String text) {
        map.put(name,text);
    }

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
        //解析消息头
        getMap();
//        System.out.println("map:"+map);
        //解析消息正文
        parseContent();
    }
    public void getRequestLine() throws IOException {
        String line = "";
        line = readLine();
        String[] data = line.split("\\s");
        System.out.println(Arrays.toString(data));
        method = data[0];
        uri = data[1];
        parseURI();//进一步解析uri
        protocol = data[2];
        System.out.println("method:"+method);
        System.out.println("uri:"+uri);
        System.out.println("protocol:"+protocol);
    }
    /*
    进一步解析uri
     */
    public void parseURI(){
        String[] data = uri.split("\\?");
        requesURI = data[0];
        if(data.length>1){
            queryString = data[1];
            data = queryString.split("&");
            for(String s :data){
                String[] arr =  s.split("=");
                if(arr.length>1){
                    parameters.put(arr[0],arr[1]);
                }
                parameters.put(arr[0],null);
            }
        }
    }
    public void getMap() throws IOException {
        String line;
        String[] data;
        while (!(line = readLine()).isEmpty()) {
//             System.out.println("消息头:"+line);
            data = line.split(":\\s");
            map.put(data[0],data[1]);
        }
        System.out.println("消息头:>");
        map.forEach((k,v)-> System.out.println(k+": "+v));
    }
    public void parseContent(){
//
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
//        is.close();
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
