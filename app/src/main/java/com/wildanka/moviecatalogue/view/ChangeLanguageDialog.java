package com.wildanka.moviecatalogue.view;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;

import com.wildanka.moviecatalogue.R;
//import com.wildanka.moviecatalogue.databinding.DialogMenuChangeLanguageBinding;
import com.wildanka.moviecatalogue.util.SharedPref;

public class ChangeLanguageDialog extends DialogFragment {
//    private DialogMenuChangeLanguageBinding binding;
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
        View rootView = inflater.inflate(R.layout.dialog_menu_change_language, container, false);
        final RadioGroup rgLanguage = rootView.findViewById(R.id.rg_language);
        Button btnSaveChanges = rootView.findViewById(R.id.btn_save_changes);
        switch (language) {
            case "en-US":
                rgLanguage.check(R.id.rb_english_us);
                break;
            case "id-ID":
                rgLanguage.check(R.id.rb_indonesian_id);
                break;
        }

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save to the sharedpref
                switch (rgLanguage.getCheckedRadioButtonId()) {
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
        return rootView;
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
