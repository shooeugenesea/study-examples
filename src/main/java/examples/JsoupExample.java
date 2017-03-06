package examples;

import expectj.ExpectJ;
import expectj.Spawn;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class JsoupExample {

    public static void main(String[] params) throws IOException {
        String[][] ss = new String[][]{
                {"e-shopping", "/bbs/e-shopping/index.html"},
//                {"BabyMother", "/bbs/BabyMother/index.html"},
//                {"joke", "/bbs/joke/index.html"},
//                {"Baseball", "/bbs/Baseball/index.html"},
//                {"NBA", "/bbs/NBA/index.html"},
//                {"LoL", "/bbs/LoL/index.html"},
//                {"C_Chat", "/bbs/C_Chat/index.html"},
//                {"Stock", "/bbs/Stock/index.html"},
//                {"WomenTalk", "/bbs/WomenTalk/index.html"},
//                {"movie", "/bbs/movie/index.html"},
//                {"KoreaDrama", "/bbs/KoreaDrama/index.html"},
//                {"KoreaStar", "/bbs/KoreaStar/index.html"},
        };
        ExpectJ expectinator = new ExpectJ(30);

        Arrays.stream(ss).forEach(s -> {
            new Thread(){
                @Override
                public void run() {
                    try {
                        Kanban kanban = new Kanban(s[1]);
                        String filename = "pttread/" + s[0] + ".html";
                        File file = new File("../shooeugenesea.github.io/" + filename);
                        System.out.println("Export file: " + file);
                        FileUtils.write(file, kanban.getHtml());
                        System.out.println("Export file: " + file + "...done");
                        Spawn shell = expectinator.spawn("\"C:\\Program Files (x86)\\git\\bin\\sh.exe\" --login -i");
                        shell.send("git -C ../shooeugenesea.github.io/ add " + filename + "\r\n");
                        shell.send("git -C ../shooeugenesea.github.io/ commit -m \"update file for " + s[0] + "\"\r\n");
                        shell.send("dir \n");
                        shell.send("git -C ../shooeugenesea.github.io/ push\r\n");
                        shell.expect("Username for");
                        shell.expect("Password for 'https://shooeugenesea@github.com': ");
                        shell.send("exit\r\n");
                        shell.expectClose();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        });

    }


}
