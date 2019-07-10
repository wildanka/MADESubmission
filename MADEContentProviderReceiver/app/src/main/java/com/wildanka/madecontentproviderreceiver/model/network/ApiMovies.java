package com.wildanka.madecontentproviderreceiver.model.network;


import com.wildanka.madecontentproviderreceiver.model.entity.Movie;
import com.wildanka.madecontentproviderreceiver.model.entity.MovieFeeds;
import com.wildanka.madecontentproviderreceiver.model.entity.TvShowFeeds;

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


}