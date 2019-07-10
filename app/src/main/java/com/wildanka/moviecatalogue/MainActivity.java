package com.wildanka.moviecatalogue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wildanka.moviecatalogue.util.SharedPref;
import com.wildanka.moviecatalogue.view.FavoritesFragment;
import com.wildanka.moviecatalogue.view.MovieFragment;
import com.wildanka.moviecatalogue.view.ReminderActivity;
import com.wildanka.moviecatalogue.view.TVShowFragment;
import com.wildanka.moviecatalogue.view.dialog.ChangeLanguageDialog;
import com.wildanka.moviecatalogue.view.dialog.SearchMovieBottomSheetDialog;
import com.wildanka.moviecatalogue.viewmodel.MovieTVViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;


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

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        Log.e("TodayDate", formatter.format(date));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true); //showing icon in actionbar overflow menu
        }
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
                break;
            case R.id.menu_search:
                Toast.makeText(MainActivity.this, "search",Toast.LENGTH_SHORT).show();
                System.out.println("clicked search");
                SearchMovieBottomSheetDialog searchMovieBottomSheetDialog = new SearchMovieBottomSheetDialog();
                searchMovieBottomSheetDialog.show(getSupportFragmentManager(), "");
                break;
            case R.id.menu_reminder:
                Toast.makeText(MainActivity.this, "search", Toast.LENGTH_SHORT).show();
                System.out.println("clicked reminder");
                Intent in = new Intent(MainActivity.this, ReminderActivity.class);
                startActivity(in);
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
