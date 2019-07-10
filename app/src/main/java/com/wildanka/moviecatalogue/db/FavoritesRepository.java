package com.wildanka.moviecatalogue.db;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.wildanka.moviecatalogue.model.entity.Movie;
import com.wildanka.moviecatalogue.model.entity.TvShow;

import java.util.List;

public class FavoritesRepository {
    private static final String TAG = "FavoritesRepository";
    private static final String DB_NAME = "db_favorites_movie";
    private MoviesDAO mMoviesDAO;
    private LiveData<List<Movie>> favoritesMovieLists;
    private LiveData<Movie> mMovieList;
    private LiveData<TvShow> mTvShowList;

    public FavoritesRepository(Application application) {
        FavoritesMovieRoomDatabase database = FavoritesMovieRoomDatabase.getInstance(application);
        this.mMoviesDAO = database.moviesDAO();
        //call SQLite DB getter
        this.favoritesMovieLists = mMoviesDAO.selectFavoritesMovies();
    }

    //region movie
    public void insertMovieDB(Movie movie) {
        new insertMovieData(mMoviesDAO).execute(movie);
        Log.e(TAG, "movie data: " + movie.getIdMovie() + " with title : " + movie.getTitle() + " Inserted");
    }

    public void removeMovieFromFavorites(Movie movie) {
        new removeMovieData(mMoviesDAO).execute(movie);
        Log.e(TAG, "movie data: " + movie.getIdMovie() + " with title : " + movie.getTitle() +  " Removed");
    }

    public LiveData<List<Movie>> loadMovieDatabase() {
        System.out.println(TAG+" | "+mMoviesDAO.selectFavoritesMovies().getValue());
        return mMoviesDAO.selectFavoritesMovies();
    }

    public LiveData<Movie> checkIsFavoriteMovie(String idMovie) {
        final MutableLiveData<Boolean> isFavorites = new MutableLiveData<>();
        final LiveData<Movie> resMovieData = mMoviesDAO.checkFavoriteMovies(idMovie);
        this.mMovieList = mMoviesDAO.checkFavoriteMovies(idMovie);

        if (resMovieData != null) {
            Log.e(TAG, "favorite movies: " + resMovieData.toString());
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

    //region tvShow
    public void insertTVShowDB(TvShow tvShow) {
        new insertTVShowData(mMoviesDAO).execute(tvShow);
        Log.e(TAG, "tv show data: " + tvShow.getIdTVShow() + " with title : " + tvShow.getTitle() + " Inserted");
    }
    public void removeTVShowFromFavorites(TvShow tvShow) {
        new removeTVShowData(mMoviesDAO).execute(tvShow);
        Log.e(TAG, "movie data: " + tvShow.getIdTVShow() + " with title : " + tvShow.getTitle() + " removed");
    }

    public LiveData<List<TvShow>> loadTVShowDatabase() {
        return mMoviesDAO.selectFavoritesTVShow();
    }
    public LiveData<TvShow> checkIsFavoriteTVShow(String idTVShow) {
        final MutableLiveData<Boolean> isFavorites = new MutableLiveData<>();
        final LiveData<TvShow> resMovieData = mMoviesDAO.checkFavoriteTVShow(idTVShow);
        this.mTvShowList = mMoviesDAO.checkFavoriteTVShow(idTVShow);

        if (resMovieData != null) {
            Log.e(TAG, "favorite TV Show: " + resMovieData.getValue());
        } else {
            Log.e(TAG, "movie is not a favorite: " + resMovieData.getValue());
        }
        return resMovieData;
    }

    private static class insertTVShowData extends AsyncTask<TvShow, Void, String> {
        private MoviesDAO moviesDAOAsyncTask;

        insertTVShowData(MoviesDAO moviesDAOAsyncTask) {
            this.moviesDAOAsyncTask = moviesDAOAsyncTask;
        }

        @Override
        protected String doInBackground(TvShow... tvShows) {
            moviesDAOAsyncTask.insertTVShow(tvShows[0]);
            return "Favorite TV Show with title "+tvShows[0].getTitle()+"Succesfully Added";
        }

        @Override
        protected void onPostExecute(String s) {
            Log.e(TAG, "onPostExecute: "+s );
            super.onPostExecute(s);
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
