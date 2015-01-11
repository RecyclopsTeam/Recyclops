package com.example.djdonahu.t4t;

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
import android.widget.ListView;
import android.widget.*;
import java.util.ArrayList;
import java.util.Map;
import android.os.Build;


public class ReferenceActivity extends ActionBarActivity {

    ListAdapter productAdapter;
    ListView productlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        SavedPreferences.getInstance(this);
        generatePhonyList();

    }

    @Override
    protected void onResume(){
        super.onResume();

        ArrayList<ProductListItem> productItems = generateListFromSavedData();
        productAdapter = new ProductListAdapter(this,
                R.layout.product_item, R.id.product_name, productItems, this);

        productlistView = (ListView) findViewById(R.id.reference_listView);
        productlistView.setAdapter(productAdapter);
    }

    private void generatePhonyList(){
        String[] UPCs = {"0078000082401", "0000001215908", "0049000028904", "0071641010697"};
        for(int i=0;i<UPCs.length;i++) {
            String UPC = UPCs[i];
            OutpanRequest.getProduct(
                    UPC,
                    new FetchUrlCallback() {
                        @Override
                        public void execute(Object result) {
                            Product p = ScanActivity.getProduct(result);
                            if (p != null) {
                                //Log.d(SCAN_TAG, p.toString());
                                SavedPreferences.addProduct(p);
                            }
                        }
                    }
            );
        }
    }

    private ArrayList<ProductListItem> generateListFromSavedData(){
        ArrayList<ProductListItem> result = new ArrayList<ProductListItem>();
        Map<String, Product> savedProducts = SavedPreferences.getSavedProductList();
        for(Product value : savedProducts.values()){
            result.add(new ProductListItem(value));
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reference, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_reference, container, false);
            return rootView;
        }
    }
}
