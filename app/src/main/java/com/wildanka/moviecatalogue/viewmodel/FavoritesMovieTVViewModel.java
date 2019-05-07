package com.wildanka.moviecatalogue.viewmodel;

import android.app.Application;

import com.wildanka.moviecatalogue.data.MovieRepo;
import com.wildanka.moviecatalogue.db.FavoritesRepository;
import com.wildanka.moviecatalogue.model.entity.Movie;
import com.wildanka.moviecatalogue.model.entity.TvShow;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class FavoritesMovieTVViewModel extends AndroidViewModel {
    private static final String TAG = "FavoritesMovieTVViewMod";
    //instance the repository
    private FavoritesRepository repository;
    private LiveData<List<Movie>> movieLists;
    private LiveData<List<TvShow>> tvLists;
    private LiveData<Movie> movieLiveData;

    public FavoritesMovieTVViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoritesRepository(application);
    }

    //region movie
    public void insertFavoriteMovieData(Movie movie){
        repository.insertMovieDB(movie);
    }
    public void removeFavoriteMovieData(Movie movies){
        repository.removeMovieFromFavorites(movies);
    }

    public LiveData<Movie> checkFavoritesElseFetch(String movieID){
        loadFavoritesMovie(movieID);
        return repository.checkFavoritesElseFetch(movieID);
    }
    public LiveData<List<Movie>> getMovieLists(String language) {
        if (movieLists == null) {
            loadMovieLists(language);
        }
        return movieLists;
    }
    public LiveData<List<Movie>> forceGetMovieLists(String language) {
        loadMovieLists(language);
        return movieLists;
    }
    private void loadFavoritesMovie(String movieID){
        movieLiveData = repository.checkFavoritesElseFetch(movieID);
    }
    private void loadMovieLists(String language){
        movieLists = repository.loadMovieDatabase();
    }
    //endregion movie

    //region tvShow
    public void insertFavoriteTVShowData(TvShow tvShow){
        repository.insertTVShowDB(tvShow);
    }
    public void removeFavoriteTVShowData(TvShow tvShow){
        repository.removeTVShowFromFavorites(tvShow);
    }

    public LiveData<TvShow> checkFavoritesElseFetchTV(String tvShowID){
        loadFavoritesTVShow(tvShowID);
        return repository.checkFavoritesElseFetchTV(tvShowID);
    }
    public LiveData<List<TvShow>> getTVLists(String language) {
        if (tvLists == null) {
            loadTVLists(language);
        }
        return tvLists;
    }
    public LiveData<List<TvShow>> forceGetTVLists(String language) {
        loadTVLists(language);
        return tvLists;
    }
    private void loadFavoritesTVShow(String tvShowID){
        movieLiveData = repository.checkFavoritesElseFetch(tvShowID);
    }
    private void loadTVLists(String language){
        tvLists = repository.loadTVShowDatabase();
    }
    //endregion tvShow

}
