package com.example.popularmovies.data;

import android.provider.BaseColumns;

/**
 * Created by eb2894 on 2016-02-16.
 */
public class DbContract {
    public static final class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "releas_date";
        //public static final String COLUMN_POSTER_IMAGE = "poster_image";

    }
}

