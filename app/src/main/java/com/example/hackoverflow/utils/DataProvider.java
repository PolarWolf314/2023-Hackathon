package com.example.hackoverflow.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataProvider {
    private Context context;
    private String fileName;

    public DataProvider(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
    }

    public List<Plant> getPlant() {
        List<Plant> plantList = new ArrayList<>();

        try {
            String jsonData = loadJSONFromAsset();
            JSONObject jsonRootObject = new JSONObject(jsonData);
            JSONArray continentsArray = jsonRootObject.getJSONArray("continents");

            for (int i = 0; i < continentsArray.length(); i++) {
                JSONObject plantObject = continentsArray.getJSONObject(i);
                String plantName = continentObject.getString("name");
                JSONArray countriesArray = continentObject.getJSONArray("countries");

                for (int j = 0; j < countriesArray.length(); j++) {
                    JSONObject plantObject = countriesArray.getJSONObject(j);
                    String name = plantObject.getString("name");
                    String image = plantObject.getString("image");
                    String description = plantObject.getString("description");
                    boolean bookmark = false;

                    Plant plant = new Plant(name, image, description, bookmark);
                    plantList.add(plant);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return plantList;
    }

    private String loadJSONFromAsset() {
        String json = null;

        try {
            InputStream inputStream = context.getAssets().open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            json = stringBuilder.toString();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

}
