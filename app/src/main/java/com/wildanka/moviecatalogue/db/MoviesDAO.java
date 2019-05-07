package com.wildanka.moviecatalogue.db;

import com.wildanka.moviecatalogue.model.entity.Movie;
import com.wildanka.moviecatalogue.model.entity.TvShow;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface MoviesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movieInsertion);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTVShow(TvShow tvShowDeletion);

    @Delete
    void removeMovieFromFavorites(Movie movieDeletions);

    @Delete
    void removeTVShowFromFavorites(TvShow tvShowDeletion);

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> selectFavoritesMovies();

    @Query("SELECT * FROM tv_shows")
    LiveData<List<TvShow>> selectFavoritesTVShow();

    @Query("SELECT * FROM movies WHERE idMovie LIKE :idMovie LIMIT 1")
    LiveData<Movie> checkFavoriteMovies(String idMovie);

    @Query("SELECT * FROM tv_shows WHERE idTVShow LIKE :idTVShow LIMIT 1")
    LiveData<TvShow> checkFavoriteTVShow(String idTVShow);


}
