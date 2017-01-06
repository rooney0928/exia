package com.lyc.exia.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by wayne on 2017/1/5.
 */

public class BaseBean extends DataSupport{

    private boolean error;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
