package com.wildanka.moviecatalogue.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;

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
    private RadioGroup rgMovieTV;
    private RadioButton rbMovie;
    private RadioButton rbTVShow;
    private RecyclerView recyclerView;
    private String language ;
    private SearchView etSearchMovie;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPref sp = new SharedPref(getActivity());
        language = sp.loadLanguage();
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.dialog_search_movie, null);
        etSearchMovie = view.findViewById(R.id.et_search_movie);
        etSearchMovie.setIconifiedByDefault(false);
        loadingBar = view.findViewById(R.id.pb_dialog_search_movie);
        loadingBar.setVisibility(View.INVISIBLE);

        final TextView tvBottomSheetTitle = view.findViewById(R.id.tv_bottom_sheet_title);

        rgMovieTV = view.findViewById(R.id.radioGroup);
        recyclerView = view.findViewById(R.id.rv_search_movie);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);


        //do anything here
        viewModel = ViewModelProviders.of(this).get(SearchDialogViewModel.class);

        rgMovieTV.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_movies:
                        tvBottomSheetTitle.setText("Search Movie");
                        break;
                    case R.id.rb_tv_shows:
                        tvBottomSheetTitle.setText("Search TV Show");
                        break;
                }
            }
        });
        etSearchMovie.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearchDate();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callSearchDate();
                return true;
            }
        });
        etSearchMovie.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        return dialog;
    }

    private void callSearchDate(){
        loadingBar.setVisibility(View.VISIBLE);
        if (rgMovieTV.getCheckedRadioButtonId() == R.id.rb_movies){ // Search Movie
            MovieRVAdapter = new MovieRVAdapter(getActivity());
            recyclerView.setAdapter(MovieRVAdapter);
            viewModel.getSearchMovieList(language, etSearchMovie.getQuery().toString()).observe(SearchMovieBottomSheetDialog.this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(List<Movie> movies) {
                    loadingBar.setVisibility(View.INVISIBLE);
                    if (movies != null) {
                        MovieRVAdapter.setListMovie(movies);
                    }
                }
            });
        }else{ // Search TV Show
            tvShowRVAdapter = new TvShowRVAdapter(getActivity());
            recyclerView.setAdapter(tvShowRVAdapter);
            viewModel.getSearchTVShowsList(language, etSearchMovie.getQuery().toString()).observe(SearchMovieBottomSheetDialog.this, new Observer<List<TvShow>>() {
                @Override
                public void onChanged(List<TvShow> tvShows) {
                    loadingBar.setVisibility(View.INVISIBLE);
                    if (tvShows != null) {
                        tvShowRVAdapter.setListMovie(tvShows);
                    }
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

}
