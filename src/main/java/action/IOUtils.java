package action;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class IOUtils {

    /**
     * 输出字符串到文件
     *
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
            e.printStackTrace();
        }
    }

    public static String HtmlUrl2HtmlString(String htmlUrl) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();//Creates CloseableHttpClient instance with default configuration.
        HttpGet httpGet = new HttpGet(htmlUrl);
        try {
            CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);
            InputStream inputStream = closeableHttpResponse.getEntity().getContent();
            return  inputStream2String(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();//释放资源
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String inputStream2String(InputStream in) throws IOException {
        StringBuilder out = new StringBuilder();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }
}
