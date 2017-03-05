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
        };
        Arrays.stream(ss).forEach(s -> {
            new Thread(){
                @Override
                public void run() {
                    try {
                        Kanban kanban = new Kanban(s[1]);
                        FileUtils.write(new File("D:/" + s[0] + ".html"), kanban.getHtml());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        });

    }


}
