package webServer.Core;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        File f = new File("./webApps/MyWeb/reg.html");
        System.out.println(f.exists());
        System.out.println(f.isFile());
    }
}
