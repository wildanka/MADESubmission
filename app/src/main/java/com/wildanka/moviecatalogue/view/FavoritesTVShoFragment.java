package com.wildanka.moviecatalogue.view;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.wildanka.moviecatalogue.R;
import com.wildanka.moviecatalogue.model.entity.Movie;
import com.wildanka.moviecatalogue.model.entity.TvShow;
import com.wildanka.moviecatalogue.util.SharedPref;
import com.wildanka.moviecatalogue.view.adapter.FavoritesMovieRVAdapter;
import com.wildanka.moviecatalogue.view.adapter.FavoritesTVShowRVAdapter;
import com.wildanka.moviecatalogue.viewmodel.FavoritesMovieTVViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FavoritesTVShoFragment extends Fragment {
    private String[] dataTitle;
    private String[] dataOverview;
    private String[] dataShortDescription;
    private String[] dataYear;
    private String[] dataRating;
    private TypedArray dataPoster;
    private FavoritesTVShowRVAdapter adapter;
    private FavoritesMovieTVViewModel viewModel;
    private ProgressBar loadingBar;
    private static final String TAG = "MovieFragment";

    public FavoritesTVShoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPref sp = new SharedPref(getActivity());
        String language = sp.loadLanguage();
        viewModel = ViewModelProviders.of(this).get(FavoritesMovieTVViewModel.class);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_movie);
        loadingBar = rootView.findViewById(R.id.pb_movie_fragment);

        //initialize the data
        prepareStringArray();

        adapter = new FavoritesTVShowRVAdapter(getActivity());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        loadingBar.setVisibility(View.VISIBLE);
        viewModel.getTVLists(language).observe(this, new Observer<List<TvShow>>() {
            @Override
            public void onChanged(@Nullable List<TvShow> tvShows) {
                if (tvShows == null || tvShows.isEmpty()) {
                    Log.d(TAG, "onChanged: No Favorite Movie Data from the Database");
                    loadingBar.setVisibility(View.INVISIBLE);
                }else{
                    System.out.println(tvShows.get(0).getTitle());
                    adapter.setListMovie(tvShows);
                    loadingBar.setVisibility(View.INVISIBLE);
                }
            }
        });
        return rootView;
    }


    private void prepareStringArray() {
        dataTitle = getResources().getStringArray(R.array.data_title);
        dataOverview = getResources().getStringArray(R.array.data_ov);
        dataShortDescription = getResources().getStringArray(R.array.data_short_desc);
        dataPoster = getResources().obtainTypedArray(R.array.data_poster);
        dataYear = getResources().getStringArray(R.array.data_year);
        dataRating = getResources().getStringArray(R.array.data_rating);
    }

    public void onRefresh(String language) {
        loadingBar.setVisibility(View.VISIBLE);
        viewModel.forceGetTVLists(language).observe(this, new Observer<List<TvShow>>() {
            @Override
            public void onChanged(@Nullable List<TvShow> tvShows) {
                if (tvShows == null) {
                    Log.e(TAG, "OnRefresh MOooooooooooo");
                } else {
                    System.out.println(tvShows.get(0).getTitle());
                    adapter.setListMovie(tvShows);
                    loadingBar.setVisibility(View.INVISIBLE);
                }
            }
        });
        Log.e(TAG, "OnRefresh MOoooooooooooVIIIIIIIIIIIe");
    }

}