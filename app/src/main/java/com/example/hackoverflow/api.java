package com.example.hackoverflow;

import java.io.File;
import java.io.IOException;

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

    public static void identifyPlant(final File imageFile) {
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
                //System.out.println(result);
                parseData(result);
                // Handle the response or update the UI here
            }
        }.execute();
    }

    public static void parseData(String jsonData){
        // Assuming you have the JSON data in a String variable called jsonData
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
                String scientificName = highestScoreData.getJSONObject("species").getString("scientificName");
                // You can add more fields as per your JSON structure

                // Use the extracted data as needed
                System.out.println("Scientific Name: " + scientificName);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
    public static void writeToJson(){
        try {
            AssetManager manager = getAssets(); // Replace 'getAssets()' with your context's asset manager
            InputStream file = manager.open("your_file_name.json");

            int size = file.available();
            byte[] buffer = new byte[size];
            file.read(buffer);
            file.close();

            String jsonString = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(jsonString);

            // Create a new JSON object or array with the data you want to append
            // For example, let's assume you have a new JSON object to append called 'newData'
            jsonArray.put(newData);

            // Write the updated JSON array back to the file
            OutputStreamWriter outputWriter = new OutputStreamWriter(openFileOutput("your_file_name.json", Context.MODE_PRIVATE));
            outputWriter.write(jsonArray.toString());
            outputWriter.close();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }
     **/

}
