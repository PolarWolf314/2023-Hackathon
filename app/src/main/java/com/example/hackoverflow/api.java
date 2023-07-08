package com.example.hackoverflow;

import okhttp3.*;

import java.io.File;
import java.io.IOException;

public class api {
    private static final String ORGAN = "auto";

    private static final String PROJECT = "all"; // try "weurope" or "canada"
    private static final String API_URL = "https://my-api.plantnet.org/v2/identify/" + PROJECT + "?api-key=";
    private static final String API_PRIVATE_KEY = "2b103ZERjEsjWMFUuTa2onvT"; // secret
    private static final String API_SIMSEARCH_OPTION = "&include-related-images=true"; // optional: get most similar images
    private static final String API_LANG = "&lang=en"; // default: en

    public static void identifyPlant(File imageFile) {
//        File imageFile = new File(imagePath);

        if (!imageFile.exists()) {
            System.out.println("The specified image file does not exist.");
            return;
        }

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("images", imageFile.getName(), RequestBody.create(MediaType.parse("image/jpeg"), imageFile))
                .addFormDataPart("organs", ORGAN)
                .build();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(API_URL + API_PRIVATE_KEY)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String jsonString = response.body().string();
                System.out.println(jsonString);
            } else {
                System.out.println("Request failed with code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

