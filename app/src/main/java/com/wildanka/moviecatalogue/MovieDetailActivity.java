package com.wildanka.moviecatalogue;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wildanka.moviecatalogue.model.entity.Movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;


public class MovieDetailActivity extends AppCompatActivity {
    private TextView tvMovieTitle, tvRating, tvReleaseDate, tvOverview;
    private ImageView ivMoviePoster;
    private String movieID;
    private String movieTitle;
    private String MOVIE_POSTER_URI;
    private String movieOverview;
    private String movieRating;
    private String movieReleaseDate;
    private String movieOriginalLanguage;
    private String moviePopularity;
    private Boolean movieIsAdult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
//        movieRoomDatabase = Room.databaseBuilder(this, MovieRoomDatabase.class,"favoritesMovieDB").build();
        //receive the intent extras
        movieID = getIntent().getStringExtra("movieId");
        movieTitle = getIntent().getStringExtra("movieVoteCount");
        movieTitle = getIntent().getStringExtra("movieTitle");
        movieReleaseDate = getIntent().getStringExtra("movieReleaseDate");
        movieRating = getIntent().getStringExtra("movieRating");
        movieOverview = getIntent().getStringExtra("movieOverview");
        MOVIE_POSTER_URI = getIntent().getStringExtra("moviePosterUri");
        movieOriginalLanguage = getIntent().getStringExtra("movieOriginalLanguage");
        moviePopularity= getIntent().getStringExtra("moviePopularity");
        movieIsAdult = getIntent().getBooleanExtra("movieIsAdult",false);

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

    private void addToFavorites(){
        //Construct the Movie Object
        Movie movie = new Movie(movieID,movieRating, movieTitle, movieReleaseDate, movieRating, movieOverview, MOVIE_POSTER_URI, movieOriginalLanguage, moviePopularity, movieIsAdult);

//        MovieDetailActivity.movieRoomDatabase.moviesDAO().insertMovies(movie);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_fav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_bahasa:
                addToFavorites();
                Toast.makeText(MovieDetailActivity.this, movieTitle+" added to Favorites",Toast.LENGTH_SHORT).show();
                System.out.println("clicked menu");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
