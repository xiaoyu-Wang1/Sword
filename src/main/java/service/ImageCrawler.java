package service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.IOUtils;
import utils.URL2ImageService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 爬虫第一弹
 */
public class ImageCrawler {
    private static final String basePath = "/home/xiaoyu/sexy20190107/";
    private static final String filePath = "/home/xiaoyu/url.txt";
    private static final String baseUri = "https://www.2234na.com";
    private static final String subBaseUri = baseUri + "/pic/4/";
    private static AtomicInteger picNumber = new AtomicInteger(1);
    private static AtomicInteger folderNumber = new AtomicInteger(1);
    private static SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static List<String> imageUrlList = new ArrayList<String>(100000);

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 1000, 10, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(10000),
            new ThreadPoolExecutor.DiscardPolicy());

    public static void main(String[] args) {
        try {
            System.out.println(sft.format(new Date()));
            Map<String, String> indexMap = indexExecute(subBaseUri);
            for (String key : indexMap.keySet()) {
                String picUrl = baseUri + key;
                final Map<String, String> picMap = picExecute(picUrl);
                for (final String key2 : picMap.keySet()) {
                    final String htmlUrl = baseUri + key2;
//                String htmlName = picMap.get(key2);
                    System.out.println(htmlUrl);

                    executor.execute(new Runnable() {
                        public void run() {
                            try {
                                imageExecute(htmlUrl, picMap.get(key2));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
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
            System.out.println(content.select("li"));
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

    /*public static void main(String[] args) throws Exception {
        String htmlUrl = "https://www.6244df.com/pic/4/index_2.html";
        picExecute(htmlUrl);
    }*/

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
                    URL2ImageService.saveURL2Image(imageUrl, folderPath() + picName + picNumber + imageName);
                    picNumber.incrementAndGet();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String folderPath() throws IOException {
        String folderPath = basePath + folderNumber;
        if (picNumber.get() % 1000 == 0) {
            folderNumber.getAndIncrement();
            folderPath = basePath + folderNumber;
            File file = new File(folderPath);
            if (!file.exists()) {
                if (!file.mkdir()) {
                    throw new IOException("创建文件夹失败");
                }
            }
        }
        return folderPath + "/";
    }
/*
    public static void main(String[] args) throws IOException {
        picNumber = new AtomicInteger(1001);
        System.out.println(folderPath());
    }*/
}
