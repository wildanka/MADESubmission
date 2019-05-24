package com.wildanka.moviecatalogue.viewmodel;

import com.wildanka.moviecatalogue.data.SearchRepo;
import com.wildanka.moviecatalogue.model.entity.Movie;
import com.wildanka.moviecatalogue.model.entity.TvShow;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SearchDialogViewModel extends ViewModel {
    private LiveData<List<Movie>> searchMovieList;
    private LiveData<List<TvShow>> searchTVShowsList;

    public LiveData<List<Movie>> getSearchMovieList(String language, String searchQuery) {
        loadSearchMovie(language, searchQuery);
        return searchMovieList;
    }

    private void loadSearchMovie(String language, String searchQuery){
        searchMovieList = SearchRepo.getInstance().getSearchMovieList(language, searchQuery);
    }

    public LiveData<List<TvShow>> getSearchTVShowsList(String language, String searchQuery) {
        loadSearchTVShows(language, searchQuery);
        return searchTVShowsList;
    }

    private void loadSearchTVShows(String language, String searchQuery){
        searchTVShowsList = SearchRepo.getInstance().getSearchTVShow(language, searchQuery);
    }
}
