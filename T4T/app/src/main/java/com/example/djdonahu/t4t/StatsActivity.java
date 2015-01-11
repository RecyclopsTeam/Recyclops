package com.example.djdonahu.t4t;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.jjoe64.graphview.*;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;

import android.os.Build;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class StatsActivity extends ActionBarActivity {

    public Map<Date,Integer> dayWasteTotal = new HashMap<Date,Integer>();
    public Map<Date,Integer> dayRecycleTotal = new HashMap<Date, Integer>();

    public GraphStats graphStats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dayWasteTotal.put(RandomDay(2014,11,6),23);
        dayWasteTotal.put(RandomDay(2014,11,7),54);
        dayWasteTotal.put(RandomDay(2014,11,8),61);

        dayWasteTotal.put(RandomDay(2014,11,9),42);
        dayWasteTotal.put(RandomDay(2014,11,10),68);
        dayWasteTotal.put(RandomDay(2014,11,11),53);

        dayWasteTotal.put(RandomDay(2014,11,9),42);
        dayWasteTotal.put(RandomDay(2014,11,10),68);
        dayWasteTotal.put(RandomDay(2014,11,11),53);

        dayWasteTotal.put(RandomDay(2014,11,9),42);
        dayWasteTotal.put(RandomDay(2014,11,10),68);
        dayWasteTotal.put(RandomDay(2014,11,11),53);

        dayWasteTotal.put(RandomDay(2014,11,9),42);
        dayWasteTotal.put(RandomDay(2014,11,10),68);
        dayWasteTotal.put(RandomDay(2014,11,11),53);

        dayRecycleTotal.put(RandomDay(2014,12,4),37);
        dayRecycleTotal.put(RandomDay(2014,12,16),33);
        dayRecycleTotal.put(RandomDay(2014,12,25),48);

        dayRecycleTotal.put(RandomDay(2014,11,6),6);
        dayRecycleTotal.put(RandomDay(2014,11,17),24);
        dayRecycleTotal.put(RandomDay(2014,11,28),21);

        setContentView(R.layout.activity_stats);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        Log.e("HMM", "good herE?!!!");
        StatTracker.getInstance(this);
        graphStats = StatTracker.getGraphData();

        Log.e("w", "w");
    }

    public DataPoint[] returnInfo(Map name){
        DataPoint[] bar = null;
        ArrayList<DataPoint> tmpList = new ArrayList<DataPoint>();
        int i = 0;
        for(Map.Entry<Date,Integer> entry: dayWasteTotal.entrySet()){

            Date day = entry.getKey();
            int value = entry.getValue();
            DataPoint dataPoint = new DataPoint(i,value);
            Log.e("POINT", dataPoint.toString());
            tmpList.add(dataPoint);
            bar = tmpList.toArray(new DataPoint[tmpList.size()]);
            i++;
        }
        return bar;
    }

    @Override
    protected void onResume(){
        super.onResume();
        GraphView graph = (GraphView) findViewById(R.id.graph);

        //DataPoint[] wasteData = returnInfo(dayWasteTotal);
        //DataPoint[] recycleData = returnInfo(dayRecycleTotal);

        //Log.e("ARRAY", wasteData[0].toString());

        BarGraphSeries<DataPoint> trash = new BarGraphSeries<DataPoint>(graphStats.totalData);
        BarGraphSeries<DataPoint> recycling = new BarGraphSeries<DataPoint>(graphStats.recyclingData);

        //StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);

        //staticLabelsFormatter.setHorizontalLabels(graphStats.labels);
        //graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return graphStats.labels[(int)Math.round(value)];
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX);
                }
            }
        });


        trash.setSpacing(10);
        recycling.setSpacing(10);
        graph.addSeries(trash);
        trash.setColor(Color.BLUE);


        graph.addSeries(recycling);
        recycling.setColor(Color.YELLOW);


        trash.setTitle("Trash");
        recycling.setTitle("Recycling");
        graph.setTitle("Stats for past 30 days");
        graph.setMinimumHeight(0);
        graph.getViewport().setScrollable(true);
        //graph.setHorizontalScrollBarEnabled(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(10);
        graph.getViewport().scrollToEnd();
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stats, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_stats, container, false);
            return rootView;
        }
    }
    public static Date RandomDay(int year,int month,int day){
        Calendar newCalendar = new GregorianCalendar(year,month,day);
        return newCalendar.getTime();
    }
}
