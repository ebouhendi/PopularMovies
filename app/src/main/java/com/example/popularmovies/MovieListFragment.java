package com.example.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.popularmovies.entities.MoviePoster;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieListFragment extends Fragment {

    private static final String LOG_TAG = MovieListFragment.class.getSimpleName();
    private PosterListAdapter adapter;
    private String orderString;

    public MovieListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ArrayList<MoviePoster> list = new ArrayList<MoviePoster>(0);
        GridView grid = (GridView)rootView.findViewById(R.id.grid_posters);
        adapter = new PosterListAdapter(getContext(), R.layout.grid_item_poster, list);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(getContext(), DetailActivity.class);
                detailIntent.putExtra("movieId", id);
                startActivity(detailIntent);
            }
        });

        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("Order", orderString);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            orderString = savedInstanceState.getString("Order", "popularity.desc") ;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(LOG_TAG, String.format("Option with itemId %s selected",item.getItemId()));

        if(item.getItemId() == R.id.action_orderby_rank) {
            orderString = "popularity.desc";
            refreshList();
        }

        if(item.getItemId() == R.id.action_orderby_date) {
            orderString= "release_date.desc";
            refreshList();
        }

        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movie_list, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onStart() {
        super.onStart();
        refreshList();
    }

    private void refreshList() {
        adapter.clear();
        new MovieListTask(adapter).execute(orderString);
    }

    public class PosterListAdapter extends ArrayAdapter<MoviePoster> {


        public PosterListAdapter(Context context, int resource, List<MoviePoster> list) {
            super(context, resource, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.grid_item_poster, null);
            }

            ImageView imgView = (ImageView)v;
            Picasso.with(getContext()).load(Uri.parse("http://image.tmdb.org/t/p/w185/"
                    + getItem(position).getPosterPath())).into(imgView);
            return v;
        }

        @Override
        public long getItemId(int position) {
            return (long)getItem(position).getMovieId();
        }
    }
}
