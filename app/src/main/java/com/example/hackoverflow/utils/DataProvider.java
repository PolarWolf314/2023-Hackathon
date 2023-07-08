package com.example.hackoverflow.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DataProvider {

    private static final String JSON_FILE_NAME = "plants.json";

    private Context context;

    public DataProvider(Context context) {
        this.context = context;
    }

    public List<Plant> getPlants() {
        List<Plant> plants = new ArrayList<>();
        try {
            String json = loadJSONFromAsset();
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String image = jsonObject.getString("image");
                String description = jsonObject.getString("description");
                Boolean bookmark = jsonObject.getBoolean("bookmark");

                Plant plant = new Plant(name, image, description, bookmark);
                plants.add(plant);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return plants;
    }

    private String loadJSONFromAsset() {
        String json;
        try {
            InputStream inputStream = context.getAssets().open(JSON_FILE_NAME);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
