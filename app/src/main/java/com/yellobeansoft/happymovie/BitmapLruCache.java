package com.yellobeansoft.happymovie;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.LruCache;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Jirawut-Jack on 12/02/2015.
 */
public class BitmapLruCache
        extends LruCache<String, Bitmap>
        implements ImageLoader.ImageCache {

//    public BitmapLruCache() {
//        this(getDefaultLruCacheSize());
//    }

    public BitmapLruCache(int maxSize) {
        super(maxSize);
    }

//    @Override
//    protected int sizeOf(String key, Bitmap value) {
//        return value.getRowBytes() * value.getHeight() / 1024;
//    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }

//    public static int getDefaultLruCacheSize() {
//        final int maxMemory =
//                (int) (Runtime.getRuntime().maxMemory() / 1024);
//        final int cacheSize = maxMemory / 8;
//
//        return cacheSize;
//    }
//
    // Returns a cache size equal to approximately three screens worth of images.
    public static int getCacheSize(Context ctx) {
        final DisplayMetrics displayMetrics = ctx.getResources().
                getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;
        // 4 bytes per pixel
        final int screenBytes = screenWidth * screenHeight * 4;

        return screenBytes * 20;
    }

}
