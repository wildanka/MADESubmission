package com.wildanka.moviecatalogue.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wildanka.moviecatalogue.R;
import com.wildanka.moviecatalogue.model.entity.Movie;
import com.wildanka.moviecatalogue.model.entity.TvShow;
import com.wildanka.moviecatalogue.util.SharedPref;
import com.wildanka.moviecatalogue.view.adapter.MovieRVAdapter;
import com.wildanka.moviecatalogue.view.adapter.TvShowRVAdapter;
import com.wildanka.moviecatalogue.viewmodel.SearchDialogViewModel;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchMovieBottomSheetDialog extends BottomSheetDialogFragment {
    private static final String TAG = "SearchMovieBottomSheetD";
    private MovieRVAdapter MovieRVAdapter;
    private TvShowRVAdapter tvShowRVAdapter;
    private BottomSheetBehavior mBehavior;
    private SearchDialogViewModel viewModel;
    private ProgressBar loadingBar;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPref sp = new SharedPref(getActivity());
        final String language = sp.loadLanguage();
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.dialog_search_movie, null);
        final RecyclerView recyclerView = view.findViewById(R.id.rv_search_movie);
        ImageView ivSearch = view.findViewById(R.id.iv_search_movie);
        final RadioGroup rgMovieTV = view.findViewById(R.id.radioGroup);
        final EditText etSearchMovie = view.findViewById(R.id.et_search_movie);
        loadingBar = view.findViewById(R.id.pb_dialog_search_movie);
        loadingBar.setVisibility(View.INVISIBLE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);


        //do anything here
        viewModel = ViewModelProviders.of(this).get(SearchDialogViewModel.class);

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar.setVisibility(View.VISIBLE);
                if (rgMovieTV.getCheckedRadioButtonId() == R.id.rb_movies){ // Search Movie
                    MovieRVAdapter = new MovieRVAdapter(getActivity());
                    recyclerView.setAdapter(MovieRVAdapter);
                    viewModel.getSearchMovieList(language, etSearchMovie.getText().toString()).observe(SearchMovieBottomSheetDialog.this, new Observer<List<Movie>>() {
                        @Override
                        public void onChanged(List<Movie> movies) {
                            loadingBar.setVisibility(View.INVISIBLE);
                            if (movies != null) {
                                Log.e(TAG, "onChanged: "+movies.get(0).getTitle());
                                MovieRVAdapter.setListMovie(movies);
                            }
                        }
                    });
                }else{ // Search TV Show
                    tvShowRVAdapter = new TvShowRVAdapter(getActivity());
                    recyclerView.setAdapter(tvShowRVAdapter);
                    Log.e(TAG, "onClick: "+etSearchMovie.getText().toString() );
                    viewModel.getSearchTVShowsList(language, etSearchMovie.getText().toString()).observe(SearchMovieBottomSheetDialog.this, new Observer<List<TvShow>>() {
                        @Override
                        public void onChanged(List<TvShow> tvShows) {
                            loadingBar.setVisibility(View.INVISIBLE);
                            if (tvShows != null) {
                                Log.e(TAG, "onChanged: "+tvShows.get(0).getTitle());
                                tvShowRVAdapter.setListMovie(tvShows);
                            }
                        }
                    });
                }
            }
        });
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        return dialog;
    }



    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

}
