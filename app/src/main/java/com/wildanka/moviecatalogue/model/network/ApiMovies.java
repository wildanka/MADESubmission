package com.wildanka.moviecatalogue.model.network;

import com.wildanka.moviecatalogue.model.entity.Movie;
import com.wildanka.moviecatalogue.model.entity.MovieFeeds;
import com.wildanka.moviecatalogue.model.entity.ReleaseTodayData;
import com.wildanka.moviecatalogue.model.entity.TvShowFeeds;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiMovies {
    @GET("discover/movie")
    Call<MovieFeeds> loadMovieList(
            @Query("api_key") String apiKey,
            @Query("language") String language //en-US
    );

    @GET("discover/tv")
    Call<TvShowFeeds> loadTVShowList(
            @Query("api_key") String apiKey,
            @Query("language") String language //en-US
    );


    @GET("t/p/{POSTER_SIZE}/POSTER_FILENAME")
    Call<Movie> loadMovieImages(
            @Path("POSTER_SIZE") String posterSize,
            @Path("POSTER_FILENAME") String posterFilename
    );


    @GET("search/movie")
    Call<MovieFeeds> searchMovie(
            @Query("api_key") String apiKey,
            @Query("language") String language, //en-US
            @Query("query") String searchQuery
    );

    @GET("search/tv")
    Call<TvShowFeeds> searchTVShow(
            @Query("api_key") String apiKey,
            @Query("language") String language, //en-US
            @Query("query") String searchQuery
    );

    @GET("https://api.themoviedb.org/3/discover/movie")
    Call<ReleaseTodayData> getTodayRelease(
            @Query("api_key") String apiKey,
            @Query("primary_release_date.gte") String releaseDateGTE, //2019-07-03
            @Query("primary_release_date.lte") String releaseDateLTE, //2019-07-03
            @Query("language") String language //en-US
    );
}