package com.wildanka.moviecatalogue.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wildanka.moviecatalogue.R;
import com.wildanka.moviecatalogue.model.entity.Movie;
import com.wildanka.moviecatalogue.util.SharedPref;
import com.wildanka.moviecatalogue.viewmodel.SearchDialogViewModel;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class SearchMovieBottomSheetDialog extends BottomSheetDialogFragment {
    private static final String TAG = "SearchMovieBottomSheetD";
    private BottomSheetBehavior mBehavior;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPref sp = new SharedPref(getActivity());
        String language = sp.loadLanguage();
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.dialog_search_movie, null);
        //do anything here
        SearchDialogViewModel viewModel = ViewModelProviders.of(this).get(SearchDialogViewModel.class);

        viewModel.getSearchMovieList(language, "avengers").observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (movies != null) {
                    Log.e(TAG, "onChanged: "+movies.get(0).getTitle());
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
