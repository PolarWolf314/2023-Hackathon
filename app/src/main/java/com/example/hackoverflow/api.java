package com.example.hackoverflow;

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
                String commonName = (String) commonNamesArray.get(0);


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


    public static void writeToJson(Context context, JSONArray newArray){

        try {
            // Retrieve the existing JSON array from the file, if any
            JSONArray existingArray;
            File file = new File(context.getFilesDir(), "data.json");

            System.out.println(file.getAbsolutePath());

            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                int size = fileInputStream.available();
                byte[] buffer = new byte[size];
                fileInputStream.read(buffer);
                fileInputStream.close();

                String fileContent = new String(buffer, "UTF-8");
                if (fileContent.isEmpty()) {
                    existingArray = new JSONArray();
                } else {
                    existingArray = new JSONArray(fileContent);
                }
            } else {
                existingArray = new JSONArray();
            }

            // Merge the existing and new JSON arrays
            for (int i = 0; i < newArray.length(); i++) {
                existingArray.put(newArray.get(i));
            }

            // Write the merged JSON array back to the file
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(existingArray.toString().getBytes());
            fileOutputStream.close();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

}
