package com.wildanka.moviecatalogue;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.wildanka.moviecatalogue.model.Movie;
import com.wildanka.moviecatalogue.view.MovieRVAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String[] dataTitle;
    private String[] dataOverview;
    private String[] dataShortDescription;
    private String[] dataYear;
    private String[] dataRating;
    private TypedArray dataPoster;
    private MovieRVAdapter adapter;
    private ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rv_movie);

        prepareStringArray();
        addItem();

        adapter = new MovieRVAdapter(this);
        adapter.setListMovie(movies);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void addItem(){
        movies = new ArrayList<>();
        for (int i = 0; i < dataTitle.length; i++) {
            Movie movie = new Movie(
                    dataTitle[i],
                    dataYear[i],
                    dataRating[i],
                    dataShortDescription[i],
                    dataOverview[i],
                    dataPoster.getResourceId(i,-1)
            );
            movies.add(movie);
        }
    }

    private void prepareStringArray(){
        dataTitle = getResources().getStringArray(R.array.data_title);
        dataOverview = getResources().getStringArray(R.array.data_ov);
        dataShortDescription = getResources().getStringArray(R.array.data_short_desc);
        dataPoster = getResources().obtainTypedArray(R.array.data_poster);
        dataYear = getResources().getStringArray(R.array.data_year);
        dataRating = getResources().getStringArray(R.array.data_rating);

    }
}
