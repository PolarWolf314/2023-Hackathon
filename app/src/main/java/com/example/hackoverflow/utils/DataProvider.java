package com.example.hackoverflow.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataProvider {

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

    public static JSONArray readJson(Context context){
        JSONArray resultArray = new JSONArray();
        try {
            FileInputStream fileInputStream = context.openFileInput("your_file_name.json");
            int size = fileInputStream.available();
            byte[] buffer = new byte[size];
            fileInputStream.read(buffer);
            fileInputStream.close();

            String fileContent = new String(buffer, "UTF-8");
            resultArray = new JSONArray(fileContent);
            System.out.println(resultArray);
            return resultArray;
            // Use the parsed JSON array as needed
            // ...

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return resultArray;

    }
}