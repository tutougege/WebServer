package webServer.Http;

import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.Map;

/*
所有和HTTP相关的规定之内容
 */
public class HttpContent {
    private static Map<String,String> mimeMapping = new HashMap<>();
    static {
        initMimeMapping();
    }
    private static void initMimeMapping(){
//        SAXReader reader = new SAXReader();
//        reader.read()
        mimeMapping.put("html","text/html");
        mimeMapping.put("css","text/css");
        mimeMapping.put("js","application/javascript");
        mimeMapping.put("png","image/png");
        mimeMapping.put("jpg","image/jpeg");
        mimeMapping.put("gif","image/gif");
    }
    /**
     *根据后缀名获取头信息Content-Type的值
     * @param ext
     * @return
     */
    public static String getMimeType(String ext){
        return mimeMapping.get(ext);
    }
}
