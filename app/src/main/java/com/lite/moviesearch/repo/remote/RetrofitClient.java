package com.lite.moviesearch.repo.remote;

import com.lite.moviesearch.util.AppConstants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit mRetrofit = null;

    private RetrofitClient() {
    }

    private static Retrofit getRetrofitInstance() {

        if (mRetrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    public static MovieSearchService getService() {

        MovieSearchService service = getRetrofitInstance().create(MovieSearchService.class);
        return service;
    }
}
