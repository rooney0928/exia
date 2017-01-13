package com.lyc.exia.bean;

import java.util.List;

/**
 * Created by wayne on 2017/1/5.
 */

public class HistoryBean {

    /**
     * error : false
     * results : ["2017-01-04","2017-01-03","2016-12-30"]
     */

    private List<String> results;

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }
}
