package com.lite.moviesearch.repo;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lite.moviesearch.models.MovieDetail;
import com.lite.moviesearch.models.MovieResponse;
import com.lite.moviesearch.models.MovieSearch;
import com.lite.moviesearch.repo.bookmark.BookMarkRepository;
import com.lite.moviesearch.repo.remote.RetrofitClient;
import com.lite.moviesearch.util.AppConstants;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static Repository repository = null;
    private MutableLiveData<MovieResponse> mResponse = new MutableLiveData<>();
    private MutableLiveData<MovieDetail> mDetail = new MutableLiveData<>();
    private BookMarkRepository mBookMarkRepository;
    private final Executor executor = Executors.newSingleThreadExecutor();

    private Repository(Context context) {
        mBookMarkRepository = new BookMarkRepository(context);
    }

    public static Repository getInstance(Context context) {
        if (repository == null) {
            repository = new Repository(context);
        }
        return repository;
    }

    public LiveData<MovieResponse> getmResponse() {
        return mResponse;
    }

    public LiveData<MovieDetail> getDetails(){
        return mDetail;
    }

    public void loadData(String query, int page) {
        Call<MovieResponse> call = RetrofitClient.getService().getSearchResults(query, page, AppConstants.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                mResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                MovieResponse movieResponse = new MovieResponse();
                movieResponse.setResponse("Fail");
                mResponse.setValue(movieResponse);
            }
        });
    }

    public void loadDetails(String id){
        Call<MovieDetail> call = RetrofitClient.getService().getDetail(id, AppConstants.API_KEY);
        call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                if(response.isSuccessful())
                    mDetail.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {

            }
        });
    }

    public void insertData(final MovieSearch movieSearch) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (mBookMarkRepository.getMovie(movieSearch.getImdbID()) == null)
                    mBookMarkRepository.insert(movieSearch);
            }
        });
    }


    public void removeData(final MovieSearch movieSearch) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mBookMarkRepository.removeData(movieSearch.getImdbID());
            }
        });
    }

    public LiveData<List<MovieSearch>> getBookMarks() {
        return mBookMarkRepository.getBookMarks();
    }
}
