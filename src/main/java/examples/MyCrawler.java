package examples;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.regex.Pattern;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class MyCrawler extends WebCrawler {

    private static final Logger logger = LoggerFactory.getLogger(MyCrawler.class);

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        logger.debug("referringPage.url:" + referringPage.getWebURL().getURL() + ", url:" + url.getURL());
        return true;
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        logger.debug("URL: " + url);
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();
            for ( String rentDiv: StringUtils.substringsBetween(html, "<div class=\"r-ent\">", "<div class=\"author\">") ) {
                for (String responseCountAndTitle: StringUtils.substringsBetween(rentDiv, "<div class=\"nrec\">", "<div class=\"meta\">")) {
                    String responseCountString = StringUtils.substringBetween(responseCountAndTitle, "<span ","</span></div>");
                    responseCountString = StringUtils.substringAfterLast(responseCountString, ">");
                    if (StringUtils.isBlank(responseCountString)) {
                        continue;
                    }
                    int responseCount = NumberUtils.toInt(responseCountString);
                    if (responseCount >= 15) {
                        String contentUrl = StringUtils.substringBetween(responseCountAndTitle, "<a href=\"", "\">");
                        String title = StringUtils.substringBetween(responseCountAndTitle, contentUrl + "\">", "</a>");
                        String date = StringUtils.substringBetween(rentDiv, "<div class=\"date\">", "</div>");
                        System.out.println("url:" + url + ", responseCount:" + responseCount + ", contentUrl:" + contentUrl + ", title:" + title + ", date:" + date);
                    }
                }
            }
        }
    }
}
