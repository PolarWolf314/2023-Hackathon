package com.example.hackoverflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.hackoverflow.utils.Plant;
import com.example.hackoverflow.utils.PlantAdapter;
import com.example.hackoverflow.utils.DataProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    public static int bySearch = 0;
    public static String searchString;

    private JSONArray plantJsonList;
    private List<Plant> filteredPlantList;
    private PlantAdapter plantAdapter;
    private GridView plantGrid;

    private TextView noCountriesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        DataProvider dataProvider = new DataProvider();

        //Context context = getIntent().getParcelableExtra("context");
        plantJsonList = dataProvider.readJson(this);

        for (int i = 0; i < plantJsonList.length(); i++) {
            try {
                JSONObject item = plantJsonList.getJSONObject(i);
                System.out.println(item);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }

        noCountriesTextView = findViewById(R.id.noPlantsTextView);
        plantGrid = findViewById(R.id.plants);

        plantAdapter = new PlantAdapter(this, R.layout.plant_item, filteredPlantList);

        if (bySearch > 0) {
//            filterPlants(searchString);
        }

        GridView gridView = findViewById(R.id.plants);
        gridView.setAdapter(plantAdapter);

        ImageView homeButton = findViewById(R.id.navbarHomeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchString = "";
                Intent homeIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(homeIntent);
            }
        });

        ImageView favouriteButton = findViewById(R.id.navbarBookmarkButton);
        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent favouriteIntent = new Intent(getBaseContext(), BookmarkActivity.class);
                startActivity(favouriteIntent);
            }
        });

        SearchView searchView = findViewById(R.id.searchActivityView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                filterPlants(newText.toLowerCase(Locale.getDefault()));
                return true;
            }
        });
    }
//
//    private void filterPlants(String filterText) {
//        filteredPlantList.clear();
//
//        if (filterText.isEmpty()) {
//            filteredPlantList.addAll(plantArrayList);
//        } else {
//            for (Plant plant : plantList) {
//                if (plant.getName().toLowerCase(Locale.getDefault()).contains(filterText)) {
//                    filteredPlantList.add(plant);
//                }
//            }
//        }
//
//        if (filteredPlantList.isEmpty()) {
//            noCountriesTextView.setVisibility(View.VISIBLE);
//            plantGrid.setVisibility(View.GONE);
//        } else {
//            noCountriesTextView.setVisibility(View.GONE);
//            plantGrid.setVisibility(View.VISIBLE);
//        }
//
//        plantAdapter.notifyDataSetChanged();
//    }
}