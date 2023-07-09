package com.example.hackoverflow;

import static com.example.hackoverflow.JSON.readJson;
import static com.example.hackoverflow.JSON.writeToJson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.res.AssetManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import okhttp3.*;

import android.os.AsyncTask;

public class api {
    private static final String ORGAN = "auto";
    private static final String PROJECT = "all";
    private static final String URL = "https://my-api.plantnet.org/v2/identify/" + PROJECT + "?api-key=2b103ZERjEsjWMFUuTa2onvT";


    public static void identifyPlant(final File imageFile, Context context) {
        if (!imageFile.exists()) {
            System.out.println("The specified image file does not exist.");
            return;
        }

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("images", imageFile.getName(), RequestBody.create(MediaType.parse("image/*"), imageFile))
                        .addFormDataPart("organs", ORGAN)
                        .build();

                Request request = new Request.Builder()
                        .url(URL)
                        .post(requestBody)
                        .build();

                OkHttpClient client = new OkHttpClient();

                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        return response.body().string();
                    } else {
                        return "HTTP request failed: " + response.code();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return "IOException occurred: " + e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                JSONArray resultArray = parseData(result, imageFile);
                writeToJson(context, resultArray);
                //readJson(context);

                // Handle the response or update the UI here
            }
        }.execute();
    }

    public static JSONArray parseData(String jsonData, File imageFile){
        // Assuming you have the JSON data in a String variable called jsonData
        JSONArray resultArray = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray resultsArray = jsonObject.getJSONArray("results");

            // Find the data with the highest score
            double maxScore = 0;
            JSONObject highestScoreData = null;
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject result = resultsArray.getJSONObject(i);
                double score = result.getDouble("score");
                if (score > maxScore) {
                    maxScore = score;
                    highestScoreData = result;
                }
            }

            if (highestScoreData != null) {
                // Extract the required information from the highest score data
                String scientificName = highestScoreData.getJSONObject("species").getJSONObject("genus").getString("scientificName");

                String familyName = highestScoreData.getJSONObject("species").getJSONObject("family").getString("scientificName");
                // You can add more fields as per your JSON structure


                JSONObject species = highestScoreData.getJSONObject("species");
                JSONArray commonNamesArray = species.getJSONArray("commonNames");
                String commonName;
                if(!commonNamesArray.isNull(0)) {
                    commonName = (String) commonNamesArray.get(0);
                } else {
                    commonName = "No common name found";
                }


                String imagePath = imageFile.getAbsolutePath();

                String bookmark = "false";

                // Use the extracted data as needed
                System.out.println("Scientific Name: " + scientificName);
                System.out.println("Family Name: " + familyName);
                System.out.println("Common Name: " + commonName);
                System.out.println("Image Path: " + imagePath);


                resultArray.put(scientificName);
                resultArray.put(familyName);
                resultArray.put(commonName);
                resultArray.put(imagePath);
                resultArray.put(bookmark);

                return resultArray;


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultArray;
    }




}
