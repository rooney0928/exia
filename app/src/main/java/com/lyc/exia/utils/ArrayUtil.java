package com.lyc.exia.utils;

import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wayne on 2017/1/6.
 */

public class ArrayUtil {

    public static List<String> getData(List<String> list, int page, int pageSize) {

        if (page < 1) {
            throw new IndexOutOfBoundsException("page must be 1 or more");
        }
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
