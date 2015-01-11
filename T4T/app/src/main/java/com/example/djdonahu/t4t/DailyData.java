package com.example.djdonahu.t4t;

/**
 * Created by Jake on 1/11/15.
 */
public class DailyData{
    public float recyling_total = 0;
    public float trash_total = 0;


    public DailyData(float trash, float recycling) {
        this.recyling_total = recycling;
        this.trash_total = trash;
    }
}