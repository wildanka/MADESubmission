package com.wildanka.moviecatalogue.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowFeeds {
    @SerializedName("results")
    List<TvShow> tvShows;

    public List<TvShow> getTvShows() {
        return tvShows;
    }

    @Override
    public String toString() {
        return "TvShowFeeds{" +
                "tvShows=" + tvShows +
                '}';
    }
}
