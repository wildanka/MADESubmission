package com.wildanka.moviecatalogue.view.adapter;

import android.util.SparseArray;
import android.view.ViewGroup;

import com.wildanka.moviecatalogue.view.FavoritesMoviesFragment;
import com.wildanka.moviecatalogue.view.FavoritesTVShowFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

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
                return new FavoritesMoviesFragment();
            case 1:
                return new FavoritesTVShowFragment();
            default:
                return new FavoritesMoviesFragment();
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