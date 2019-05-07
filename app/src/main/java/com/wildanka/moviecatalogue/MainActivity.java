package com.wildanka.moviecatalogue;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wildanka.moviecatalogue.util.SharedPref;
import com.wildanka.moviecatalogue.view.ChangeLanguageDialog;
import com.wildanka.moviecatalogue.view.FavoritesFragment;
import com.wildanka.moviecatalogue.view.MovieFragment;
import com.wildanka.moviecatalogue.view.TVShowFragment;
import com.wildanka.moviecatalogue.viewmodel.MovieTVViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


public class MainActivity extends AppCompatActivity implements ChangeLanguageDialog.OnChangeLanguageListener {
    private static final String TAG = "MainActivity";
    private String language;
    private MovieTVViewModel viewModel;
    private Fragment selectedFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.fragmentMovieMenu:
                    selectedFragment = new MovieFragment();
                    break;
                case R.id.fragmentTVShowsMenu:
                    selectedFragment = new TVShowFragment();
                    break;
                case R.id.fragmentFavoritesMenu:
                    selectedFragment = new FavoritesFragment();
                    break;
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sp = new SharedPref(this);
        language = sp.loadLanguage();
        Log.d(TAG, "onCreateView: " + language);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
        bottomNavView.setOnNavigationItemSelectedListener(navListener);

        selectedFragment = new MovieFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .commit();
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_main_activity);
        setSupportActionBar(toolbar);
        //binding view
//        tlMoviesCategory = (TabLayout) findViewById(R.id.tl_movies_category);
//        vpMoviesCategory = (ViewPager) findViewById(R.id.vp_movies_category);


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
//        Fragment fragment = tabAdapter.getFragment(tlMoviesCategory.getSelectedTabPosition());
//        Fragment fragmentMovie = tabAdapter.getFragment(0);
//        Fragment fragmentTVShow = tabAdapter.getFragment(1);
//        //null checking to make it safe calling onRefresh method on every active fragment
//        if (fragment != null) {
//            if (fragmentMovie != null) {
//                ((MovieFragment) fragmentMovie).onRefresh(language);
//            }
//            if (fragmentTVShow != null) {
//                ((TVShowFragment) fragmentTVShow).onRefresh(language);
//            }
//        }
    }
}
