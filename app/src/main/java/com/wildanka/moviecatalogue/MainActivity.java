package com.wildanka.moviecatalogue;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.wildanka.moviecatalogue.model.Movie;
import com.wildanka.moviecatalogue.view.MovieAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String[] dataTitle;
    private String[] dataDescription;
    private TypedArray dataPoster;
    private MovieAdapter adapter;
    private ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.lv_movie);

        //TODO 1 : implement the listView Adapter
        prepareStringArray();

        adapter = new MovieAdapter(this);
        listView.setAdapter(adapter);
        addItem();

        //create a listview listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(movies.get(position).getTitle());
                Toast.makeText(MainActivity.this, movies.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addItem(){
        movies = new ArrayList<>();
        for (int i = 0; i < dataTitle.length; i++) {
            Movie movie = new Movie(
                    dataTitle[i],
                    "1992",
                    "75%",
                    dataDescription[i],
                    dataDescription[i],
                    dataPoster.getResourceId(i,-1)
            );
            movies.add(movie);
        }
        //after finishing the loop, let's send the data to the adapter
        adapter.setListMovie(movies);
    }

    private void prepareStringArray(){
        dataTitle = getResources().getStringArray(R.array.data_name);
        dataDescription = getResources().getStringArray(R.array.data_description);
        dataPoster = getResources().obtainTypedArray(R.array.data_photo);
    }
}
