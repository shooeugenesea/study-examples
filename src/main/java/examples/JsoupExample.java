package examples;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class JsoupExample {

    public static void main(String[] params) throws IOException {
        String[][] ss = new String[][]{
                {"e-shopping", "/bbs/e-shopping/index.html"},
                {"BabyMother", "/bbs/BabyMother/index.html"},
                {"joke", "/bbs/joke/index.html"},
                {"Baseball", "/bbs/Baseball/index.html"},
                {"NBA", "/bbs/NBA/index.html"},
                {"LoL", "/bbs/LoL/index.html"},
                {"C_Chat", "/bbs/C_Chat/index.html"},
                {"Stock", "/bbs/Stock/index.html"},
                {"WomenTalk", "/bbs/WomenTalk/index.html"},
                {"movie", "/bbs/movie/index.html"},
                {"KoreaDrama", "/bbs/KoreaDrama/index.html"},
                {"KoreaStar", "/bbs/KoreaStar/index.html"},
        };
        Arrays.stream(ss).forEach(s -> {
            new Thread(){
                @Override
                public void run() {
                    try {
                        Kanban kanban = new Kanban(s[1]);
                        File file = new File("D:/" + s[0] + ".html");
                        System.out.println("Export file: " + file);
                        FileUtils.write(file, kanban.getHtml());
                        System.out.println("Export file: " + file + "...done");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        });

    }


}
