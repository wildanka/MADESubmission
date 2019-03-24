package com.wildanka.moviecatalogue.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.wildanka.moviecatalogue.R;
import com.wildanka.moviecatalogue.model.Movie;

import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity {
    private TextView tvMovieTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        tvMovieTitle = findViewById(R.id.tv_movie_details_title);
        Movie movie = getIntent().getParcelableExtra("selectedMovie");
        tvMovieTitle.setText(movie.getTitle()+" | "+movie.getRating());
    }
}
