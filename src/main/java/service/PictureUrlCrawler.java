package service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import utils.URL2ImageService;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 爬取 https://www.7160.com/meinv/ 上的图片
 */
public class PictureUrlCrawler {

    private static final String site_url = "https://www.7160.com/rentiyishu/";

    private static AtomicInteger count = new AtomicInteger(0);

    /**
     * 解析某一页html的内容
     * @param url 要解析的网页的地址，如 http://www.91porn.com/v.php?next=watch&page=100
     */
    private static void perPageParse(String url) throws Exception {
        Document doc = Jsoup.connect(url).get();
        String title = doc.title();
        // make file

        String text = doc.getElementsByClass("itempage").get(0).getAllElements().get(1).text();
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(text);
        System.out.println( );
        String imageNumStr = m.replaceAll("");
        int imageNum = Integer.parseInt(imageNumStr);

        String baseUri = doc.baseUri();
        System.out.println(baseUri);
        System.out.println(imageNum);
        String imageHtmlUrl = "";
        for (int i = 1; i <= imageNum; i++) {
            if (i == 1) {
                imageHtmlUrl = baseUri;
                System.out.println(baseUri);
            } else {
                imageHtmlUrl = baseUri + "index_" + i + ".html";
                System.out.println(imageHtmlUrl);
            }
            String imageUrl = parseImageUrlFromImageHtmlUrl(imageHtmlUrl);
            URL2ImageService.saveURL2Image(imageUrl, title+i);
            count.incrementAndGet();
        }
    }

    private static String parseImageUrlFromImageHtmlUrl(String imageHtmlUrl) throws IOException {
        Document document = Jsoup.connect(imageHtmlUrl).get();
        return  document.getElementsByClass("picsbox picsboxcenter").get(0).getElementsByTag("img").attr("src");
    }
    public static void main(String[] args) throws Exception {
        for (int i = 59800; i < 59854; i++) {
            try {
                perPageParse(site_url + i);
            } catch (Exception e) {
                System.out.println(i + " 爬取异常");
            }
        }
        System.out.println(count);
    }

}
