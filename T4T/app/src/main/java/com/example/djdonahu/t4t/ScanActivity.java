package com.example.djdonahu.t4t;

import android.content.Intent;
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
import android.os.Build;
import android.widget.TextView;

import com.google.zxing.integration.android.*;


public class ScanActivity extends ActionBarActivity {

    private TextView scanResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        Intent intent = getIntent();
        boolean shouldScan = intent.getBooleanExtra("startScanning", false);
        intent.putExtra("startScanning", false);

        // Don't accidentally get stuck in a scanning loop, only scan on first creation
        if (shouldScan) {
            Log.d("T4T", "Launching scan in create");
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.initiateScan();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        scanResults = (TextView) findViewById(R.id.scanResult);
    }

    // Returning from scan app
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d("T4T", "Scan activity result");
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            // handle scan result
            if (scanResults != null) {
                scanResults.setText(scanResult.toString());
            }
        }
    }

    //
    // Buttons
    //

    // Scan again
    public void scanAgain (View v) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scan, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_scan, container, false);
            return rootView;
        }
    }
}
