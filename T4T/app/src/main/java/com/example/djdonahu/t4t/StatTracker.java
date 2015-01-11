package com.example.djdonahu.t4t;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Created by Jake on 1/11/15.
 */
public class StatTracker {
    private static final String SAVED_PREFS = "RecyclopsPreferences";
    private static final String STORED_GSON = "GsonData";

    private static StatTracker instance;
    private static Context currentApplicationContext;

    public Tracker_Map tracker_data;
    private SharedPreferences settings = null;

    public class DailyData{
        public float recyling_total = 0;
        public float trash_total = 0;


        public DailyData(float trash, float recycling) {
            this.recyling_total = recycling;
            this.trash_total = trash;
        }
    }

    public class Tracker_Map {
        public HashMap<GregorianCalendar, DailyData> tracker_data;

        public Tracker_Map() {
            this.tracker_data = new HashMap<GregorianCalendar, DailyData>();
        }
    }

    protected StatTracker() {
        if (currentApplicationContext != null) {
            settings = currentApplicationContext.getSharedPreferences(SAVED_PREFS, Context.MODE_PRIVATE);
            String json = settings.getString(STORED_GSON, "");
            Gson g = new Gson();
            try {
                tracker_data = g.fromJson(json, Tracker_Map.class);
            } catch (JsonParseException e) {
                // Presumably this was due to an updated data class
                // Right now just tosses old saved data
                Log.e(SAVED_PREFS, "JSON Exception in saved preferences");
            }
            if (tracker_data == null) {
                tracker_data = new Tracker_Map();
                Log.i(SAVED_PREFS, "Unable to load preferences");
            }
        } else {
            Log.e(SAVED_PREFS, "Settings not initialized, call getInstance(context) first");
        }
    }

    public static StatTracker getInstance() {
        if (instance == null) {
            instance = new StatTracker();
        }

        return instance;
    }

    public static StatTracker getInstance(Context c) {
        currentApplicationContext = c;
        if (instance == null) {
            instance = new StatTracker();
        }

        return instance;
    }

    private void commitToPrefs() {
        if (settings != null) {
            SharedPreferences.Editor e = settings.edit();
            Gson g = new Gson();
            String json;
            json = g.toJson(tracker_data);
            e.putString(STORED_GSON, json);
            e.commit();
        } else {
            Log.i(SAVED_PREFS, "Attempted to save to null settings");
        }
    }

    public static void addStats(GregorianCalendar day, float trash, float recycling){
        DailyData sumsSoFar = getInstance().tracker_data.tracker_data.get(day);
        sumsSoFar.trash_total += trash;
        sumsSoFar.recyling_total += recycling;
        getInstance().commitToPrefs();
    }

    public static void addStats(float trash, float recycling){
        GregorianCalendar currentDate = new GregorianCalendar();
        addStats(currentDate, trash, recycling);
    }

    public static void getGraphData(GregorianCalendar start, GregorianCalendar end){
        long diff = start.getTimeInMillis() - end.getTimeInMillis();
        int numDays = (int) (diff / (24 * 60 * 60 * 1000)) + 2;

        SimpleDateFormat fmt = new SimpleDateFormat("MMM/dd/yyyy");

        HashMap<GregorianCalendar, DailyData> data = getInstance().tracker_data.tracker_data;

        GraphStats gs = new GraphStats(numDays);
        GregorianCalendar currentDay = (GregorianCalendar) start.clone();
        for(int i=0; i < numDays; ++i){
            gs.XValues[i] = i;
            gs.labels[i] = fmt.format(currentDay.getTime());

            float recycling = data.get(currentDay).recyling_total;
            float trash = data.get(currentDay).trash_total;

            gs.recyclingSums[i] = recycling;
            gs.totalSums[i] = recycling + trash;

            currentDay.add(GregorianCalendar.DATE, 1);
        }
    }

}
