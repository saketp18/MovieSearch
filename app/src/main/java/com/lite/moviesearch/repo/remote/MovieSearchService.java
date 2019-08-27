package com.lite.moviesearch.repo.remote;

import com.lite.moviesearch.models.MovieDetail;
import com.lite.moviesearch.models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieSearchService {

    @GET("/")
    Call<MovieResponse> getSearchResults(@Query("s") String query,
                                         @Query("page") int page,
                                         @Query("apikey") String apikey);

    @GET("/")
    Call<MovieDetail> getDetail(@Query("i") String id,
                                @Query("apikey") String apikey);
}
