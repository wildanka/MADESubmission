package com.wildanka.moviecatalogue.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wildanka.moviecatalogue.R;
import com.wildanka.moviecatalogue.model.entity.Movie;
import com.wildanka.moviecatalogue.model.entity.TvShow;
import com.wildanka.moviecatalogue.view.adapter.MovieRVAdapter;
import com.wildanka.moviecatalogue.view.adapter.TvShowRVAdapter;
import com.wildanka.moviecatalogue.viewmodel.MovieTVViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowFragment extends Fragment {
    private String[] dataTitle;
    private String[] dataOverview;
    private String[] dataShortDescription;
    private String[] dataYear;
    private String[] dataRating;
    private TypedArray dataPoster;
    private TvShowRVAdapter adapter;
    private MovieTVViewModel viewModel;

    public TVShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_movie);
        viewModel = ViewModelProviders.of(this).get(MovieTVViewModel.class);

        //initialize the data
        prepareStringArray();

        adapter = new TvShowRVAdapter(getActivity());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        viewModel.getTVLists().observe(this, new Observer<List<TvShow>>() {
            @Override
            public void onChanged(@Nullable List<TvShow> tvShows) {
                if (tvShows == null) {

                }else{
                    System.out.println(tvShows.get(0).getTitle());
                    adapter.setListMovie(tvShows);
                }
            }
        });
        return rootView;
    }



    private void prepareStringArray(){
        dataTitle = getResources().getStringArray(R.array.tv_data_title);
        dataOverview = getResources().getStringArray(R.array.tv_data_ov);
        dataShortDescription = getResources().getStringArray(R.array.tv_data_short_desc);
        dataPoster = getResources().obtainTypedArray(R.array.tv_data_poster);
        dataYear = getResources().getStringArray(R.array.tv_data_year);
        dataRating = getResources().getStringArray(R.array.tv_data_rating);
    }
}
