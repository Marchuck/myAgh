package pl.marchuck.myagh.tabs;

import android.util.Log;

import org.jsoup.nodes.Element;

import java.util.Random;

/**
 * @author Lukasz Marczak
 * @since 08.05.16.
 */
public class Article {
    public static final String TAG = Article.class.getSimpleName();
    public String title;
    public String date;
    public String url;

    public Article() {
        this("article no " + new Random().nextInt(1000));
    }

    public Article(String title) {
        this.title = title;
    }

    public Article(Element el) {
        this.date = parseDate(el);
        this.title = parseTitle(el);
        this.url = parseUrl(el);
    }

    private String parseUrl(Element el) {
        Element link = el.select("a").first();
        String relHref = link.attr("href");
        String absHref = link.attr("abs:href");
        Log.i(TAG, "parseUrl: " + relHref);
        Log.i(TAG, "parseUrl: " + absHref);
        return "https://www.eaiib.agh.edu.pl/" + relHref;
    }

    String parseDate(Element el) {
        return el.text().substring(0, 10);
    }

    String parseTitle(Element el) {
        return el.text().substring(11).replace("więcej »", "");
    }
}
