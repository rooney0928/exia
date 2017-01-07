package com.lyc.exia.utils;

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
}
