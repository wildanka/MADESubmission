package com.wildanka.madecontentproviderreceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wildanka.madecontentproviderreceiver.model.entity.Movie;

public class MainActivity extends AppCompatActivity {
    private static final int LOADER_MOVIES = 1;

    private MovieAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SwipeRefreshLayout srlMain = findViewById(R.id.srl_main);

        final RecyclerView list = findViewById(R.id.rv_movie_list);
        list.setLayoutManager(new LinearLayoutManager(list.getContext()));
        moviesAdapter = new MovieAdapter();
        list.setAdapter(moviesAdapter);

        getSupportLoaderManager().initLoader(LOADER_MOVIES, null, mLoaderCallbacks);

        srlMain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                list.setAdapter(moviesAdapter);
                getSupportLoaderManager().initLoader(LOADER_MOVIES, null, mLoaderCallbacks);
                srlMain.setRefreshing(false);
            }
        });
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<Cursor>() {

                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    switch (id) {
                        case LOADER_MOVIES:
                            return new CursorLoader(getApplicationContext(),
                                    SampleContentProvider.URI_MOVIE,
                                    new String[]{Movie.COLUMN_NAME},
                                    null, null, null);
                        default:
                            throw new IllegalArgumentException();
                    }
                }

                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                    switch (loader.getId()) {
                        case LOADER_MOVIES:
                            moviesAdapter.setMovies(data);
                            break;
                    }
                }

                @Override
                public void onLoaderReset(Loader<Cursor> loader) {
                    switch (loader.getId()) {
                        case LOADER_MOVIES:
                            moviesAdapter.setMovies(null);
                            break;
                    }
                }
            };

    static class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

        private Cursor mCursor;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (mCursor.moveToPosition(position)) {
                holder.mText.setText(mCursor.getString(
                        mCursor.getColumnIndexOrThrow(Movie.COLUMN_NAME)));
            }
        }

        @Override
        public int getItemCount() {
            return mCursor == null ? 0 : mCursor.getCount();
        }

        void setMovies(Cursor cursor) {
            mCursor = cursor;
            notifyDataSetChanged();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            TextView mText;

            ViewHolder(ViewGroup parent) {
                super(LayoutInflater.from(parent.getContext()).inflate(
                        android.R.layout.simple_list_item_1, parent, false));
                mText = itemView.findViewById(android.R.id.text1);
            }
        }
    }
}
