package webServer.Http;

import com.sun.corba.se.impl.ior.iiop.IIOPProfileTemplateImpl;

import javax.xml.transform.Source;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;

/*
响应对象
该类的每一个实例用于表示HTTP协议规定的一个响应
每个响应由三部分组成
响应状态行　响应头　响应正文
 */
public class HttpServletResponse {
    //状态行相应属性
    private int statusCode = 200;
    private String statusReason = "OK";
    //响应头相关信息
    private Map<String,String> headers = new HashMap<>();
    //响应正文
    File file;
    private Socket socket;
    public void setResponse() throws IOException {
        /*
        将当前响应的正文 用标准的响应格式响应
         */
        //发送响应
        //发送状态行
        SendStatusCode();
        //发送响应头
        SendHeaders();
        //发送响应正文
        SendContent();
    }
    private void SendStatusCode() throws IOException {
        String line;
        line = "HTTP/1.1 "+statusCode+" "+statusReason;
        System.out.println("响应状态行:"+line);
        printLine(line);
    }
    private void SendHeaders() throws IOException {
//        String line;
//        line = "Content-Type: text/html";
//        System.out.println("响应头:"+line);
//        printLine(line);
//        line = "Content-Length: "+file.length();
//        System.out.println("响应头:"+line);
//        printLine(line);
        for(Map.Entry<String,String> entry : headers.entrySet()){
                String line = entry.getKey()+": "+entry.getValue();
                printLine(line);
        }
        printLine("");//单独发送回车换行，表示响应头响应完毕
    }

    private void SendContent() throws IOException {
        String line;
        OutputStream out = socket.getOutputStream();
        FileInputStream fis = new FileInputStream(file);
        int len;
        byte[] bytes = new byte[1024*10];
        while((len = fis.read(bytes))!=-1){
            out.write(bytes,0,len);
        }
        System.out.println("响应发送完毕");
        out.close();
        fis.close();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void addHeaders(String name, String value) {
        headers.put(name,value);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
        String fileName = file.getName();
        //根据用户请求的资源文件截取后缀名
        String ext = fileName.substring(fileName.lastIndexOf(".")+1);
        addHeaders("Content-Type", HttpContent.getMimeType(ext));
        addHeaders("Content-Length",file.length()+"");
//        System.out.println(ext);
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public HttpServletResponse(Socket socket) throws IOException {
        this.socket = socket;
    }
    public void printLine(String line) throws IOException {
        OutputStream os = socket.getOutputStream();
        byte[] bytes = line.getBytes(StandardCharsets.ISO_8859_1);
        os.write(bytes);
        os.write((char)13);//发送回车符
        os.write((char)10);//发送换行符
    }
}
