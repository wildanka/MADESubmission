package com.wildanka.moviecatalogue.db;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.wildanka.moviecatalogue.model.entity.Movie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

public class FavoritesRepository {
    private static final String TAG = "FavoritesRepository";
    private static final String DB_NAME = "db_favorites_movie";
    private MoviesDAO mMoviesDAO;
    private LiveData<List<Movie>> mUserInboxLiveData;

    public FavoritesRepository(Application application) {
        FavoritesMovieRoomDatabase mFavoritesMovieRoomDatabase = Room.databaseBuilder(application, FavoritesMovieRoomDatabase.class, DB_NAME).build();
        this.mMoviesDAO = mFavoritesMovieRoomDatabase.moviesDAO();
        //call SQLite DB getter
        this.mUserInboxLiveData = mMoviesDAO.selectFavoritesMovies();
    }


    public void insertMovieDB(Movie movie) {
        new insertMovieData(mMoviesDAO).execute(movie);
        Log.e(TAG, "movie data: " + movie.getIdMovie() + " Inserted");
    }

    public void removeMovieFromFavorites(Movie movie) {
        new removeMovieData(mMoviesDAO).execute(movie);
        Log.e(TAG, "movie data: " + movie.getIdMovie() + " Inserted");
    }

    public LiveData<List<Movie>> loadMovieDatabase() {
        return mMoviesDAO.selectFavoritesMovies();
    }

    public LiveData<Boolean> checkMovieFavorites(String idMovie) {
        final MutableLiveData<Boolean> isFavorites = new MutableLiveData<>();
        final LiveData<Integer> resCount = mMoviesDAO.checkFavorites(idMovie);

        Log.e(TAG, "checkMovieFavorites: "+resCount.getValue());
        if (resCount.getValue() == 1) {
            isFavorites.setValue(true);
        } else {
            isFavorites.setValue(false);
        }

        return isFavorites;
    }


    private static class insertMovieData extends AsyncTask<Movie, Void, Void> {
        private MoviesDAO moviesDAOAsyncTask;

        insertMovieData(MoviesDAO moviesDAOAsyncTask) {
            this.moviesDAOAsyncTask = moviesDAOAsyncTask;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            moviesDAOAsyncTask.insertMovie(movies[0]);
            return null;
        }
    }

    private static class removeMovieData extends AsyncTask<Movie, Void, Void> {
        private MoviesDAO moviesDAOAsyncTask;

        removeMovieData(MoviesDAO moviesDAOAsyncTask) {
            this.moviesDAOAsyncTask = moviesDAOAsyncTask;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            moviesDAOAsyncTask.removeMovieFromFavorites(movies[0]);
            return null;
        }
    }

}
