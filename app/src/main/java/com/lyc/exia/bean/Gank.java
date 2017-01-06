package com.lyc.exia.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by wayne on 2017/1/5.
 */

public class Gank extends DataSupport{

    /**
     * _id : 56cc6d23421aa95caa707c6f
     * createdAt : 2015-08-06T01:33:55.463Z
     * desc : 8.6
     * publishedAt : 2015-08-06T04:16:55.601Z
     * type : 福利
     * url : http://ww4.sinaimg.cn/large/7a8aed7bgw1eusn3niy2bj20hs0qo0wb.jpg
     * used : true
     * who : 张涵宇
     */
    private int id;
    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String type;
    private String url;
    private boolean used;
    private String who;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    @Override
    public String toString() {
        return "Gank{" +
                "id=" + id +
                ", _id='" + _id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", who='" + who + '\'' +
                ", used=" + used +
                '}';
    }
}
