package com.wildanka.moviecatalogue.view.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wildanka.moviecatalogue.R;
import com.wildanka.moviecatalogue.databinding.DialogMenuChangeLanguageBinding;

public class DialogChangeLanguage extends DialogFragment {
    private static final String TAG = "DialogChangeLanguage";
    private DialogMenuChangeLanguageBinding binding;

    public interface OnChangeLanguageListener{
        void changeLanguage();
    }
    public OnChangeLanguageListener mOnChangeLanguageListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_menu_change_language, container, false);

        binding.btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Save Changes");
                //do the localization change

                getDialog().dismiss();
            }
        });
        return binding.getRoot();
    }
}
