package com.example.clothstoreapp.utils;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JSONUtils {

    // Method to read the JSON file from Assets folder
    public static String loadJSONFromAsset(Context context, String filename) {
        String json = null;
        try {
            // Open the file from the Assets folder
            InputStream is = context.getAssets().open(filename);

            // Convert InputStream to String
            json = convertStreamToString(is);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error loading JSON file", Toast.LENGTH_SHORT).show();
        }
        return json;
    }

    // Helper method to convert InputStream to String
    public static String convertStreamToString(InputStream is) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    // Example of how to parse the JSON (you can call this method)
    public static void parseItemsJSON(Context context) {
        String json = loadJSONFromAsset(context, "items.json"); // Replace "items.json" with your file name
        if (json != null) {
            try {
                // Parse the JSON data
                JSONObject jsonObject = new JSONObject(json);
                JSONArray itemsArray = jsonObject.getJSONArray("items");

                // Example: loop through the items and process them
                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject item = itemsArray.getJSONObject(i);
                    String title = item.getString("title");
                    String description = item.getString("description");
                    // Add other fields as needed, like price, image, etc.
                    System.out.println("Title: " + title);
                    System.out.println("Description: " + description);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
