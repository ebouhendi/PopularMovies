package com.example.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private PosterListAdapter adapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final String LOG_TAG = MainActivityFragment.class.getSimpleName();

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ArrayList<String> list = new ArrayList<String>(5);
        list.add("Test");
        list.add("Test");
        list.add("Test");
        GridView grid = (GridView)rootView.findViewById(R.id.grid_posters);

        adapter = new PosterListAdapter(getContext(), R.layout.grid_item_poster, list);
        grid.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        new MovieListTask(adapter).execute();
    }

    public class PosterListAdapter extends ArrayAdapter<String> {


        private final String LOG_TAG = PosterListAdapter.class.getSimpleName();

        public PosterListAdapter(Context context, int resource) {
            super(context, resource);
        }

        public PosterListAdapter(Context context, int resource, List<String> list) {
            super(context, resource, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.grid_item_poster, null);
                ImageView imgView = (ImageView)v;

                Picasso.with(getContext()).load(Uri.parse("http://image.tmdb.org/t/p/w185/" + getItem(position))).into(imgView);
            }

            return v;
        }
    }
}
