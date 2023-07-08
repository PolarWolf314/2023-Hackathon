package com.example.hackoverflow;

import java.io.File;
import java.io.IOException;

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
                System.out.println(result);
                // Handle the response or update the UI here
            }
        }.execute();
    }
}
