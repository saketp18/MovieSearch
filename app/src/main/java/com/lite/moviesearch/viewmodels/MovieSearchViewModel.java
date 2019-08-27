package com.lite.moviesearch.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lite.moviesearch.models.MovieDetail;
import com.lite.moviesearch.models.MovieResponse;
import com.lite.moviesearch.models.MovieSearch;
import com.lite.moviesearch.repo.Repository;

import java.util.List;

public class MovieSearchViewModel extends AndroidViewModel {

    private LiveData<MovieResponse> movieResponseLiveData;
    private LiveData<List<MovieSearch>> listLiveData;
    private LiveData<MovieDetail> detailLiveData;
    private Repository repository;

    public MovieSearchViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application.getApplicationContext());
    }

    public void loadData(String query, int page) {
        setListLiveData();
        repository.loadData(query, page);
        setMovieResponseLiveData(repository.getmResponse());
    }

    public void insertData(MovieSearch movieSearch) {
        repository.insertData(movieSearch);
    }

    public LiveData<List<MovieSearch>> getListLiveData() {
        return listLiveData;
    }

    private void setListLiveData() {
        listLiveData = repository.getBookMarks();
    }

    public void removedata(MovieSearch movieSearch) {
        repository.removeData(movieSearch);
    }

    public LiveData<MovieResponse> getMovieResponseLiveData() {
        return movieResponseLiveData;
    }

    private void setMovieResponseLiveData(LiveData<MovieResponse> data) {
        movieResponseLiveData = data;
    }

    public void loadLDetails(String id){
        repository.loadDetails(id);
        detailLiveData = repository.getDetails();
    }

    public LiveData<MovieDetail> getDetailLiveData(){
        return detailLiveData;
    }
}
