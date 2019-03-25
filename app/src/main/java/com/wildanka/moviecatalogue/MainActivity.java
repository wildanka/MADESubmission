package com.wildanka.moviecatalogue;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.wildanka.moviecatalogue.view.MovieFragment;
import com.wildanka.moviecatalogue.view.adapter.MoviesVPAdapter;

public class MainActivity extends AppCompatActivity {
    private MoviesVPAdapter tabAdapter;
    private TabLayout tlMoviesCategory;
    private ViewPager vpMoviesCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //binding view
        tlMoviesCategory = (TabLayout) findViewById(R.id.tl_movies_category);
        vpMoviesCategory = (ViewPager) findViewById(R.id.vp_movies_category);

        tabAdapter = new MoviesVPAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new MovieFragment(), "Movies");
        tabAdapter.addFragment(new MovieFragment(), "TV Shows");

        vpMoviesCategory.setAdapter(tabAdapter);
        tlMoviesCategory.setupWithViewPager(vpMoviesCategory);
    }


}
