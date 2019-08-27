package com.lite.moviesearch.repo.bookmark;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.lite.moviesearch.models.MovieSearch;

import java.util.List;

@Dao
public interface BookmarDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(MovieSearch movieSearch);

    @Query("SELECT * FROM MovieSearch WHERE imdbID = :id")
    MovieSearch getMovie(String id);

    @Query("SELECT * FROM MovieSearch")
    LiveData<List<MovieSearch>> getMovies();

    @Query("DELETE FROM MovieSearch WHERE imdbID = :id")
    void removeData(String id);
}
