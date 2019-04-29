package com.wildanka.moviecatalogue.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.wildanka.moviecatalogue.R;
import com.wildanka.moviecatalogue.view.adapter.MoviesVPAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {
    private MoviesVPAdapter tabAdapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        TabLayout tlMoviesCategory = (TabLayout) rootView.findViewById(R.id.tl_favorites_category);
        ViewPager vpMoviesCategory = (ViewPager) rootView.findViewById(R.id.vp_favorites_category);

        //tabs arrayList
        ArrayList<String> tabs = new ArrayList<>();
        tabs.add("Movies");
        tabs.add("TV Shows");

        tabAdapter = new MoviesVPAdapter(getActivity().getSupportFragmentManager(), tabs);
        vpMoviesCategory.setAdapter(tabAdapter);
        tlMoviesCategory.setupWithViewPager(vpMoviesCategory);
        return rootView;
    }

}
