package com.wildanka.moviecatalogue.view;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wildanka.moviecatalogue.R;
import com.wildanka.moviecatalogue.model.entity.Movie;


public class MovieDetailActivity extends AppCompatActivity {
    private TextView tvMovieTitle, tvRating, tvReleaseDate, tvOverview;
    private ImageView ivMoviePoster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        //receive the intent extras
        Movie movie = getIntent().getParcelableExtra("selectedMovie");

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
        if (Integer.parseInt(movie.getRating()) < 60){
            tvRating.setTextColor(ContextCompat.getColor(this,R.color.colorRed));
        }else if(Integer.parseInt(movie.getRating()) < 70){
            tvRating.setTextColor(ContextCompat.getColor(this,R.color.colorYellow));
        }else{
            tvRating.setTextColor(ContextCompat.getColor(this,R.color.colorGreen));
        }
        tvMovieTitle.setText(movie.getTitle());
        tvRating.setText(MovieDetailActivity.this.getString(R.string.rating_in_percent,movie.getRating()));
        tvReleaseDate.setText(movie.getDateYear());
        tvOverview.setText(movie.getOverview());
        String MOVIE_POSTER_URI = "https://image.tmdb.org/t/p/w185/"+movie.getPosterPath();
        Picasso.get().load(MOVIE_POSTER_URI).into(ivMoviePoster);
//        ivMoviePoster.setImageDrawable(getResources().getDrawable(movie.getIvPoster()));
    }
}
