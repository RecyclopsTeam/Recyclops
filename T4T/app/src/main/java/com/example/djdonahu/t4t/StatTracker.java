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
    private static SimpleDateFormat label_fmt = new SimpleDateFormat("mm/dd");


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
            if (tracker_data == null || tracker_data.tracker_data == null) {
                tracker_data = new Tracker_Map();
                generatePhonyData(tracker_data);
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
        getInstance().tracker_data.tracker_data.get(day);
        //getInstance().commitToPrefs();
    }

    public static void addStats(float trash, float recycling){
        GregorianCalendar currentDate = new GregorianCalendar();
        addStats(currentDate, trash, recycling);
    }

    public static GraphStats getGraphData(GregorianCalendar start, GregorianCalendar end){
        long diff = start.getTimeInMillis() - end.getTimeInMillis();
        int numDays = (int) (diff / (24 * 60 * 60 * 1000)) + 2;

        Log.d("Mem", "size: "+numDays);

        HashMap<String, DailyData> data = getInstance().tracker_data.tracker_data;

        GraphStats gs = new GraphStats(numDays);
        GregorianCalendar currentDay = start;//(GregorianCalendar) start.clone();
        for(int i=0; i < numDays; ++i){
            gs.XValues[i] = i;
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
        aMonthAgo.add(GregorianCalendar.DATE, -10);
        return getGraphData(today, aMonthAgo);
    }

    private void generatePhonyData(Tracker_Map tracker_map){
        GregorianCalendar today = new GregorianCalendar();
        GregorianCalendar aMonthAgo = ((GregorianCalendar) today.clone());
        aMonthAgo.add(GregorianCalendar.DATE, -10);
        long diff = today.getTimeInMillis() - aMonthAgo.getTimeInMillis();
        int numDays = (int) (diff / (24 * 60 * 60 * 1000)) + 2;

        HashMap<String, DailyData> data = this.tracker_data.tracker_data;

        GraphStats gs = new GraphStats(numDays);
        GregorianCalendar currentDay = today;
        for(int i=0; i < numDays; ++i){
            this.tracker_data.tracker_data.put(format(currentDay), new DailyData((float)Math.random()*20, (float)Math.random()*20));
            //tracker_map.tracker_data.put((GregorianCalendar) currentDay.clone(), new DailyData((float)Math.random()*50, (float)Math.random()*50));
            currentDay.add(GregorianCalendar.DATE, 1);
        }

    }

    private static String format(GregorianCalendar cal){
        return fmt.format(cal.getTime());
    }

}
