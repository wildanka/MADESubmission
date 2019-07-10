package com.wildanka.moviecatalogue.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.wildanka.moviecatalogue.model.entity.Movie;
import com.wildanka.moviecatalogue.model.entity.TvShow;

@Database(entities = {Movie.class, TvShow.class}, version = 1, exportSchema = false)
public abstract class FavoritesMovieRoomDatabase extends RoomDatabase {
    private static FavoritesMovieRoomDatabase INSTANCE;
    public abstract MoviesDAO moviesDAO();

    public static synchronized FavoritesMovieRoomDatabase getInstance(Context context){
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FavoritesMovieRoomDatabase.class, "favorites_databse").fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}
