package com.wildanka.moviecatalogue.viewmodel;

import com.wildanka.moviecatalogue.data.SearchRepo;
import com.wildanka.moviecatalogue.model.entity.Movie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SearchDialogViewModel extends ViewModel {
    private LiveData<List<Movie>> searchMovieList;

    public LiveData<List<Movie>> getSearchMovieList(String language, String searchQuery) {
        loadSearchMovie(language, searchQuery);
        return searchMovieList;
    }

    private void loadSearchMovie(String language, String searchQuery){
        searchMovieList = SearchRepo.getInstance().getSearchMovieList(language, searchQuery);
    }

    public void setSearchMovieList(LiveData<List<Movie>> searchMovieList) {
        this.searchMovieList = searchMovieList;
    }
}
