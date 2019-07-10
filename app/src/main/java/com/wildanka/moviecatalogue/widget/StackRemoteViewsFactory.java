package com.wildanka.moviecatalogue.widget;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.lifecycle.LiveData;

import com.squareup.picasso.Picasso;
import com.wildanka.moviecatalogue.MovieCatalog;
import com.wildanka.moviecatalogue.R;
import com.wildanka.moviecatalogue.db.FavoritesMovieRoomDatabase;
import com.wildanka.moviecatalogue.db.FavoritesRepository;
import com.wildanka.moviecatalogue.db.MoviesDAO;
import com.wildanka.moviecatalogue.model.entity.Movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private final List<Uri> moviePosterUri = new ArrayList<>();
    private final Context mContext;
    private FavoritesMovieRoomDatabase database;
    private FavoritesRepository repository;
    private LiveData<List<Movie>> movieLists;
    private static final String TAG = "StackRemoteViewsFactory";
    private MoviesDAO mMoviesDAO;

    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

        movieLists = loadMovieDatabase(); // 3.

//        Log.e(TAG, "WIDGET onDataSetChanged: "+movieLists.getValue().get(0).getTitle());

    }

    private LiveData<List<Movie>> loadMovieDatabase() {
        //Load data from SQLite
        database = FavoritesMovieRoomDatabase.getInstance(mContext); // 1. Buka Koneksi Database
        this.mMoviesDAO = database.moviesDAO(); //2. instansiasi dao
        Log.e(TAG, "loadMovieDatabase: INI WIDGET ");
        if (mMoviesDAO.selectFavoritesMovies() != null) {
            Log.e(TAG, "WIDGET TIDAK NULL ");
            System.out.println(TAG+" | "+mMoviesDAO.selectFavoritesMovies().getValue());
        }else{
            Log.e(TAG, "WIDGET NULL ");
        }
        return mMoviesDAO.selectFavoritesMovies();
    }

    @Override
    public void onDataSetChanged() {
        //TODO: Load Data dari SQLite, kemudian tampilkan poster berdasarkan URL yang disimpan

        loadMovieDatabase();
//        String MOVIE_POSTER_URI = "https://image.tmdb.org/t/p/w185/" + "b9uYMMbm87IBFOq59pppvkkkgNg.jpg";
        String MOVIE_POSTER_URI = "https://image.tmdb.org/t/p/w92/";

        moviePosterUri.add(Uri.parse(MOVIE_POSTER_URI + "b9uYMMbm87IBFOq59pppvkkkgNg.jpg"));
        moviePosterUri.add(Uri.parse(MOVIE_POSTER_URI + "lIv1QinFqz4dlp5U4lQ6HaiskOZ.jpg"));
        moviePosterUri.add(Uri.parse(MOVIE_POSTER_URI + "gdiLTof3rbPDAmPaCf4g6op46bj.jpg"));
        moviePosterUri.add(Uri.parse(MOVIE_POSTER_URI + "2eQfjqlvPAxd9aLDs8DvsKLnfed.jpg"));
        try {
            Bitmap poster = Picasso.get().load(MOVIE_POSTER_URI + "b9uYMMbm87IBFOq59pppvkkkgNg.jpg").get();
            Bitmap poster1 = Picasso.get().load(MOVIE_POSTER_URI + "lIv1QinFqz4dlp5U4lQ6HaiskOZ.jpg").get();
            Bitmap poster2 = Picasso.get().load(MOVIE_POSTER_URI + "gdiLTof3rbPDAmPaCf4g6op46bj.jpg").get();
            Bitmap poster3 = Picasso.get().load(MOVIE_POSTER_URI + "2eQfjqlvPAxd9aLDs8DvsKLnfed.jpg").get();
            mWidgetItems.add(poster);
            mWidgetItems.add(poster1);
            mWidgetItems.add(poster2);
            mWidgetItems.add(poster3);
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.darth_vader));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.star_wars_logo));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.storm_trooper));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.starwars));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.falcon));*/
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mWidgetItems != null){ // karena sekarang dataset stackwidget masih ditanam, maka
            // "mWidgetItems != null" akan selalu menghasilkan 'true'
            return mWidgetItems.size();
        }else{
            return 0;
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        remoteViews.setImageViewBitmap(R.id.iv_movie_poster_widget, mWidgetItems.get(position));

        Bundle extras = new Bundle();
        extras.putInt(MovieCatalogFavoritesWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.iv_movie_poster_widget,fillIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
