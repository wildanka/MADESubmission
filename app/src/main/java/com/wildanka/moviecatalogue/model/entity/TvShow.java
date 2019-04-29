package com.wildanka.moviecatalogue.model.entity;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tv_shows")
public class TvShow {
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private String idTVShow;
    @SerializedName("vote_count")
    private String voteCount;
    @SerializedName("original_name")
    private String originalName;
    @SerializedName("name")
    private String title;

    @SerializedName("first_air_date")
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

    public TvShow(String idMovie, String voteCount, String originalName, String title, String dateYear, String rating, String overview, String posterPath, String originalLanguage, String popularity) {
        this.idTVShow = idMovie;
        this.voteCount = voteCount;
        this.originalName = originalName;
        this.title = title;
        this.dateYear = dateYear;
        this.rating = rating;
        this.overview = overview;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.popularity = popularity;
    }


    public String getIdTVShow() {
        return idTVShow;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public String getOriginalName() {
        return originalName;
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

    public void setIdTVShow(@NonNull String idTVShow) {
        this.idTVShow = idTVShow;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDateYear(String dateYear) {
        this.dateYear = dateYear;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    @Override
    public String toString() {
        return "TvShow{" +
                "idTVShow='" + idTVShow + '\'' +
                ", voteCount='" + voteCount + '\'' +
                ", originalName='" + originalName + '\'' +
                ", title='" + title + '\'' +
                ", dateYear='" + dateYear + '\'' +
                ", rating='" + rating + '\'' +
                ", overview='" + overview + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", popularity='" + popularity + '\'' +
                '}';
    }
}
