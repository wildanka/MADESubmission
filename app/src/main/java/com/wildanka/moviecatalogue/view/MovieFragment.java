package com.wildanka.moviecatalogue.view;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.res.TypedArray;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.wildanka.moviecatalogue.R;
import com.wildanka.moviecatalogue.model.entity.Movie;
import com.wildanka.moviecatalogue.util.SharedPref;
import com.wildanka.moviecatalogue.view.adapter.MovieRVAdapter;
import com.wildanka.moviecatalogue.viewmodel.MovieTVViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private String[] dataTitle;
    private String[] dataOverview;
    private String[] dataShortDescription;
    private String[] dataYear;
    private String[] dataRating;
    private TypedArray dataPoster;
    private MovieRVAdapter adapter;
    private MovieTVViewModel viewModel;
    private ProgressBar loadingBar;
    private static final String TAG = "MovieFragment";

    public MovieFragment() {
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
        viewModel = ViewModelProviders.of(this).get(MovieTVViewModel.class);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_movie);
        loadingBar = rootView.findViewById(R.id.pb_movie_fragment);

        //initialize the data
//        prepareStringArray();

        if (savedInstanceState != null){
            viewModel.getMovieLists(language).observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> movies) {
                    System.out.println(movies.get(0).getTitle());
                    adapter.setListMovie(movies);
                    loadingBar.setVisibility(View.INVISIBLE);
                }
            });
        }else{
            viewModel.forceGetMovieLists(language).observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> movies) {
                    System.out.println(movies.get(0).getTitle());
                    adapter.setListMovie(movies);
                    loadingBar.setVisibility(View.INVISIBLE);
                }
            });
        }


        adapter = new MovieRVAdapter(getActivity());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        loadingBar.setVisibility(View.VISIBLE);
        return rootView;
    }


    private void prepareStringArray(){
        dataTitle = getResources().getStringArray(R.array.data_title);
        dataOverview = getResources().getStringArray(R.array.data_ov);
        dataShortDescription = getResources().getStringArray(R.array.data_short_desc);
        dataPoster = getResources().obtainTypedArray(R.array.data_poster);
        dataYear = getResources().getStringArray(R.array.data_year);
        dataRating = getResources().getStringArray(R.array.data_rating);
    }

    public void onRefresh(String language) {
        loadingBar.setVisibility(View.VISIBLE);
        viewModel.forceGetMovieLists(language).observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if (movies == null) {
                    Log.e(TAG, "OnRefresh MOooooooooooo");
                }else{
                    System.out.println(movies.get(0).getTitle());
                    adapter.setListMovie(movies);
                    loadingBar.setVisibility(View.INVISIBLE);
                }
            }
        });
        Log.e(TAG, "OnRefresh MOoooooooooooVIIIIIIIIIIIe");
    }

}