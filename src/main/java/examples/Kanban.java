package examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
        while (!current.getPrevPageLink().isEmpty() && hotLinks.size() < 300) {
            current = new KanbanPage(current.getPrevPageLink());
            hotLinks.addAll(current.getHotRents());
            System.out.println(indexLink + ":" + hotLinks.size());
        }
        this.html = "<html>" +
                "<head>" +
                    "<style>" +
                        "body {" +
                            "    background-color: lightgray;" +
                            "}" +
                            "table {" +
                            "    border: 0;" +
                            "    font-size: 85%;" +
                            "}" +
                            "p {" +
                            "    text-align: center;   " +
                            "    font-size: 85%;" +
                            "}" +
                            "tr:hover {" +
                            "    background-color: lightgray;" +
                            "}" +
                    "</style>" +
                "</head>" +
                "<body><table align=\"center\">" +
                "<p>Update time:" + new Date() + "</p>"
                + hotLinks.stream().map(e ->
                new StringBuilder()
                        .append("<tr>")
                            .append("<td>").append(e.getDate()).append("</td>")
                            .append("<td>").append(e.getResponseCount() == Integer.MAX_VALUE ? "爆" : e.getResponseCount()).append("</td>")
                            .append("<td>")
                                .append("<a target='new' href='").append(PTT.baseUrl).append(e.getLink()).append("'>").append(e.getTitle()).append("</a>")
                            .append("</td>")
                        .append("</tr>")
                        .toString()
            ).collect(Collectors.joining())
                    + "</table></body></html>";
    }

    public String getHtml() {
        return html;
    }

}
