package com.wildanka.moviecatalogue.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

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

    private void loadMovieLists(String language){
        movieLists = MovieRepo.getInstance().getMovieList(language);
    }

    public LiveData<List<TvShow>> getTVLists(String language) {
        if (movieLists == null) {
            loadTVLists(language);
        }
        return tvLists;
    }

    private void loadTVLists(String language){
        tvLists = MovieRepo.getInstance().getTVList(language);
    }
}
