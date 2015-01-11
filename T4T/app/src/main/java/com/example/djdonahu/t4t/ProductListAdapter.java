package com.example.djdonahu.t4t;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Jake on 1/10/15.
 */
public class ProductListAdapter extends ArrayAdapter<ProductListItem>{

    Activity currentActivity; //Used to create onClick callback

    ProductListAdapter(Context context, int resource, Activity currentActivity){
        super(context, resource);
        this.currentActivity = currentActivity;
    }

    ProductListAdapter(Context context, int resource, ProductListItem[] productNames, Activity currentActivity){
        super(context, resource, productNames);
        this.currentActivity = currentActivity;
    }

    ProductListAdapter(Context context, int resource, int textViewResourceId, ProductListItem[] productNames, Activity currentActivity){
        super(context, resource, textViewResourceId,productNames);
        this.currentActivity = currentActivity;
    }

    ProductListAdapter(Context context, int resource, int textViewResourceId, ArrayList<ProductListItem> productNames, Activity currentActivity){
        super(context, resource, textViewResourceId,productNames);
        this.currentActivity = currentActivity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.product_item, null);
        }

        final ProductListItem product = this.getItem(position);

        if (product != null) {
            final TextView productName = (TextView) v.findViewById(R.id.product_name);
            if (productName != null) {
                productName.setText(product.name);
            }

            final Button productButton = (Button) v.findViewById(R.id.product_button);
            if (productButton != null){
                productButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(currentActivity, ViewItemActivity.class);
                        intent.putExtra("UPC", product.UPC);
                        currentActivity.startActivity(intent);
                    }
                });
            }
        }
        return v;
    }
}
