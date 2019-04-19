package com.wildanka.moviecatalogue.data;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.wildanka.moviecatalogue.model.entity.Movie;
import com.wildanka.moviecatalogue.model.entity.MovieFeeds;
import com.wildanka.moviecatalogue.model.entity.TvShow;
import com.wildanka.moviecatalogue.model.entity.TvShowFeeds;
import com.wildanka.moviecatalogue.model.network.ApiMovies;
import com.wildanka.moviecatalogue.util.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wildanka.moviecatalogue.BuildConfig.API_V3_KEY;

public class MovieRepo {
    private static final String TAG = "MovieRepo";
    private static class SingletonHelper {
        private static final MovieRepo INSTANCE = new MovieRepo();
    }
    public static MovieRepo getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public MutableLiveData<List<Movie>> getMovieList(String language){
        final MutableLiveData<MovieFeeds> feeds = new MutableLiveData<>();
        final MutableLiveData<List<Movie>> data = new MutableLiveData<>();

        ApiMovies api = ApiClient.getClient().create(ApiMovies.class);
        Call<MovieFeeds> call = api.loadMovieList(API_V3_KEY, language);
        call.enqueue(new Callback<MovieFeeds>() {
            @Override
            public void onResponse(Call<MovieFeeds> call, Response<MovieFeeds> response) {
                if (response.code()==200){
                    feeds.setValue(response.body());
                    data.setValue(feeds.getValue().getMovies());

                }else{
                    Log.e(TAG, "onResponse: "+response.errorBody().toString());
                    Log.e(TAG, "onResponse: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<MovieFeeds> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage());
            }
        });
        return data;
    }
    public MutableLiveData<List<TvShow>> getTVList(String language){
        final MutableLiveData<TvShowFeeds> feeds = new MutableLiveData<>();
        final MutableLiveData<List<TvShow>> data = new MutableLiveData<>();

        ApiMovies api = ApiClient.getClient().create(ApiMovies.class);
        Call<TvShowFeeds> call = api.loadTVShowList(API_V3_KEY, language);
        call.enqueue(new Callback<TvShowFeeds>() {
            @Override
            public void onResponse(Call<TvShowFeeds> call, Response<TvShowFeeds> response) {
                if (response.code()==200){
                    feeds.setValue(response.body());
                    Log.e(TAG, "onResponse: "+response.body().toString() );
                    data.setValue(feeds.getValue().getTvShows());
                }else{
                    Log.e(TAG, "onResponse: "+response.errorBody().toString());
                    Log.e(TAG, "onResponse: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<TvShowFeeds> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage());
            }
        });
        return data;
    }
}
