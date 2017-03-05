package examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class Kanban {

    private final String indexLink;
    private final String html;
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
        this.html = "<html><head><body><div class=\"r-list-container action-bar-margin bbs-screen\">"
                + hotLinks.stream().map(e -> e.getHtml().replace("href=\"", "href=\"" + PTT.baseUrl)).collect(Collectors.joining())
                + "</div></body></html>";
    }

    public List<KanbanPage.KanbanRent> getHotLinks() {
        return new ArrayList<>(hotLinks);
    }

    public String getHtml() {
        return html;
    }

}
