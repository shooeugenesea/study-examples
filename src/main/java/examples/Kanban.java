package examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class Kanban {

    private final String indexLink;
    private final List<KanbanPage.KanbanRent> hotLinks = new ArrayList<>();

    public Kanban(String indexLink) throws IOException {
        this.indexLink = indexLink;
        KanbanPage index = new KanbanPage(indexLink);
        hotLinks.addAll(index.getHotRents());
        KanbanPage current = index;
        while (!current.getPrevPageLink().isEmpty() && hotLinks.size() < 10) {
            current = new KanbanPage(current.getPrevPageLink());
            hotLinks.addAll(current.getHotRents());
            System.out.println(indexLink + ":" + hotLinks.size());
        }
    }

    public List<KanbanPage.KanbanRent> getHotLinks() {
        return new ArrayList<>(hotLinks);
    }

}
