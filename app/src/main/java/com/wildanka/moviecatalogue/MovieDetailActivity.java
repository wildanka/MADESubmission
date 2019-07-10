package com.wildanka.moviecatalogue;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wildanka.moviecatalogue.model.entity.Movie;
import com.wildanka.moviecatalogue.viewmodel.FavoritesMovieTVViewModel;
import com.wildanka.moviecatalogue.widget.MovieCatalogFavoritesWidget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class MovieDetailActivity extends AppCompatActivity {
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

        viewModel.checkFavoritesElseFetch(movieID).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                if (movie != null){
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
//        Movie movie = new Movie(movieID,movieRating, movieTitle, movieReleaseDate, movieRating, movieOverview, MOVIE_POSTER_URI, movieOriginalLanguage, moviePopularity, movieIsAdult);
        Movie movie = new Movie();
        movie.setIdMovie(movieID)
                .setVoteCount(movieRating)
                .setTitle(movieTitle)
                .setDateYear(movieReleaseDate)
                .setRating(movieRating)
                .setOverview(movieOverview)
                .setPosterPath(MOVIE_POSTER_URI)
                .setOriginalLanguage(movieOriginalLanguage)
                .setPopularity(moviePopularity)
                .setAdult(movieIsAdult);
        viewModel.insertFavoriteMovieData(movie);
        isFavorites=true;
        setFavorite();

        //TODO : Lakukan update terhadap data di stackView,
        // Caranya? trigger AppWidgetManager notifyAppWidgetViewDataChanged
//
//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
//        appWidgetManager.notifyAppWidgetViewDataChanged(MovieCatalogFavoritesWidget.class,);

        Intent intent = new Intent(this, MovieCatalogFavoritesWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        int[] ids = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), MovieCatalogFavoritesWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }


    private void removeFromFavorites(){
//        Movie movie = new Movie(movieID,movieRating, movieTitle, movieReleaseDate, movieRating, movieOverview, MOVIE_POSTER_URI, movieOriginalLanguage, moviePopularity, movieIsAdult);
        Movie movie = new Movie();
        movie.setIdMovie(movieID)
                .setVoteCount(movieRating)
                .setTitle(movieTitle)
                .setDateYear(movieReleaseDate)
                .setRating(movieRating)
                .setOverview(movieOverview)
                .setPosterPath(MOVIE_POSTER_URI)
                .setOriginalLanguage(movieOriginalLanguage)
                .setPopularity(moviePopularity)
                .setAdult(movieIsAdult);
        viewModel.removeFavoriteMovieData(movie);
        isFavorites=false;
        setFavorite();

        Intent intent = new Intent(this, MovieCatalogFavoritesWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        int[] ids = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), MovieCatalogFavoritesWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
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
                    Toast.makeText(MovieDetailActivity.this, movieTitle+" removed from Favorites",Toast.LENGTH_SHORT).show();
                }else{
                    addToFavorites();
                    setFavorite();
                    Toast.makeText(MovieDetailActivity.this, movieTitle+" added to Favorites",Toast.LENGTH_SHORT).show();
                }
                System.out.println("clicked menu");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
