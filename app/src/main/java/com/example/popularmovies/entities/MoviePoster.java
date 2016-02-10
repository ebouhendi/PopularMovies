package com.example.popularmovies.entities;

/**
 * Created by eb2894 on 2015-12-14.
 */
public class MoviePoster {
    private int movieId;
    private String posterPath;

    public MoviePoster(int movieId, String posterPath) {
        this.movieId = movieId;
        this.posterPath = posterPath;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
