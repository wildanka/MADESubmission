package com.wildanka.moviecatalogue.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wildanka.moviecatalogue.R;
import com.wildanka.moviecatalogue.model.entity.Movie;

import java.util.ArrayList;

public class MovieRVAdapter extends RecyclerView.Adapter<MovieRVAdapter.MovieRVViewHolder> {
    private Context mContext;
    private ArrayList<Movie> listMovie;

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
    }

    //inject the context here from the constructor
    public MovieRVAdapter(Context mContext) {
        this.mContext = mContext;
        this.listMovie = new ArrayList<>();
    }

    @NonNull
    @Override
    public MovieRVViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        //inflate the custom Layout
        View itemView = inflater.inflate(R.layout.item_movie,viewGroup, false);
        return new MovieRVViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieRVViewHolder holder, int i) {
        final Movie selectedMovie = listMovie.get(i);
        holder.bind(selectedMovie);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent detailIntent = new Intent(mContext, MovieDetailActivity.class);
//                detailIntent.putExtra("selectedMovie", selectedMovie);
                System.out.println(selectedMovie.getTitle());
                Toast.makeText(mContext, selectedMovie.getTitle(), Toast.LENGTH_SHORT).show();
//                mContext.startActivity(detailIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (listMovie==null){
            return 0;
        }else {
            return listMovie.size();
        }
//        return (listMovie.isEmpty()) ? 0 : listMovie.size();
    }

    class MovieRVViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieTitle, tvShortDesc, tvRating, tvReleaseDate;
        ImageView ivMoviePoster;
        public MovieRVViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTitle = (TextView) itemView.findViewById(R.id.tv_item_title);
            tvShortDesc = (TextView) itemView.findViewById(R.id.tv_item_short_desc);
            tvRating = (TextView) itemView.findViewById(R.id.tv_item_rating);
            tvReleaseDate = (TextView) itemView.findViewById(R.id.tv_item_release_date);
            ivMoviePoster = (ImageView) itemView.findViewById(R.id.iv_item_movie_poster);
        }
        void bind(Movie movie){
            if (Integer.parseInt(movie.getRating()) < 60){
                tvRating.setTextColor(ContextCompat.getColor(mContext,R.color.colorRed));
            }else if(Integer.parseInt(movie.getRating()) < 70){
                tvRating.setTextColor(ContextCompat.getColor(mContext,R.color.colorYellow));
            }else{
                tvRating.setTextColor(ContextCompat.getColor(mContext,R.color.colorGreen));
            }

            tvRating.setText(mContext.getString(R.string.rating_in_percent,movie.getRating()));
            tvMovieTitle.setText(movie.getTitle());
            tvShortDesc.setText(movie.getOverview());
            tvReleaseDate.setText(movie.getDateYear());
            ivMoviePoster.setImageDrawable(mContext.getDrawable(movie.getIvPoster()));
        }
    }
}
