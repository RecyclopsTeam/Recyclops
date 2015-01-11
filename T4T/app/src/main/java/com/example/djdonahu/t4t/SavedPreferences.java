package com.example.djdonahu.t4t;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class SavedPreferences {

    private static final String SAVED_PREFS = "RecyclopsPreferences";
    private static final String STORED_GSON = "GsonData";

    private static SavedPreferences instance = null;
    public Product_List all_products;
    private SharedPreferences settings = null;
    private static Context applicationContext;

    public class Product_List {
        public ArrayList<Product> products;

        public Product_List() {
            this.products = new ArrayList<Product>();
        }
    }

    protected SavedPreferences() {
        if (applicationContext != null) {
            settings = applicationContext.getSharedPreferences(SAVED_PREFS, Context.MODE_PRIVATE);
            String json = settings.getString(STORED_GSON, "");
            Gson g = new Gson();
            try {
                all_products = g.fromJson(json, Product_List.class);
            } catch (JsonParseException e) {
                // Presumably this was due to an updated data class
                // Right now just tosses old saved data
                Log.e(SAVED_PREFS, "JSON Exception in saved preferences");
            }
            if (all_products == null) {
                all_products = new Product_List();
                Log.i(SAVED_PREFS, "Unable to load preferences");
            }
        } else {
            Log.e(SAVED_PREFS, "Settings not initialized, call getInstance(context) first");
        }
    }

    public static SavedPreferences getInstance() {
        if (instance == null) {
            instance = new SavedPreferences();
        }

        return instance;
    }

    // This method should always be called first to set the application context
    // Really poor code, but quick and dirty
    public static SavedPreferences getInstance(Context c) {
        applicationContext = c;
        if (instance == null) {
            instance = new SavedPreferences();
        }

        return instance;
    }

    private void commitToPrefs() {
        if (settings != null) {
            SharedPreferences.Editor e = settings.edit();
            Gson g = new Gson();
            String json = g.toJson(all_products);
            e.putString(STORED_GSON, json);
            e.commit();
        } else {
            Log.i(SAVED_PREFS, "Attempted to save to null settings");
        }
    }

    public void addProduct(Product item) {
        instance.all_products.products.add(item);
        instance.commitToPrefs();
    }

    public void removeProduct(Product item) {
        instance.all_products.products.remove(item);
        instance.commitToPrefs();
    }

    public ArrayList<Product> getSavedProductList() {
        return instance.all_products.products;
    }

    public Product getProduct(Product item) {
        int i = instance.all_products.products.lastIndexOf(item);
        return instance.all_products.products.get(i);
    }

}
