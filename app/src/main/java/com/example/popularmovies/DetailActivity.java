package com.example.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.tmdb.MovieDetail;
import com.example.popularmovies.util.HttpClient;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    private long movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent sourceIntent = getIntent();
        if(sourceIntent != null) {
            movieId = sourceIntent.getLongExtra("movieId", 0);
        }

        if(movieId != 0) {
            new MovieDetailTask().execute(String.format("%d",movieId));
        }
    }

    protected void showMovieDetail(MovieDetail movieDetail) {
        if(movieDetail == null) {
            return;
        }

        ImageView posterImage = (ImageView)findViewById(R.id.detail_image_poster);
        TextView releaseDateTextView = (TextView)findViewById(R.id.detail_text_release_date);
        TextView titleTextView = (TextView)findViewById(R.id.detail_text_title);

        releaseDateTextView.setText(movieDetail.getReleaseYear());
        titleTextView.setText(movieDetail.getTitle());
        Picasso.with(this).load(Uri.parse("http://image.tmdb.org/t/p/w185/"
                + movieDetail.getPosterPath())).into(posterImage);
    }

    class MovieDetailTask extends AsyncTask<String, Void, MovieDetail> {
        private final String LOG_TAG = MovieListTask.class.getSimpleName();

        @Override
        protected MovieDetail doInBackground(String... params) {
            final String FORECAST_BASE_URL = "http://api.themoviedb.org/3/movie/";
            final String APPID_PARAM = "api_key";

            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendPath(params[0])
                    .appendQueryParameter(APPID_PARAM, "9ba004b4995bf150034752ff19f040d7")
                    .build();

            try {
                URL url = new URL(builtUri.toString());
                HttpClient httpClient = new HttpClient();
                String result = httpClient.performHttpGet(url);

                if (result == null)
                    return null;

                return parseJsonList(result);
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Error in URL:", e);
            }
            return null;
        }

        private MovieDetail parseJsonList(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                MovieDetail movie = new MovieDetail();
                movie.setPosterPath(jsonObject.getString("poster_path"));
                movie.setReleaseYear(jsonObject.getString("release_date").substring(0,4));
                movie.setTitle(jsonObject.getString("original_title"));
                return movie;
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(LOG_TAG,"JSON parsing error", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(MovieDetail movieDetail) {
            super.onPostExecute(movieDetail);
            showMovieDetail(movieDetail);
        }
    }
}

