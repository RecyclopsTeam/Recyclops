package com.example.djdonahu.t4t;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Debug;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.jjoe64.graphview.series.DataPoint;

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

    private static SimpleDateFormat fmt = new SimpleDateFormat("MMM/dd/yyyy");
    private static SimpleDateFormat label_fmt = new SimpleDateFormat("MM/dd");

    private static final HashMap<String, Integer> weightValues;
    static
    {
        weightValues = new HashMap<String, Integer>();
        weightValues.put("tiny", 1);
        weightValues.put("small", 3);
        weightValues.put("medium_small", 6);
        weightValues.put("medium", 10);
        weightValues.put("medium_large", 15);
        weightValues.put("large", 25);
        weightValues.put("huge", 50);
    }

    public Tracker_Map tracker_data;
    private SharedPreferences settings = null;

    public class Tracker_Map {
        public HashMap<String, DailyData> tracker_data;

        public Tracker_Map() {
            this.tracker_data = new HashMap<String, DailyData>();
        }
    }

    protected StatTracker() {
        if (currentApplicationContext != null) {
            settings = currentApplicationContext.getSharedPreferences(SAVED_PREFS, Context.MODE_PRIVATE);
            String json = settings.getString(STORED_GSON, "");
            Gson g = new Gson();
            try {
                //tracker_data = g.fromJson(json, Tracker_Map.class);
            } catch (JsonParseException e) {
                // Presumably this was due to an updated data class
                // Right now just tosses old saved data
                Log.e(SAVED_PREFS, "JSON Exception in saved preferences");
            }
        } else {
            Log.e(SAVED_PREFS, "Settings not initialized, call getInstance(context) first");
        }
        if (tracker_data == null || tracker_data.tracker_data == null) {
            tracker_data = new Tracker_Map();
            generatePhonyData(tracker_data);
            Log.i(SAVED_PREFS, "Unable to load preferences");
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

        DailyData sumsSoFar = getInstance().tracker_data.tracker_data.get(format(day));
        if(sumsSoFar == null){
            sumsSoFar = new DailyData(0,0);
        }
        sumsSoFar.trash_total += trash;
        sumsSoFar.recyling_total += recycling;
        getInstance().tracker_data.tracker_data.put(format(day), sumsSoFar);
        //getInstance().commitToPrefs();
    }

    //Adds stats for today
    public static void addStats(float trash, float recycling){
        GregorianCalendar currentDate = new GregorianCalendar();
        addStats(currentDate, trash, recycling);
    }

    public static void addStats(Product product){
        float recycling = 0;
        float trash = 0;
        float contentWeight = 0;
        float packagingWeight = 0;
        if (product.contents_size != null) {
            contentWeight = weightValues.get(product.contents_size);
        }
        if (product.packaging_size != null) {
            packagingWeight = weightValues.get(product.packaging_size);
        }

        if(product.hasPackaging()) {
            if (product.contentsRecyclable()) {
                recycling += contentWeight;
            } else {
                trash += contentWeight;
            }
        }
        if(product.hasContents()) {
            if (product.packageRecyclable()) {
                recycling += packagingWeight;
            } else {
                trash += packagingWeight;
            }
        }
        addStats(trash, recycling);
    }

    public static GraphStats getGraphData(GregorianCalendar start, GregorianCalendar end){
        long diff = start.getTimeInMillis() - end.getTimeInMillis();
        int numDays = (int) (diff / (24 * 60 * 60 * 1000))+1;

        Log.d("Mem", "size: "+numDays);

        HashMap<String, DailyData> data = getInstance().tracker_data.tracker_data;

        GraphStats gs = new GraphStats(numDays);
        GregorianCalendar currentDay = end;//(GregorianCalendar) start.clone();
        for(int i=0; i < numDays; ++i){
            gs.XValues[i] = i;
            String day = format(currentDay);
            gs.labels[i] = label_fmt.format(currentDay.getTime());

            DailyData dayData = data.get(format(currentDay));
            float recycling = 0;
            float trash = 0;
            if(dayData != null){
                recycling = dayData.recyling_total;
                trash = dayData.trash_total;
            }

            gs.recyclingData[i] = new DataPoint(i,recycling);
            gs.totalData[i] = new DataPoint(i,recycling + trash);

            currentDay.add(GregorianCalendar.DATE, 1);
        }
        return gs;
    }

    //gets last 30 days worth of data
    public static GraphStats getGraphData(){
        GregorianCalendar today = new GregorianCalendar();
        GregorianCalendar aMonthAgo = ((GregorianCalendar) today.clone());
        aMonthAgo.add(GregorianCalendar.DATE, -30);
        return getGraphData(today, aMonthAgo);
    }

    private void generatePhonyData(Tracker_Map tracker_map){
        GregorianCalendar today = new GregorianCalendar();
        GregorianCalendar aMonthAgo = ((GregorianCalendar) today.clone());
        aMonthAgo.add(GregorianCalendar.DATE, -30);
        long diff = today.getTimeInMillis() - aMonthAgo.getTimeInMillis();
        int numDays = (int) (diff / (24 * 60 * 60 * 1000)) + 2;

        HashMap<String, DailyData> data = this.tracker_data.tracker_data;

        GraphStats gs = new GraphStats(numDays);
        GregorianCalendar currentDay = aMonthAgo;
        for(int i=0; i < numDays; ++i){
            String day = format(currentDay);
            this.tracker_data.tracker_data.put(format(currentDay), new DailyData((float)Math.random()*20, (float)Math.random()*20));
            //tracker_map.tracker_data.put((GregorianCalendar) currentDay.clone(), new DailyData((float)Math.random()*50, (float)Math.random()*50));
            currentDay.add(GregorianCalendar.DATE, 1);
        }

    }

    private static String format(GregorianCalendar cal){
        return fmt.format(cal.getTime());
    }

}
