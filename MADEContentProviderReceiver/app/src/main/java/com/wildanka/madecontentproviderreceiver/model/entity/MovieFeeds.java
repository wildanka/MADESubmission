package com.wildanka.madecontentproviderreceiver.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieFeeds {
    @SerializedName("results")
    List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }
}
