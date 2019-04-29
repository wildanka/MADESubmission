package com.wildanka.moviecatalogue.view.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.wildanka.moviecatalogue.view.FavoriteTVShowFragment;
import com.wildanka.moviecatalogue.view.FavoritesMoviesFragment;
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
                FavoritesMoviesFragment movieFragment = new FavoritesMoviesFragment();
                return movieFragment;
            case 1:
                FavoriteTVShowFragment tvShowFragment = new FavoriteTVShowFragment();
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