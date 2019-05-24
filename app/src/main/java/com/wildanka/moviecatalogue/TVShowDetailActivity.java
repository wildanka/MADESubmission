package com.wildanka.moviecatalogue;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wildanka.moviecatalogue.model.entity.TvShow;
import com.wildanka.moviecatalogue.viewmodel.FavoritesMovieTVViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class TVShowDetailActivity extends AppCompatActivity {
    private TextView tvMovieTitle, tvRating, tvReleaseDate, tvOverview;
    private ImageView ivMoviePoster;
    private Boolean isFavorites = false;
    private static final String TAG = "MovieDetailActivity";
    private Menu menuItem;
    private String movieID;
    private String movieTitle;
    private String MOVIE_POSTER_URI;
    private String movieOverview;
    private String movieRating;
    private String movieReleaseDate;
    private String movieOriginalLanguage;
    private String moviePopularity;
    private Boolean movieIsAdult;
    private FavoritesMovieTVViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        viewModel = ViewModelProviders.of(this).get(FavoritesMovieTVViewModel.class);
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
        tvRating.setText(TVShowDetailActivity.this.getString(R.string.rating_in_percent,movieRating));
        tvReleaseDate.setText(movieReleaseDate);
        tvOverview.setText(movieOverview);
//        String MOVIE_POSTER_URI = "https://image.tmdb.org/t/p/w185/"+movie.getPosterPath();
        Picasso.get().load(MOVIE_POSTER_URI).into(ivMoviePoster);

        viewModel.checkFavoritesElseFetchTV(movieID).observe(this, new Observer<TvShow>() {
            @Override
            public void onChanged(TvShow tvShow) {
                if (tvShow != null){
                    Log.d(TAG, "onChanged: "+movieID+" is a favorite");
                    isFavorites = true;
                }else{
                    Log.d(TAG, "onChanged: "+movieID+" not a favorite");
                    isFavorites = false;
                }
            }
        });
    }

    private void addToFavorites(){
        //Construct the Movie Object
        TvShow tvShow = new TvShow(movieID,movieRating, movieTitle, movieTitle, movieReleaseDate, movieRating, movieOverview, MOVIE_POSTER_URI, movieOriginalLanguage, moviePopularity );
        viewModel.insertFavoriteTVShowData(tvShow);
        isFavorites=true;
        setFavorite();
    }


    private void removeFromFavorites(){
        TvShow tvShow = new TvShow(movieID,movieRating, movieTitle, movieTitle, movieReleaseDate, movieRating, movieOverview, MOVIE_POSTER_URI, movieOriginalLanguage, moviePopularity );
        viewModel.removeFavoriteTVShowData(tvShow);
        isFavorites=false;
        setFavorite();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_fav_menu, menu);
        menuItem = menu;
        setFavorite();
        return super.onCreateOptionsMenu(menu);
    }

    private void setFavorite() {
        if (isFavorites){
            menuItem.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp));
        }else{
            menuItem.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_bahasa:
                if (isFavorites){
                    removeFromFavorites();
                    setFavorite();
                    Toast.makeText(TVShowDetailActivity.this, movieTitle+" removed from Favorites",Toast.LENGTH_SHORT).show();
                }else{
                    addToFavorites();
                    setFavorite();
                    Toast.makeText(TVShowDetailActivity.this, movieTitle+" added to Favorites",Toast.LENGTH_SHORT).show();
                }
                System.out.println("clicked menu");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
