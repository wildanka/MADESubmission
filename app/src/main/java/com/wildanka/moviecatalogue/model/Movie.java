package com.wildanka.moviecatalogue.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String title;
    private String dateYear;
    private String rating;
    private String shortDescription;
    private String overview;
    private int ivPoster;

    public Movie(String title, String dateYear, String rating, String shortDescription, String overview, int ivPoster) {
        this.title = title;
        this.dateYear = dateYear;
        this.rating = rating;
        this.shortDescription = shortDescription;
        this.overview = overview;
        this.ivPoster = ivPoster;
    }

    public void setIvPoster(int ivPoster) {
        this.ivPoster = ivPoster;
    }

    public int getIvPoster() {
        return ivPoster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateYear() {
        return dateYear;
    }

    public void setDateYear(String dateYear) {
        this.dateYear = dateYear;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.dateYear);
        dest.writeString(this.rating);
        dest.writeString(this.shortDescription);
        dest.writeString(this.overview);
        dest.writeInt(this.ivPoster);
    }

    protected Movie(Parcel in) {
        this.title = in.readString();
        this.dateYear = in.readString();
        this.rating = in.readString();
        this.shortDescription = in.readString();
        this.overview = in.readString();
        this.ivPoster = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
