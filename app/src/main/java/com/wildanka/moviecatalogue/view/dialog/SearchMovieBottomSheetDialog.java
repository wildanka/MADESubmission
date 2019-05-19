package com.wildanka.moviecatalogue.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wildanka.moviecatalogue.R;
import com.wildanka.moviecatalogue.model.entity.Movie;
import com.wildanka.moviecatalogue.util.SharedPref;
import com.wildanka.moviecatalogue.view.adapter.MovieRVAdapter;
import com.wildanka.moviecatalogue.viewmodel.SearchDialogViewModel;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchMovieBottomSheetDialog extends BottomSheetDialogFragment {
    private static final String TAG = "SearchMovieBottomSheetD";
    private MovieRVAdapter adapter;
    private BottomSheetBehavior mBehavior;
    private SearchDialogViewModel viewModel;
    private ProgressBar loadingBar;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPref sp = new SharedPref(getActivity());
        final String language = sp.loadLanguage();
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.dialog_search_movie, null);
        RecyclerView recyclerView = view.findViewById(R.id.rv_search_movie);
        ImageView ivSearch = view.findViewById(R.id.iv_search_movie);
        loadingBar = view.findViewById(R.id.pb_dialog_search_movie);
        loadingBar.setVisibility(View.INVISIBLE);
        final EditText etSearchMovie = view.findViewById(R.id.et_search_movie);

        adapter = new MovieRVAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        //do anything here
        viewModel = ViewModelProviders.of(this).get(SearchDialogViewModel.class);

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar.setVisibility(View.VISIBLE);
                viewModel.getSearchMovieList(language, etSearchMovie.getText().toString()).observe(SearchMovieBottomSheetDialog.this, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(List<Movie> movies) {
                        loadingBar.setVisibility(View.INVISIBLE);
                        if (movies != null) {
                            Log.e(TAG, "onChanged: "+movies.get(0).getTitle());
                            adapter.setListMovie(movies);
                        }
                    }
                });

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
