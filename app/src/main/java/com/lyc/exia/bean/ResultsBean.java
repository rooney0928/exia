package com.lyc.exia.bean;

import com.google.gson.annotations.SerializedName;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by wayne on 2017/1/6.
 */

public class ResultsBean {

    @SerializedName("Android")
    private List<Gank> androidList = new ArrayList<Gank>();
    @SerializedName("iOS")
    private List<Gank> iosList = new ArrayList<Gank>();
    @SerializedName("休息视频")
    private List<Gank> relaxList = new ArrayList<Gank>();
    @SerializedName("拓展资源")
    private List<Gank> expandList = new ArrayList<Gank>();
    @SerializedName("瞎推荐")
    private List<Gank> xjbtjList = new ArrayList<Gank>();
    @SerializedName("福利")
    private List<Gank> benefitList = new ArrayList<Gank>();


    public List<Gank> getAndroidList() {
        return androidList;
    }

    public void setAndroidList(List<Gank> androidList) {
        this.androidList = androidList;
    }

    public List<Gank> getIosList() {
        return iosList;
    }

    public void setIosList(List<Gank> iosList) {
        this.iosList = iosList;
    }

    public List<Gank> getRelaxList() {
        return relaxList;
    }

    public void setRelaxList(List<Gank> relaxList) {
        this.relaxList = relaxList;
    }

    public List<Gank> getExpandList() {
        return expandList;
    }

    public void setExpandList(List<Gank> expandList) {
        this.expandList = expandList;
    }

    public List<Gank> getXjbtjList() {
        return xjbtjList;
    }

    public void setXjbtjList(List<Gank> xjbtjList) {
        this.xjbtjList = xjbtjList;
    }

    public List<Gank> getBenefitList() {
        return benefitList;
    }

    public void setBenefitList(List<Gank> benefitList) {
        this.benefitList = benefitList;
    }
}
