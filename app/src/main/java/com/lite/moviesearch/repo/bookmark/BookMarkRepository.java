package com.lite.moviesearch.repo.bookmark;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.lite.moviesearch.models.MovieSearch;

import java.util.List;

public class BookMarkRepository {

    private String DB_NAME = "db_name";
    private BookMarkDatabase mBookMarkDatabase;

    public BookMarkRepository(Context context) {
        mBookMarkDatabase = Room.databaseBuilder(context, BookMarkDatabase.class, DB_NAME).build();
    }

    public void insert(MovieSearch movieSearch) {
        mBookMarkDatabase.doDaoModelAccess().insert(movieSearch);
    }

    public MovieSearch getMovie(String id) {
        return mBookMarkDatabase.doDaoModelAccess().getMovie(id);
    }

    public LiveData<List<MovieSearch>> getBookMarks() {
        return mBookMarkDatabase.doDaoModelAccess().getMovies();
    }

    public void removeData(String id) {
        mBookMarkDatabase.doDaoModelAccess().removeData(id);
    }
}
