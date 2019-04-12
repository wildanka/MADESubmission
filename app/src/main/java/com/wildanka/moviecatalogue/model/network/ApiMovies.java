package com.wildanka.moviecatalogue.model.network;

import com.wildanka.moviecatalogue.model.entity.Movie;
import com.wildanka.moviecatalogue.model.entity.MovieFeeds;

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

    @GET("discover/tv?api_key={API_KEY}&language={language}")
    Call<MovieFeeds> loadTVShowList(
            @Path("API_KEY") String apiKey,
            @Path("language") String language //en-US
    );


    @GET("t/p/{POSTER_SIZE}/POSTER_FILENAME")
    Call<Movie> loadMovieImages(
            @Path("POSTER_SIZE") String posterSize,
            @Path("POSTER_FILENAME") String posterFilename
    );

}