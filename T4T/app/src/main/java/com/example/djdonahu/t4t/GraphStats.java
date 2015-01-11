package com.example.djdonahu.t4t;

import com.jjoe64.graphview.series.DataPoint;

/**
 * Created by Jake on 1/11/15.
 */
public class GraphStats{
    public String[] labels;
    public float[] XValues;
    public DataPoint[] recyclingData;
    public DataPoint[] totalData;

    public GraphStats(int numDays){
        labels = new String[numDays];
        XValues = new float[numDays];
        recyclingData = new DataPoint[numDays];
        totalData = new DataPoint[numDays];
    }
}
