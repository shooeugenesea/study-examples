package examples;

import expectj.ExpectJ;
import expectj.Spawn;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class JsoupExample {


    public static void main(String[] params) throws IOException {
        String gitUserName = System.getenv("GIT_USERNAME");
        String gitPassword = System.getenv("GIT_PASSWORD");
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
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(ss.length);
        Arrays.stream(ss).forEach(s -> {
            scheduler.scheduleAtFixedRate(() -> {
                try {
                    Kanban kanban = new Kanban(s[1]);
                    String filename = "pttread/" + s[0] + ".html";
                    File file = new File("../shooeugenesea.github.io/" + filename);
                    System.out.println("Export file: " + file);
                    FileUtils.write(file, kanban.getHtml());
                    System.out.println("Export file: " + file + "...done");
                    ExpectJ expectinator = new ExpectJ(5);
                    Spawn shell = expectinator.spawn("\"C:\\Program Files (x86)\\git\\bin\\sh.exe\" --login -i");
                    shell.send("cd ~/workspace_github/shooeugenesea.github.io/\n");
                    shell.send("git status \n");
                    shell.send("git add .\n");
                    shell.send("git commit -m \"update file for " + s[0] + "\"\n");
                    shell.send("git push https://" + gitUserName + ":" + gitPassword + "@github.com/shooeugenesea/shooeugenesea.github.io --all \n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 0, 5, TimeUnit.MINUTES);
        });

    }


}
