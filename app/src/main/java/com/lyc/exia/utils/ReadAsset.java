package com.lyc.exia.utils;

import android.app.Activity;
import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wayne on 2017/1/7.
 */

public class ReadAsset {

    public static String read(Context context,String filename) {
        try {
            //Return an AssetManager instance for your application's package
            InputStream is = context.getAssets().open(filename);
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
            String text = new String(buffer, "utf-8");
            // Finally stick the string into the text view.
            return text;
        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }
    }
}
