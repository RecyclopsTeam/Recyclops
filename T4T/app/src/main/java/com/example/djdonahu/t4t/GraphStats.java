package com.example.djdonahu.t4t;

/**
 * Created by Jake on 1/11/15.
 */
public class GraphStats{
    public String[] labels;
    public float[] XValues;
    public float[] recyclingSums;
    public float[] totalSums;

    public GraphStats(int numDays){
        labels = new String[numDays];
        XValues = new float[numDays];
        recyclingSums = new float[numDays];
        totalSums = new float[numDays];
    }
}
