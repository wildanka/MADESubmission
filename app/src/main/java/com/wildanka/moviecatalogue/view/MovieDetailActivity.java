package com.wildanka.moviecatalogue.view;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wildanka.moviecatalogue.R;


public class MovieDetailActivity extends AppCompatActivity {
    private TextView tvMovieTitle, tvRating, tvReleaseDate, tvOverview;
    private ImageView ivMoviePoster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        //receive the intent extras
        String movieID = getIntent().getStringExtra("movieId");
        String movieTitle = getIntent().getStringExtra("movieTitle");
        String MOVIE_POSTER_URI = getIntent().getStringExtra("moviePosterUri");
        String movieOverview = getIntent().getStringExtra("movieOverview");
        String movieRating = getIntent().getStringExtra("movieRating");
        String movieReleaseDate = getIntent().getStringExtra("movieReleaseDate");

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_movie_activity_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set up button
        tvMovieTitle = findViewById(R.id.tv_title);
        tvRating = findViewById(R.id.tv_rating_detail);
        tvReleaseDate = findViewById(R.id.tv_detail_year);
        tvOverview = findViewById(R.id.tv_overview);
        ivMoviePoster = findViewById(R.id.iv_movie_poster_detail);

        //let's color our life :)
        if (Double.parseDouble(movieRating)*10 < 60){
            tvRating.setTextColor(ContextCompat.getColor(this,R.color.colorRed));
        }else if(Double.parseDouble(movieRating)*10 < 70){
            tvRating.setTextColor(ContextCompat.getColor(this,R.color.colorYellow));
        }else{
            tvRating.setTextColor(ContextCompat.getColor(this,R.color.colorGreen));
        }

        tvMovieTitle.setText(movieTitle);
        tvRating.setText(MovieDetailActivity.this.getString(R.string.rating_in_percent,movieRating));
        tvReleaseDate.setText(movieReleaseDate);
        tvOverview.setText(movieOverview);
//        String MOVIE_POSTER_URI = "https://image.tmdb.org/t/p/w185/"+movie.getPosterPath();
        Picasso.get().load(MOVIE_POSTER_URI).into(ivMoviePoster);
    }
}
