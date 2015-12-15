package com.example.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.example.popularmovies.com.example.popularmovies.tmdb.MoviePoster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by eb2894 on 2015-12-14.
 */
public class MovieListTask extends AsyncTask<Void, Void, List<MoviePoster>> {

    private static final String LOG_TAG = MovieListTask.class.getSimpleName();
    private MainActivityFragment.PosterListAdapter adapter;

    public MovieListTask(MainActivityFragment.PosterListAdapter adapter) {
        this.adapter = adapter;
    }


    @Override
    protected List<MoviePoster> doInBackground(Void... params) {

        final String FORECAST_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
        final String PAGE_PARAM = "page";
        final String APPID_PARAM = "api_key";

        Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(PAGE_PARAM, Integer.toString(1))
                .appendQueryParameter(APPID_PARAM, "9ba004b4995bf150034752ff19f040d7")
                .build();

        try {
            URL url = new URL(builtUri.toString());
            String result = performHttpGet(url);

            if (result == null)
                return null;

            return parseJsonList(result);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error in URL:", e);
        }
        return null;
    }

    @Nullable
    private String performHttpGet(URL url) {
        String result = "";
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n"); // To make debugging easier
            }

            result = buffer.toString();

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return result;
    }

    private List<MoviePoster> parseJsonList(String jsonStr) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonStr);

            JSONArray moviesArray = jsonObject.getJSONArray("results");
            List<MoviePoster> result = new ArrayList<>(moviesArray.length());
            for (int i = 0; i< moviesArray.length(); i++) {
                JSONObject movie = moviesArray.getJSONObject(i);
                MoviePoster poster = new MoviePoster(movie.getInt("id"), movie.getString("poster_path"));
                result.add(poster);
            }

            return result;
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error in JSON parsing", e);
        }

        return new ArrayList<>(0);
    }

    @Override
    protected void onPostExecute(List<MoviePoster> result) {
        super.onPostExecute(result);
        adapter.addAll(result);
    }
}
