package com.wildanka.moviecatalogue.view.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wildanka.moviecatalogue.R;
import com.wildanka.moviecatalogue.model.entity.Movie;
import com.wildanka.moviecatalogue.MovieDetailActivity;

import java.util.List;

public class MovieRVAdapter extends RecyclerView.Adapter<MovieRVAdapter.MovieRVViewHolder> {
    private static final String TAG = "MovieRVAdapter";
    private Context mContext;
    private List<Movie> listMovie;

    public void setListMovie(List<Movie> listMovie) {
        this.listMovie = listMovie;
        notifyDataSetChanged();
    }

    //inject the context here from the constructor
    public MovieRVAdapter(Context mContext) {
        this.mContext = mContext;
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
    }

    @Override
    public int getItemCount() {
        if (listMovie==null){
            return 0;
        }else {
            return listMovie.size();
        }
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
        void bind(final Movie movie){
            if (Double.parseDouble(movie.getRating()) < 6.0){
                tvRating.setTextColor(ContextCompat.getColor(mContext,R.color.colorRed));
            }else if(Double.parseDouble(movie.getRating()) < 7.0){
                tvRating.setTextColor(ContextCompat.getColor(mContext,R.color.colorYellow));
            }else{
                tvRating.setTextColor(ContextCompat.getColor(mContext,R.color.colorGreen));
            }

            tvRating.setText(mContext.getString(R.string.rating_in_percent,movie.getRating()));
            tvMovieTitle.setText(movie.getTitle());
            tvShortDesc.setText(movie.getOverview());
            tvReleaseDate.setText(movie.getDateYear());
            final String MOVIE_POSTER_URI = "https://image.tmdb.org/t/p/w185"+movie.getPosterPath();
            Picasso.get().load(MOVIE_POSTER_URI).into(ivMoviePoster);
            Log.e(TAG, "bind: https://image.tmdb.org/t/p/w185/"+movie.getPosterPath());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailIntent = new Intent(mContext, MovieDetailActivity.class);

                    detailIntent.putExtra("movieId", movie.getIdMovie());
                    detailIntent.putExtra("movieVoteCount", movie.getVoteCount());
                    detailIntent.putExtra("movieTitle", movie.getTitle());
                    detailIntent.putExtra("movieReleaseDate", movie.getDateYear());
                    detailIntent.putExtra("movieRating", movie.getRating());
                    detailIntent.putExtra("movieOverview", movie.getOverview());
                    detailIntent.putExtra("moviePosterUri", MOVIE_POSTER_URI);
                    detailIntent.putExtra("movieOriginalLanguage", movie.getOriginalLanguage());
                    detailIntent.putExtra("moviePopularity", movie.getPopularity());
                    detailIntent.putExtra("movieIsAdult", movie.isAdult());
                    Toast.makeText(mContext, movie.getTitle(), Toast.LENGTH_SHORT).show();
                mContext.startActivity(detailIntent);
                }
            });
        }
    }
}
