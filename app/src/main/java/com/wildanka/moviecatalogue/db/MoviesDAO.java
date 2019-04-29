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

    @Delete
    void removeMovieFromFavorites(Movie movieDeletions);

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertTVShow(TvShow tvShow);
//
//    @Delete
//    void removeTVShowFromFavorites(TvShow tvShowDeletion);

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> selectFavoritesMovies();

    @Query("SELECT count(*) FROM movies WHERE idMovie LIKE :idMovie")
    LiveData<Integer> checkFavorites(String idMovie);


//    @Query("SELECT * FROM tvShows")
//    LiveData<List<TvShow>> favoritesTVShow();
}
