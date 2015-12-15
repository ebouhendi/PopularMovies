package com.example.popularmovies.tmdb;

/**
 * Created by eb2894 on 2015-12-15.
 */
public class MovieDetail {
    private String title;
    private String releaseDate;
    private String posterPath;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setPosterPath(String postrPath) {
        this.posterPath = postrPath;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
