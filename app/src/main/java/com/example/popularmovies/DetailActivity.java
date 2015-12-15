package com.example.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent sourceIntent = getIntent();
        if(sourceIntent != null) {
            int movieId = sourceIntent.getIntExtra("movieId", 0);
            if(movieId != 0) {
                Toast.makeText(this, String.format("movie id: %d ", movieId), Toast.LENGTH_SHORT).show();
            }

        }
    }
}
