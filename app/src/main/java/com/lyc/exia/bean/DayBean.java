package com.lyc.exia.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wayne on 2017/1/10.
 */

public class DayBean extends BaseBean{

    /**
     * category : ["iOS","Android","瞎推荐","拓展资源","福利","休息视频"]
     * error : false
     */

    private List<String> category;
    private ResultBean results;

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public ResultBean getResults() {
        return results;
    }

    public void setResults(ResultBean results) {
        this.results = results;
    }

    public class ResultBean {
        @SerializedName("Android")
        public List<Gank> androidList;
        @SerializedName("iOS")
        public List<Gank> iosList;
        @SerializedName("休息视频")
        public List<Gank> relaxList;
        @SerializedName("拓展资源")
        public List<Gank> expandList;
        @SerializedName("瞎推荐")
        public List<Gank> recommendList;
        @SerializedName("福利")
        public List<Gank> benefitList;

    }
}
