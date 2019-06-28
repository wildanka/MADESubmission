package com.wildanka.moviecatalogue.model.entity;

import android.content.ContentValues;
import android.provider.BaseColumns;
import android.view.Menu;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Movie.TABLE_NAME)
public class Movie {
    public static final String TABLE_NAME = "movies";
    public static final String COLUMN_ID = "idMovie";
    public static final String COLUMN_NAME = "title";

    public static Movie fromContentValues(ContentValues contentValues) {
        final Movie movie = new Movie();
        if (contentValues.containsKey(COLUMN_ID)) {
            movie.idMovie = contentValues.getAsString(COLUMN_ID);
        }
        if (contentValues.containsKey(COLUMN_NAME)) {
            movie.title = contentValues.getAsString(COLUMN_NAME);
        }
        return movie;
    }

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    public String idMovie;
    @SerializedName("vote_count")
    public String voteCount;
    @SerializedName("title")
    public String title;
    @SerializedName("release_date")
    public String dateYear;
    @SerializedName("vote_average")
    public String rating;

    @SerializedName("overview")
    public String overview;
    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("original_language")
    public String originalLanguage;

    @SerializedName("popularity")
    public String popularity;

    @SerializedName("adult")
    public boolean adult;

    public Movie() {
    }

/*
    public Movie(String idMovie, String voteCount, String title, String dateYear, String rating, String overview, String posterPath, String originalLanguage, String popularity, boolean adult) {
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
    }
*/

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


    public Movie setIdMovie(@NonNull String idMovie) {
        this.idMovie = idMovie;
        return this;
    }

    public Movie setVoteCount(String voteCount) {
        this.voteCount = voteCount;
        return this;
    }

    public Movie setTitle(String title) {
        this.title = title;
        return this;
    }

    public Movie setDateYear(String dateYear) {
        this.dateYear = dateYear;
        return this;
    }

    public Movie setRating(String rating) {
        this.rating = rating;
        return this;
    }

    public Movie setOverview(String overview) {
        this.overview = overview;
        return this;
    }

    public Movie setPosterPath(String posterPath) {
        this.posterPath = posterPath;
        return this;
    }

    public Movie setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
        return this;
    }

    public Movie setPopularity(String popularity) {
        this.popularity = popularity;
        return this;
    }

    public Movie setAdult(boolean adult) {
        this.adult = adult;
        return this;
    }


}
