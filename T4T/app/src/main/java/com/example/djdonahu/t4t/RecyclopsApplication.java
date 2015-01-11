package com.example.djdonahu.t4t;

import android.app.Application;
import android.content.Context;

public class RecyclopsApplication extends Application {

    private static Context c;

    @Override
    public void onCreate() {
        // Application level onCreate method, called before any activity initialization
        super.onCreate();

        // Save the application context for passing later
        c = getApplicationContext();
        // Initialize shared prefs with application context
        SavedPreferences.getInstance(getApplicationContext());
    }

    public static Context getContext() {
        return c;
    }
}

