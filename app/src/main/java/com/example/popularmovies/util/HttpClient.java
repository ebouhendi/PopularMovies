package com.example.popularmovies.util;

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by eb2894 on 2015-12-15.
 */
public class HttpClient {

    private static final String LOG_TAG = HttpClient.class.getSimpleName();

    @Nullable
    public String performHttpGet(URL url) {
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
            Log.e(LOG_TAG, "Error " + e.getMessage());
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
}

