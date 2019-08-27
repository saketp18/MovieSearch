package com.lite.moviesearch.repo.bookmark;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.lite.moviesearch.models.MovieSearch;

@Database(entities = {MovieSearch.class}, exportSchema = false, version = 2)
public abstract class BookMarkDatabase extends RoomDatabase {

    public abstract BookmarDao doDaoModelAccess();
}
