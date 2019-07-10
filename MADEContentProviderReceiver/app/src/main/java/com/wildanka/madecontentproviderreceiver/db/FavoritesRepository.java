package com.wildanka.madecontentproviderreceiver.db;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.wildanka.madecontentproviderreceiver.model.entity.Movie;
import com.wildanka.madecontentproviderreceiver.model.entity.TvShow;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class FavoritesRepository {
    private static final String TAG = "FavoritesRepository";
    private static final String DB_NAME = "db_favorites_movie";
    private MoviesDAO mMoviesDAO;
    private LiveData<List<Movie>> favoritesMovieLists;
    private LiveData<Movie> mMovieList;

    public FavoritesRepository(Application application) {
        FavoritesMovieRoomDatabase database = FavoritesMovieRoomDatabase.getInstance(application);
        this.mMoviesDAO = database.moviesDAO();
        //call SQLite DB getter
    }

    //region movie
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

    public LiveData<Movie> checkFavoritesElseFetch(String idMovie) {
        final MutableLiveData<Boolean> isFavorites = new MutableLiveData<>();
        final LiveData<Movie> resMovieData = mMoviesDAO.checkFavoriteMovies(idMovie);
        this.mMovieList = mMoviesDAO.checkFavoriteMovies(idMovie);

        if (resMovieData != null) {
            Log.e(TAG, "favorite movies: " + resMovieData.getValue());
        } else {
            Log.e(TAG, "movie is not a favorite: " + resMovieData.getValue());
        }
        return resMovieData;
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
    //endregion movie


    private static class insertTVShowData extends AsyncTask<TvShow, Void, Void> {
        private MoviesDAO moviesDAOAsyncTask;

        insertTVShowData(MoviesDAO moviesDAOAsyncTask) {
            this.moviesDAOAsyncTask = moviesDAOAsyncTask;
        }

        @Override
        protected Void doInBackground(TvShow... tvShows) {
            moviesDAOAsyncTask.insertTVShow(tvShows[0]);
            return null;
        }
    }

    private static class removeTVShowData extends AsyncTask<TvShow, Void, Void> {
        private MoviesDAO moviesDAOAsyncTask;

        removeTVShowData(MoviesDAO moviesDAOAsyncTask) {
            this.moviesDAOAsyncTask = moviesDAOAsyncTask;
        }

        @Override
        protected Void doInBackground(TvShow... tvShows) {
            moviesDAOAsyncTask.removeTVShowFromFavorites(tvShows[0]);
            return null;
        }
    }
    //endregion tvShow
}
