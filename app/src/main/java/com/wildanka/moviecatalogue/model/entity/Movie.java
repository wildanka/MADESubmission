package com.wildanka.moviecatalogue.model.entity;

import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("id")
    private String idMovie;
    @SerializedName("vote_count")
    private String voteCount;
    @SerializedName("title")
    private String title;
    @SerializedName("release_date")
    private String dateYear;
    @SerializedName("vote_average")
    private String rating;

    @SerializedName("overview")
    private String overview;
    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("popularity")
    private String popularity;

    @SerializedName("adult")
    private boolean adult;
    private int ivPoster;

    public Movie(String idMovie, String voteCount, String title, String dateYear, String rating, String overview, String posterPath, String originalLanguage, String popularity, boolean adult, int ivPoster) {
        this.idMovie = idMovie;
        this.voteCount = voteCount;
        this.title = title;
        this.dateYear = dateYear;
        this.rating = rating;
        this.overview = overview;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.popularity = popularity;
        this.adult = adult;
        this.ivPoster = ivPoster;
    }

    public String getIdMovie() {
        return idMovie;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public String getTitle() {
        return title;
    }

    public String getDateYear() {
        return dateYear;
    }

    public String getRating() {
        return rating;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getPopularity() {
        return popularity;
    }

    public boolean isAdult() {
        return adult;
    }

    public int getIvPoster() {
        return ivPoster;
    }
}
