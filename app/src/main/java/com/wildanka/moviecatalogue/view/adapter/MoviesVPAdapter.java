package com.wildanka.moviecatalogue.view.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MoviesVPAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> tabFragment = new ArrayList<>();
    private final List<String> tabTitle = new ArrayList<>();

    public MoviesVPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return tabFragment.get(i);
    }

    @Override
    public int getCount() {
        return tabTitle.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle.get(position);
    }

    public void addFragment(Fragment newFragment,String newTitle ){
        tabFragment.add(newFragment);
        tabTitle.add(newTitle);
    }
}
