package examples;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class KanbanPage {

    private final String prevPageLink;
    private final List<KanbanRent> hotRents;

    public KanbanPage(String kanbanUrl) throws IOException {
        Document doc = Jsoup.connect(PTT.baseUrl + kanbanUrl).get();
        Element actionBarElmt = doc.getElementById("action-bar-container");
        try {
            this.prevPageLink = actionBarElmt.getAllElements().stream()
                    .filter(e -> e.text().contains("上頁"))
                    .filter(e -> StringUtils.isNotBlank(e.attr("href")))
                    .findFirst().get().attr("href");
            this.hotRents = doc.getElementsByClass("r-ent").stream()
                    .map(e -> new KanbanRent(e))
                    .filter(k -> k.isHot())
                    .collect(Collectors.toList());
        } catch (RuntimeException ex) {
            System.out.println(actionBarElmt.toString());
            throw ex;
        }
    }

    public List<KanbanRent> getHotRents() {
        return new ArrayList<>(hotRents);
    }

    public String getPrevPageLink() {
        return prevPageLink;
    }

    public static class KanbanRent {
        private final String html;
        private final int responseCount;
        private final String link;
        private final String title;
        private final String date;
        KanbanRent(Element rentElmt) {
            this.html = rentElmt.html();
            Element responseSpanElmt = rentElmt.getElementsByClass("nrec").first().getElementsByTag("span").first();
            String respCntString = responseSpanElmt == null ? "0" : responseSpanElmt.text();
            this.responseCount = "爆".equals(respCntString) ? Integer.MAX_VALUE : NumberUtils.toInt(respCntString);
            Element titleAElmt = rentElmt.getElementsByClass("title").first().getElementsByTag("a").first();
            this.link = titleAElmt == null ? "": titleAElmt.attr("href");
            this.title = titleAElmt == null ? "": titleAElmt.text();
            Element dateElmt = rentElmt.getElementsByClass("date").first();
            this.date = dateElmt == null ? "" : dateElmt.text();
        }
        public boolean isHot() {
            return responseCount >= 15;
        }

        @Override
        public String toString() {
            return "responseCount:" + responseCount + ", title:" + title + ", link:" + link + ", date:" + date;
        }

        public String getHtml() {
            return html;
        }
    }

}
