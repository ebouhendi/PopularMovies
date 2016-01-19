package com.example.popularmovies.tmdb;

/**
 * Created by eb2894 on 2015-12-15.
 */
public class MovieDetail {
    private String title;
    private String releaseYear;
    private String posterPath;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setPosterPath(String postrPath) {
        this.posterPath = postrPath;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
