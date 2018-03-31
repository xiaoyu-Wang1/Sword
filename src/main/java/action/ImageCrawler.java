package action;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 爬虫第一弹
 */
public class ImageCrawler {
    private static final String basePath = "/home/xiaoyu/sexy/";
    private static final String filePath = "/home/xiaoyu/url.txt";
    private static final String baseUri = "https://www.9001df.com";
    private static int picNumber = 0;
    private static SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static List<String> imageUrlList = new ArrayList<String>(100000);

    public static void main(String[] args) {
        try {
            System.out.println(sft.format(new Date()));
            Map<String, String> indexMap = indexExecute("https://www.9001df.com/pic/4/index.html");
            for (String key : indexMap.keySet()) {
                String picUrl = baseUri + key;
                Map<String, String> picMap = picExecute(picUrl);
                for (String key2 : picMap.keySet()) {
                    String htmlUrl = baseUri + key2;
//                String htmlName = picMap.get(key2);
                    System.out.println(htmlUrl);
                    imageExecute(htmlUrl, picMap.get(key2));
                    Thread.sleep((int) (Math.random() * 10) * 1000);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println(sft.format(new Date()));

            imageUrlList.add("图片数：" + imageUrlList.size());
            IOUtils.WriteStringToFile5(imageUrlList, filePath);

            System.out.println(sft.format(new Date()));
        }
    }

    /**
     * 第一层：/pic/4/index_185.html 第 185 頁
     *
     * @param indexUrl
     * @return
     * @throws Exception
     */
    private static Map<String, String> indexExecute(String indexUrl) throws Exception {
        Map<String, String> picUrlMap = new HashMap<String, String>();
        try {
            String htmlString = IOUtils.HtmlUrl2HtmlString(indexUrl);
            // 解析html
            Document document = Jsoup.parse(htmlString);
            // 获取 “box list channel” 节点
            Elements content = document.getElementsByClass("pagination");
            // 获取 "href"
            String picUrl = "";
            String picName = "";
//            System.out.println(content.select("li"));
            for (Element element : content.select("option")) {
                picUrl = element.getElementsByTag("option").attr("value");
                picName = element.getElementsByTag("option").text();
                System.out.println(picUrl + " " + picName);
                picUrlMap.put(picUrl, picName);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return picUrlMap;
    }

    /**
     * 第二层爬虫：链接-链接里图片主题，/pic/4/2018-03-15/23987.html 泳池风景好[13P]
     *
     * @param htmlUrl
     * @return
     * @throws Exception
     */
    private static Map<String, String> picExecute(String htmlUrl) throws Exception {
        Map<String, String> picUrlMap = new HashMap<String, String>();
        try {
            String htmlString = IOUtils.HtmlUrl2HtmlString(htmlUrl);
            // 解析html
            Document document = Jsoup.parse(htmlString);
            // 获取 “box list channel” 节点
            Elements content = document.getElementsByClass("box list channel");
            // 获取 "href"
            String picUrl = "";
            String picName = "";
//            System.out.println(content.select("li"));
            for (Element element : content.select("li")) {
                picUrl = element.getElementsByTag("a").attr("href");
                picName = element.getElementsByTag("a").text().substring(19);
                System.out.println("****" + picUrl + " " + picName);
                picUrlMap.put(picUrl, picName);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return picUrlMap;
    }

    /**
     * 第三级：image 级别
     *
     * @param htmlUrl
     * @throws Exception
     */
    private static void imageExecute(String htmlUrl, String picName) throws Exception {
        try {
            String htmlString = IOUtils.HtmlUrl2HtmlString(htmlUrl);
            // 解析html
            Document document = Jsoup.parse(htmlString);
            // 获取 “content” 节点
            Elements content = document.getElementsByClass("content");
            // 获取 "src"
            String imageUrl = "";
            String imageName = ".jpg";
            for (Element element : content.select("img")) {
                System.out.println("**********" + imageUrl);
                imageUrl = element.getElementsByTag("img").attr("src");
                if (imageUrl != null && imageUrl.length() > 0) {
                    URL2ImageService.saveURL2Image(imageUrl, basePath  + picName +  picNumber + imageName);
                    picNumber++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
