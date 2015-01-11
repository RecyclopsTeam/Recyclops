package com.example.djdonahu.t4t;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseObject;

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
        StatTracker.getInstance(getApplicationContext());

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "QIMMVQrjWNYfbCNfoXEsIU8w3Dyw1E0FlwBtnL7v", "X4PBoUvAeNYkzW1Ihh4nUYLO0tud8zD1j0qRQQOV");
    }

    public static Context getContext() {
        return c;
    }
}

