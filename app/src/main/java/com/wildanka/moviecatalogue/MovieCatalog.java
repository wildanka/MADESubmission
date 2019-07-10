package com.wildanka.moviecatalogue;

import android.app.Application;
import android.content.Context;

public class MovieCatalog extends Application {
    private static Context context;

    @Override
    public void onCreate(){
        super.onCreate();
        MovieCatalog.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MovieCatalog.context;
    }

}
