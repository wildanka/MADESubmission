package com.wildanka.moviecatalogue.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wildanka.moviecatalogue.R;
import com.wildanka.moviecatalogue.TVShowDetailActivity;
import com.wildanka.moviecatalogue.model.entity.TvShow;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class TvShowRVAdapter extends RecyclerView.Adapter<TvShowRVAdapter.MovieRVViewHolder> {
    private static final String TAG = "MovieRVAdapter";
    private Context mContext;
    private List<TvShow> listMovie;

    public void setListMovie(List<TvShow> listMovie) {
        this.listMovie = listMovie;
        notifyDataSetChanged();
    }

    //inject the context here from the constructor
    public TvShowRVAdapter(Context mContext) {
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
        final TvShow selectedMovie = listMovie.get(i);
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

        void bind(final TvShow tvShow) {
            if (Double.parseDouble(tvShow.getRating()) < 6.0){
                tvRating.setTextColor(ContextCompat.getColor(mContext,R.color.colorRed));
            }else if(Double.parseDouble(tvShow.getRating()) < 7.0){
                tvRating.setTextColor(ContextCompat.getColor(mContext,R.color.colorYellow));
            }else{
                tvRating.setTextColor(ContextCompat.getColor(mContext,R.color.colorGreen));
            }

            tvRating.setText(mContext.getString(R.string.rating_in_percent,tvShow.getRating()));
            tvMovieTitle.setText(tvShow.getTitle());
            tvShortDesc.setText(tvShow.getOverview());
            tvReleaseDate.setText(tvShow.getDateYear());
            final String MOVIE_POSTER_URI = "https://image.tmdb.org/t/p/w185/"+tvShow.getPosterPath();
            Picasso.get().load(MOVIE_POSTER_URI).into(ivMoviePoster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailIntent = new Intent(mContext, TVShowDetailActivity.class);

                    detailIntent.putExtra("movieId", tvShow.getIdTVShow());
                    detailIntent.putExtra("movieTitle", tvShow.getTitle());
                    detailIntent.putExtra("moviePosterUri", MOVIE_POSTER_URI);
                    detailIntent.putExtra("movieOverview", tvShow.getOverview());
                    detailIntent.putExtra("movieRating", tvShow.getRating());
                    detailIntent.putExtra("movieReleaseDate", tvShow.getDateYear());
                    Toast.makeText(mContext, tvShow.getTitle(), Toast.LENGTH_SHORT).show();
                    mContext.startActivity(detailIntent);
                }
            });
        }
    }
}
