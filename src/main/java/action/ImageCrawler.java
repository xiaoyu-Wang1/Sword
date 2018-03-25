package action;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
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

    private static SimpleDateFormat sft =new SimpleDateFormat("yyyy MM dd HH mm ss");

    private static List<String> imageUrlList = new ArrayList<String>(100000);

    public static void main(String[] args) throws Exception {
        System.out.println(sft.format(new Date()));
//        String htmlUrl = "https://www.9001df.com/pic/4/2018-03-21/24015.html";
//        imageExcute(htmlUrl);
//        picExecute("https://www.9001df.com/pic/4/index.html");
        Map<String, String> indexMap = indexExecute("https://www.9001df.com/pic/4/index.html");
        for (String key : indexMap.keySet()) {
            String picUrl = baseUri + key;
            Map<String, String> picMap = picExecute(picUrl);
            for (String key2 : picMap.keySet()) {
                String htmlUrl = baseUri + key2;
//                String htmlName = picMap.get(key2);
                System.out.println(htmlUrl);
                imageExcute(htmlUrl);
                Thread.sleep((int)(Math.random() * 100) * 10);
            }
        }
//        imageExcute(htmlUrl);

//        picExecute(htmlUrl);



        System.out.println(sft.format(new Date()));
        imageUrlList.add("图片数：" + imageUrlList.size());
        WriteStringToFile5(imageUrlList, filePath);

        System.out.println(sft.format(new Date()));
    }

    /**
     * 输出字符串到文件
     * @param stringList
     * @param filePath
     */
    public static void WriteStringToFile5(List<String> stringList, String filePath) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            for (String s : stringList) {
                fos.write(s.getBytes());
                fos.write(10); // '\n' 对应的ASCII码为 10
            }
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 第一层：/pic/4/index_185.html 第 185 頁
     * @param indexUrl
     * @return
     * @throws Exception
     */
    public static Map<String, String> indexExecute(String indexUrl) throws Exception {
        Map<String, String> picUrlMap = new HashMap<String, String>();
        CloseableHttpClient httpCilent = HttpClients.createDefault();//Creates CloseableHttpClient instance with default configuration.
        HttpGet httpGet = new HttpGet(indexUrl);
        try {
            CloseableHttpResponse closeableHttpResponse = httpCilent.execute(httpGet);
            InputStream inputStream = closeableHttpResponse.getEntity().getContent();
            String htmlString = inputStream2String(inputStream);
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
        } finally {
            try {
                httpCilent.close();//释放资源
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return picUrlMap;
    }

    /**
     * 第二层爬虫：链接-链接里图片主题，/pic/4/2018-03-15/23987.html 泳池风景好[13P]
     * @param htmlUrl
     * @return
     * @throws Exception
     */
    public static Map<String, String> picExecute(String htmlUrl) throws Exception {
        Map<String, String> picUrlMap = new HashMap<String, String>();
        CloseableHttpClient httpCilent = HttpClients.createDefault();//Creates CloseableHttpClient instance with default configuration.
        HttpGet httpGet = new HttpGet(htmlUrl);
        try {
            CloseableHttpResponse closeableHttpResponse = httpCilent.execute(httpGet);
            InputStream inputStream = closeableHttpResponse.getEntity().getContent();
            String htmlString = inputStream2String(inputStream);
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
        } finally {
            try {
                httpCilent.close();//释放资源
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return picUrlMap;
    }

    /**
     * 第三级：image 级别
     * @param htmlUrl
     * @throws Exception
     */
    public static void imageExcute(String htmlUrl) throws Exception {
        CloseableHttpClient httpCilent = HttpClients.createDefault();//Creates CloseableHttpClient instance with default configuration.
        HttpGet httpGet = new HttpGet(htmlUrl);
        try {
            CloseableHttpResponse closeableHttpResponse = httpCilent.execute(httpGet);
            InputStream inputStream = closeableHttpResponse.getEntity().getContent();
            String htmlString = inputStream2String(inputStream);
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
                    saveURL2Image(imageUrl, basePath + picNumber + imageName);
                    picNumber++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpCilent.close();//释放资源
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String inputStream2String(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }


    public static boolean saveURL2Image(String imageUrl, String imageName) throws Exception{
        //new一个URL对象
        URL url = new URL(imageUrl);
        //打开链接
        HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
        //设置请求方式为"GET"
        conn.setRequestMethod("GET");
        //超时响应时间为5秒
        conn.setConnectTimeout(50 * 1000);
        //通过输入流获取图片数据
        InputStream inStream = conn.getInputStream();
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性
        byte[] data = readInputStream(inStream);
        //new一个文件对象用来保存图片，默认保存当前工程根目录
        File imageFile = new File(imageName);
        //创建输出流
        FileOutputStream outStream = new FileOutputStream(imageFile);
        //写入数据
        outStream.write(data);
        //关闭输出流
        outStream.close();
        return true;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }
}
