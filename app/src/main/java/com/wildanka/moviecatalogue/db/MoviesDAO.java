package com.wildanka.moviecatalogue.db;

import com.wildanka.moviecatalogue.model.entity.Movie;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface MoviesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(Movie movieInsertion);

    @Delete
    void removeFromFavorites(Movie movieDeletion);
}
