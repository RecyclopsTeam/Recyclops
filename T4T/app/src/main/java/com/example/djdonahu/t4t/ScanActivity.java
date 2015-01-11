package com.example.djdonahu.t4t;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.integration.android.*;
import com.squareup.picasso.Picasso;

import android.widget.Button;
import android.widget.Toast;


public class ScanActivity extends ActionBarActivity {

    // This is important. Don't delete it or the class will break! :-)
    private boolean hasLoaded = false;
    private String lastBarcode;
    private static String SCAN_TAG = "T4T_SCAN";

    private TextView scanResults;
    // initialize View variables (TextViews, ListViews, Buttons, etc)
    private Button purchasedButton;
    private Button nopeButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_scan);
        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LoaderFragment())
                    .commit();
        }*/
        setContentView(R.layout.scan_loading);
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

    public void onCancelClick(View v)
    {
        OutpanRequest.cancelRequest();
        finish();

    }

    public void onPause()
    {
        super.onPause();
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putString("lastBarcode", lastBarcode);
        editor.commit();

    }

    private void loadProductView(Product product)
    {
        if (product == null) return;
        Context context = getApplicationContext();
        setContentView(R.layout.fragment_scan);

        TextView title = (TextView) findViewById(R.id.item_name);
        title.setText((CharSequence) product.product_name);

        //point our View variables to the id that they correspond to
        purchasedButton = (Button) findViewById(R.id.purchased_button);
        nopeButton = (Button) findViewById(R.id.nope_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);

        ImageView item_image = (ImageView) findViewById( R.id.item_image );
        //String fullThumbUrl = Settings.thumbUrlRoot + "/" + item.thumb_url;
        if ( product.images.size() > 0) {
            String url = product.images.get(0);
            Picasso.with(context).load(url).into(item_image);

        }

        //define the behaviors for when each button is clicked.
        purchasedButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Toast toast = Toast.makeText(getApplicationContext(), "Item stored, returning to scanner", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        nopeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Toast toast = Toast.makeText(getApplicationContext(), "Item discarded, returning to scanner", Toast.LENGTH_SHORT);
                toast.show();
                scanAgain(v);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ScanActivity.this, StartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getPreferences( MODE_PRIVATE );
        String storedBarcode = prefs.getString( "lastBarcode", null );

        setContentView(R.layout.scan_loading);

        // If we have a stored barcode, do a search on it. Otherwise, end the activity.
       // if ( storedBarcode != null )
        //    doSearch(storedBarcode);
       // else
        //    finish();
//

    }

    private void doSearch(String upc)
    {
        // To the internet!
        OutpanRequest.getProduct(
            upc,
            new FetchUrlCallback() {

                @Override
                public void execute(Object result) {
                    Product p = OutpanRequest.castProduct(result);
                    if (p != null) {
                        if ( p.error.containsKey("code"))
                        {
                            String code = p.error.get("code");
                            setContentView(R.layout.scan_error);
                            TextView error_text = (TextView) findViewById(R.id.scan_error_text);

                            CharSequence error_msg = "There was a problem scanning your item.";
                            if ( code == "210" )
                            {
                                error_msg = "A barcode is required.";

                            }
                            else if ( code == "120" )
                            {
                                error_msg = "There was a problem accessing the item database.";

                            }
                            else if ( code == "420" )
                            {
                                error_msg = "Something went horribly wrong. I shouldn't even be here today.";

                            }

                            error_text.setText(error_msg);

                        }
                        else
                        {
                            loadProductView(p);
                            Log.d(SCAN_TAG, p.toString());

                        }
                    }
                }
            }
        );
    }

    // Returning from scan app
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d("T4T", "Scan activity result");
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null && scanResult.getContents() != null) {
            // handle scan result
            String format = scanResult.getFormatName();
            if (!format.equals("UPC_A")) {
                Log.w(SCAN_TAG, "Possibly unsupported UPC type " + format);
            }
            String upc = scanResult.getContents();
            Log.d(SCAN_TAG, "UPC: " + upc);

            doSearch(upc);

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
