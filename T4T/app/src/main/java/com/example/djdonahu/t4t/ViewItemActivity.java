package com.example.djdonahu.t4t;

import android.content.Context;
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
import android.content.Intent;
import android.os.Build;
import android.widget.TextView;


public class ViewItemActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String UPC = intent.getStringExtra("UPC");

        //Product product = null;
        Log.e(UPC,UPC);
        Product product = SavedPreferences.getProduct(UPC);
        if(product == null) return;

        TextView UPCView = (TextView) findViewById(R.id.view_UPC);
        TextView nameView = (TextView) findViewById(R.id.view_product_name);
        TextView packageRecycleableView = (TextView) findViewById(R.id.view_package_recycleable);
        TextView contentsRecycleableView = (TextView) findViewById(R.id.view_contents_recycleable);
        TextView packageMaterialView = (TextView) findViewById(R.id.view_package_material);
        TextView contentsMaterialView = (TextView) findViewById(R.id.view_contents_material);

        Log.e("Product", product.toString());

        UPCView.setText(UPC);
        packageRecycleableView.setText((product.packageRecyclable() ? "Yes" : "Nope"));
        contentsRecycleableView.setText((product.contentsRecyclable()  ? "Yes" : "Nope"));
        packageMaterialView.setText(product.packaging_material);
        contentsMaterialView.setText(product.contents_material);
        nameView.setText(product.product_name);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_item, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_view_item, container, false);
            return rootView;
        }
    }
}
