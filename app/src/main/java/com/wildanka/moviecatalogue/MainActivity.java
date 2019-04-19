package com.wildanka.moviecatalogue;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.wildanka.moviecatalogue.util.SharedPref;
import com.wildanka.moviecatalogue.view.ChangeLanguageDialog;
import com.wildanka.moviecatalogue.view.MovieFragment;
import com.wildanka.moviecatalogue.view.TVShowFragment;
import com.wildanka.moviecatalogue.view.adapter.MoviesVPAdapter;
import com.wildanka.moviecatalogue.viewmodel.MovieTVViewModel;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class MainActivity extends AppCompatActivity implements ChangeLanguageDialog.OnChangeLanguageListener {
    private MoviesVPAdapter tabAdapter;
    private TabLayout tlMoviesCategory;
    private ViewPager vpMoviesCategory;
    private String language;
    private MovieTVViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sp = new SharedPref(this);
        language = sp.loadLanguage();
        Log.d(TAG, "onCreateView: " + language);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MovieTVViewModel.class);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_main_activity);
        setSupportActionBar(toolbar);
        //binding view
        tlMoviesCategory = (TabLayout) findViewById(R.id.tl_movies_category);
        vpMoviesCategory = (ViewPager) findViewById(R.id.vp_movies_category);


        //tabs arrayList
        ArrayList<String> tabs = new ArrayList<>();
        tabs.add("Movies");
        tabs.add("TV Shows");

        tabAdapter = new MoviesVPAdapter(getSupportFragmentManager(), tabs);
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
                ChangeLanguageDialog dialog = new ChangeLanguageDialog();
                dialog.show(getSupportFragmentManager(), "changeLanguage");
//                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
//                startActivity(mIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void changeLanguage() {
        SharedPref sp = new SharedPref(this);
        language = sp.loadLanguage();
        Fragment fragment = tabAdapter.getFragment(tlMoviesCategory.getSelectedTabPosition());
        Fragment fragmentMovie = tabAdapter.getFragment(0);
        Fragment fragmentTVShow = tabAdapter.getFragment(1);
        //null checking to make it safe calling onRefresh method on every active fragment
        if (fragment != null) {
            if (fragmentMovie != null) {
                ((MovieFragment) fragmentMovie).onRefresh(language);
            }
            if (fragmentTVShow != null) {
                ((TVShowFragment) fragmentTVShow).onRefresh(language);
            }
        }
//        Log.e(TAG, "OnLanguageChangedListener: " + language);
    }
}
