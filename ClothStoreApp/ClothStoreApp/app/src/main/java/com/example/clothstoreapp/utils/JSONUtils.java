package com.example.clothstoreapp.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.clothstoreapp.models.Address;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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

    // Method to save address data as JSON
    public static void saveAddressToJson(Context context, Address address) {
        try {
            // Create a JSON object to represent the address
            JSONObject addressJson = new JSONObject();
            addressJson.put("name", address.getName());
            addressJson.put("phone", address.getPhone());
            addressJson.put("address", address.getAddress());
            addressJson.put("city", address.getCity());
            addressJson.put("state", address.getState());
            addressJson.put("postalCode", address.getPostalCode());

            // Write the JSON object to a file in the app's private storage
            FileOutputStream fos = context.openFileOutput("user_address.json", Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            writer.write(addressJson.toString());
            writer.close();

            Toast.makeText(context, "Address saved successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error saving address", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to load saved address from the private storage
    public static Address loadAddressFromJson(Context context) {
        String json = null;
        try {
            // Open the saved address file
            InputStream is = context.openFileInput("user_address.json");
            json = convertStreamToString(is);

            // Convert the JSON string to an Address object
            if (json != null) {
                JSONObject addressJson = new JSONObject(json);
                String name = addressJson.getString("name");
                String phone = addressJson.getString("phone");
                String address = addressJson.getString("address");
                String city = addressJson.getString("city");
                String state = addressJson.getString("state");
                String postalCode = addressJson.getString("postalCode");

                return new Address(name, phone, address, city, state, postalCode);
            }
        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error loading saved address", Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}
