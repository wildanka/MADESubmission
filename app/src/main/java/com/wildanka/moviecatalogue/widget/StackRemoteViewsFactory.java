package com.wildanka.moviecatalogue.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.squareup.picasso.Picasso;
import com.wildanka.moviecatalogue.R;
import com.wildanka.moviecatalogue.db.FavoritesMovieRoomDatabase;
import com.wildanka.moviecatalogue.model.entity.Movie;

import java.io.IOException;
import java.util.List;

import static com.wildanka.moviecatalogue.BuildConfig.URL_IMG_WIDGET;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final Context mContext;
    private List<Movie> favoriteMovieLists;
    private static final String TAG = "StackRemoteViewsFactory";

    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate: WIDGET" );
    }

    @Override
    public void onDataSetChanged() {
        //Load Data dari SQLite, kemudian tampilkan poster berdasarkan URL yang disimpan
        favoriteMovieLists = FavoritesMovieRoomDatabase.getInstance(mContext).moviesDAO().selectFavoritesMoviesSync();
        System.out.println(TAG + " | " + favoriteMovieLists.get(0).getPosterPath());
        Log.e(TAG, "onDataSetChanged: WIDGET");
        for (Movie movie : favoriteMovieLists) {
            Log.e(TAG, "onDataSetChanged: "+ URL_IMG_WIDGET+movie.getPosterPath());
        }

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (favoriteMovieLists != null){ // karena sekarang dataset stackwidget masih ditanam, maka
            // "mWidgetItems != null" akan selalu menghasilkan 'true'
            return favoriteMovieLists.size();
        }else{
            return 0;
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        Log.e(TAG, "getViewAt: "+ URL_IMG_WIDGET+favoriteMovieLists.get(position).getPosterPath());
        try {
            Bitmap poster = Picasso.get().load(Uri.parse(URL_IMG_WIDGET+favoriteMovieLists.get(position).getPosterPath())).get();
            remoteViews.setImageViewBitmap(R.id.iv_movie_poster_widget, poster);
        } catch (IOException e) {
            e.printStackTrace();
        }

        remoteViews.setTextViewText(R.id.tv_movie_title_widget, favoriteMovieLists.get(position).getTitle());
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
