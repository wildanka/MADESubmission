package com.wildanka.moviecatalogue.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.wildanka.moviecatalogue.model.entity.Movie;
import com.wildanka.moviecatalogue.data.MovieRepo;

import java.util.List;

public class MovieTVViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movieLists;

    public LiveData<List<Movie>> getMovieLists() {
        if (movieLists == null) {
            loadMovieLists();
        }
        return movieLists;
    }

    private void loadMovieLists(){
        movieLists = MovieRepo.getInstance().getMovieList();
    }
}
