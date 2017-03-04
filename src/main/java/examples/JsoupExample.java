package examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class JsoupExample {

    public static void main(String[] params) throws IOException {
        String[] ss = new String[]{
                "bbs/e-shopping/index.html",
                "bbs/Food/index.html",
//                "bbs/Gossiping/index.html",
        };
        Arrays.stream(ss).forEach(s -> {
            new Thread(){
                @Override
                public void run() {
                    try {
                        Kanban kanban = new Kanban(s);
                        System.out.println(kanban.getHotLinks());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        });

    }


}
