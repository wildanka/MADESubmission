package com.wildanka.moviecatalogue.db;

import com.wildanka.moviecatalogue.model.entity.Movie;

import androidx.room.Database;
import androidx.room.RoomDatabase;

//@Database(entities = (Movie.class), version = 1)
public abstract class MovieRoomDatabase extends RoomDatabase {
    public abstract MoviesDAO moviesDAO();
    private static MovieRoomDatabase INSTANCE;

}
