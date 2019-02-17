package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 爬虫的结果的基准类
 */
public class BaseResult implements Serializable{
    private Map<String, String> urlImageNameMap;

    public Map<String, String> getUrlImageNameMap() {
        return urlImageNameMap;
    }

    public void setUrlImageNameMap(Map<String, String> urlImageNameMap) {
        this.urlImageNameMap = urlImageNameMap;
    }
}
