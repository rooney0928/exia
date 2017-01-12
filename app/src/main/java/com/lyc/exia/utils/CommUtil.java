package com.lyc.exia.utils;

import com.lyc.exia.base.MyApplication;

/**
 * Created by wayne on 2017/1/8.
 */

public class CommUtil {


    public static int getMaxElem(int[] arr) {
        int size = arr.length;
        int maxVal = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            if (arr[i]>maxVal)
                maxVal = arr[i];
        }
        return maxVal;
    }

    /**
     * 获取资源文件 String
     * @param id
     * @return
     */
    public static String getResourcesString(int id) {
        return MyApplication.context.getResources().getString(id);
    }
}
