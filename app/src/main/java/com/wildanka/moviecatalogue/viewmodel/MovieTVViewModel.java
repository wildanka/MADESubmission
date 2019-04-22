package com.wildanka.moviecatalogue.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wildanka.moviecatalogue.model.entity.Movie;
import com.wildanka.moviecatalogue.data.MovieRepo;
import com.wildanka.moviecatalogue.model.entity.TvShow;

import java.util.List;

public class MovieTVViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movieLists;
    private MutableLiveData<List<TvShow>> tvLists;

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


    private void loadMovieLists(String language){
        movieLists = MovieRepo.getInstance().getMovieList(language);
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

    private void loadTVLists(String language){
        tvLists = MovieRepo.getInstance().getTVList(language);
    }
}
