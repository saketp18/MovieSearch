package com.lite.moviesearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    @SerializedName("Search")
    @Expose
    private List<MovieSearch> search = null;
    @SerializedName("Response")
    @Expose
    private String response;

    public List<MovieSearch> getSearch() {
        return search;
    }

    public void setSearch(List<MovieSearch> search) {
        this.search = search;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
