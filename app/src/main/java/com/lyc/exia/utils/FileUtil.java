package com.lyc.exia.utils;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wayne on 2017/1/12.
 */

public class FileUtil {
    private String SDCardRoot;
    private String SDStateString;

    public FileUtil() {
        //得到当前外部存储设备的目录
        SDCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
        // 获取扩展SD卡设备状态
        SDStateString = Environment.getExternalStorageState();
    }

    public static String getExiaRoot() {
        String exiaRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "codeSharer";
        File dir = new File(exiaRoot);
        if(!dir.exists()){
            dir.mkdirs();
        }
        return exiaRoot;
    }

    public static String getTime(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(date);
    }
}
