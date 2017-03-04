package examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class JsoupExample {

    public static void main(String[] params) throws IOException {
        List<KanbanPage.KanbanRent> hotLinks = new ArrayList<>();
        KanbanPage index = new KanbanPage("bbs/e-shopping/index.html");
        hotLinks.addAll(index.getHotRents());
        KanbanPage current = index;
        while (!current.getPrevPageLink().isEmpty() && hotLinks.size() < 100) {
            current = new KanbanPage(current.getPrevPageLink());
            hotLinks.addAll(current.getHotRents());
            System.out.println(hotLinks.size());
        }
        hotLinks.forEach(System.out::println);
    }


}
