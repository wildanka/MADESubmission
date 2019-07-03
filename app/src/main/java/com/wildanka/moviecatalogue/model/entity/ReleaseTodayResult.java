package com.wildanka.moviecatalogue.model.entity;

import com.google.gson.annotations.SerializedName;

public class ReleaseTodayResult {
    @SerializedName("vote_count")
    private Integer voteCount;
    @SerializedName("vote_count")
    private Integer id;
    @SerializedName("vote_average")
    private Integer voteAverage;
    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("release_date")
    private String releaseDate;

    public Integer getVoteCount() {
        return voteCount;
    }

    public Integer getId() {
        return id;
    }

    public Integer getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
