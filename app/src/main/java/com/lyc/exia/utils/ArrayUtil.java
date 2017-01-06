package com.lyc.exia.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wayne on 2017/1/6.
 */

public class ArrayUtil {

    public static List<String> getData(List<String> list, int page, int pageSize) {
        int len = list.size();
        int fromIndex = pageSize * (page - 1);
        int toIndex = fromIndex + pageSize;
        if (toIndex > len) {
            toIndex = len;
        }

        if (fromIndex < len) {
            return list.subList(fromIndex, toIndex);
        } else {
            return new ArrayList<>();
        }

    }
}
