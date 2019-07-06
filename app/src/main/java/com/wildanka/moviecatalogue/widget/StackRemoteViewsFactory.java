package com.wildanka.moviecatalogue.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.wildanka.moviecatalogue.R;

import java.util.ArrayList;
import java.util.List;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private final Context mContext;

    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.darth_vader));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.star_wars_logo));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.storm_trooper));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.starwars));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.falcon));
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
        remoteViews.setImageViewBitmap(R.id.imageView, mWidgetItems.get(position));

        Bundle extras = new Bundle();
        extras.putInt(MovieCatalogFavoritesWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.imageView,fillIntent);
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
