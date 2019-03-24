package com.wildanka.moviecatalogue.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wildanka.moviecatalogue.R;
import com.wildanka.moviecatalogue.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Movie> listMovie;

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
    }

    //inject the context here from the constructor
    public MovieAdapter(Context mContext) {
        this.mContext = mContext;
        this.listMovie = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return listMovie.size();
    }

    @Override
    public Object getItem(int position) {
        return listMovie.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(convertView);
        Movie movie = (Movie) getItem(position);
        viewHolder.bind(movie);
        return convertView;
    }

    private class ViewHolder {
        private TextView tvTitle;
        private TextView tvRating;
        private TextView tvShortDesc;
        private ImageView ivMoviePoster;
        ViewHolder(View view) {
            tvTitle = view.findViewById(R.id.tv_title);
            tvRating = view.findViewById(R.id.tv_rating);
            tvShortDesc = view.findViewById(R.id.tv_short_desc);
            ivMoviePoster = view.findViewById(R.id.iv_movie_poster);
        }
        void bind(Movie movie) {
            tvRating.setText(movie.getRating());
            tvTitle.setText(movie.getTitle());
            tvShortDesc.setText(movie.getShortDescription());
            ivMoviePoster.setImageResource(movie.getIvPoster());
        }
    }
}
