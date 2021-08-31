package com.palmdev.learn_english;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;

public class AssetsUtil {
    public static Drawable loadDrawable(AssetManager manager, String fileName)
    {
        try{
            InputStream in = manager.open(fileName);
            return Drawable.createFromStream(in, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
