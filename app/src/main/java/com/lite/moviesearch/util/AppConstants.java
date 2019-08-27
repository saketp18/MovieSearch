package com.lite.moviesearch.util;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lite.moviesearch.R;

public class AppConstants {

    public final static String BASE_URL = "https://www.omdbapi.com";
    public final static String API_KEY = "142d20f0";

    public static RequestManager initGlide(Context context) {
        RequestOptions options = new RequestOptions().placeholder(R.drawable.white_background).error(R.drawable.white_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        return Glide.with(context)
                .setDefaultRequestOptions(options);
    }
}
