package com.lyc.exia.bean;


import java.util.List;

/**
 * Created by wayne on 2017/1/6.
 */

public class DayBean extends BaseBean{


    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 586f290b421aa9315ea7990e
         * content : <p></p>
         * created_at : 2017-01-06T13:20:11.648Z
         * publishedAt : 2017-01-06T13:18:00.0Z
         * rand_id : a253134f-1ae6-48be-9837-c03240bddffd
         * title : 今日力推：马云和宋小宝小品首秀 / 贝塞尔Loading—化学风暴 / 仿小红书图片标签 / Android 动态 Menu 菜单 Demo
         * updated_at : 2017-01-06T15:22:23.811Z
         */

        private String _id;
        private String content;
        private String created_at;
        private String publishedAt;
        private String rand_id;
        private String title;
        private String updated_at;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getRand_id() {
            return rand_id;
        }

        public void setRand_id(String rand_id) {
            this.rand_id = rand_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}
