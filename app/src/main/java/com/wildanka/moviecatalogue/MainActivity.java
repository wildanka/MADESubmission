package com.wildanka.moviecatalogue;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.wildanka.moviecatalogue.view.MovieFragment;
import com.wildanka.moviecatalogue.view.TVShowFragment;
import com.wildanka.moviecatalogue.view.adapter.MoviesVPAdapter;

public class MainActivity extends AppCompatActivity {
    private MoviesVPAdapter tabAdapter;
    private TabLayout tlMoviesCategory;
    private ViewPager vpMoviesCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_main_activity);
        setSupportActionBar(toolbar);
        //binding view
        tlMoviesCategory = (TabLayout) findViewById(R.id.tl_movies_category);
        vpMoviesCategory = (ViewPager) findViewById(R.id.vp_movies_category);

        tabAdapter = new MoviesVPAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new MovieFragment(), "Movies");
        tabAdapter.addFragment(new TVShowFragment(), "TV Shows");

        vpMoviesCategory.setAdapter(tabAdapter);
        tlMoviesCategory.setupWithViewPager(vpMoviesCategory);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_bahasa:
                Toast.makeText(MainActivity.this, "Clicked",Toast.LENGTH_SHORT).show();
                System.out.println("clicked menu");
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
