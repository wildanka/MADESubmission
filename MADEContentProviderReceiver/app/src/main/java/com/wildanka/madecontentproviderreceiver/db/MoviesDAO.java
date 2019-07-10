package com.wildanka.madecontentproviderreceiver.db;

import android.database.Cursor;

import com.wildanka.madecontentproviderreceiver.model.entity.Movie;
import com.wildanka.madecontentproviderreceiver.model.entity.TvShow;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MoviesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movieInsertion);

    @Insert
    long insertMovieContentProvider(Movie movieInsertion);

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


    @Query("SELECT * FROM movies")
    Cursor selectFavoritesMoviesCursor();

    @Query("SELECT * FROM " +Movie.TABLE_NAME+" WHERE "+Movie.COLUMN_ID+" = :id")
    Cursor selectFavoritesMoviesCursorById(long id);
    /**
     * Delete a movie by the MovieID.
     *
     * @param idMovie The row ID.
     * @return A number of movie deleted. This should always be {@code 1}.
     */
    @Query("DELETE FROM " + Movie.TABLE_NAME + " WHERE " + Movie.COLUMN_ID + " = :idMovie")
    int deleteById(String idMovie);

    /**
     * Update the movie. The cheese is identified by the row ID.
     *
     * @param movie The cheese to update.
     * @return A number of movies updated. This should always be {@code 1}.
     */
    @Update
    int update(Movie movie);

}
