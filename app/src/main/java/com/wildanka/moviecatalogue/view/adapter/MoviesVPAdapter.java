package com.wildanka.moviecatalogue.view.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.wildanka.moviecatalogue.view.MovieFragment;
import com.wildanka.moviecatalogue.view.TVShowFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MoviesVPAdapter extends FragmentStatePagerAdapter {
    private final SparseArray<WeakReference<Fragment>> instantiatedFragment = new SparseArray<>();
    private ArrayList<String> mTabHeader;


    public MoviesVPAdapter(FragmentManager fm, ArrayList<String> mTabHeader) {
        super(fm);
        this.mTabHeader = mTabHeader;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                MovieFragment movieFragment = new MovieFragment();
                return movieFragment;
            case 1:
                TVShowFragment tvShowFragment = new TVShowFragment();
                return tvShowFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabHeader.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final Fragment fragment = (Fragment) super.instantiateItem(container, position);
        instantiatedFragment.put(position, new WeakReference<Fragment>(fragment));
        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        instantiatedFragment.remove(position);
        super.destroyItem(container, position, object);
    }

    @Nullable
    public Fragment getFragment(final int position) {
        final WeakReference<Fragment> wr = instantiatedFragment.get(position);
        if (wr != null) {
            return wr.get();
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabHeader.get(position);
    }

}
