package com.example.hackoverflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.hackoverflow.utils.PlantAdapter;
import com.example.hackoverflow.utils.DataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    public static int bySearch = 0;
    public static String searchString;

    private List<String> PlantList;
    private List<String> filteredPlantList;
    private PlantAdapter plantAdapter;
    private GridView plantGrid;

    private TextView noCountriesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        DataProvider dataProvider = new DataProvider(this, "data.json");
        countryList = dataProvider.getCountry();
        filteredCountryList = new ArrayList<>(countryList);

        noCountriesTextView = findViewById(R.id.noCountriesTextView);
        countriesGrid = findViewById(R.id.countries);

        countriesAdapter = new CountryAdaptor(this, R.layout.country_item, filteredCountryList);

        if (bySearch > 0) {
            filterCountries(searchString);
        }


        GridView gridView = findViewById(R.id.countries);
        gridView.setAdapter(countriesAdapter);

        ImageView homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchString = "";
                Intent homeIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(homeIntent);
            }
        });

        ImageView favouriteButton = findViewById(R.id.favouriteButton);
        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent favouriteIntent = new Intent(getBaseContext(), FavouriteActivity.class);
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
                filterCountries(newText.toLowerCase(Locale.getDefault()));
                return true;
            }
        });
    }

    private void filterCountries(String filterText) {
        filteredCountryList.clear();

        if (filterText.isEmpty()) {
            filteredCountryList.addAll(countryList);
        } else {
            for (Country country : countryList) {
                if (country.getName().toLowerCase(Locale.getDefault()).contains(filterText)) {
                    filteredCountryList.add(country);
                }
            }
        }

        if (filteredCountryList.isEmpty()) {
            noCountriesTextView.setVisibility(View.VISIBLE);
            countriesGrid.setVisibility(View.GONE);
        } else {
            noCountriesTextView.setVisibility(View.GONE);
            countriesGrid.setVisibility(View.VISIBLE);
        }

        countriesAdapter.notifyDataSetChanged();
    }
}