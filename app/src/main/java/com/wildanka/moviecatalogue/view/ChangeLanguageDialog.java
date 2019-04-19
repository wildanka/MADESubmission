package com.wildanka.moviecatalogue.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.wildanka.moviecatalogue.R;
import com.wildanka.moviecatalogue.databinding.DialogMenuChangeLanguageBinding;
import com.wildanka.moviecatalogue.util.SharedPref;

public class ChangeLanguageDialog extends DialogFragment {
    private DialogMenuChangeLanguageBinding binding;
    private static final String TAG = "ChangeLanguageDialog";
    private SharedPref sp;
    public interface OnChangeLanguageListener{
        void changeLanguage();
    }
    private OnChangeLanguageListener mOnChangeLanguageListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sp = new SharedPref(getActivity());
        String language = sp.loadLanguage();

        Log.e(TAG, "onCreateView : " + language);
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_menu_change_language, container, false);

        switch (language) {
            case "en-US":
                binding.rgLanguage.check(R.id.rb_english_us);
                break;
            case "id-ID":
                binding.rgLanguage.check(R.id.rb_indonesian_id);
                break;
        }

        binding.btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save to the sharedpref
                switch (binding.rgLanguage.getCheckedRadioButtonId()) {
                    case R.id.rb_english_us:
                        Log.e(TAG, "onClick: to English");
                        sp.setLanguage("en-US");
                        break;
                    case R.id.rb_indonesian_id:
                        Log.e(TAG, "onClick: ke Bahasa Indonesia");
                        sp.setLanguage("id-ID");
                        break;
                }

                //refresh the movies and tvshows fragment
                mOnChangeLanguageListener.changeLanguage();
                getDialog().dismiss();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Window window = getDialog().getWindow();
        window.setLayout(width, (int) (height * 0.45));
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnChangeLanguageListener = (OnChangeLanguageListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }
}
