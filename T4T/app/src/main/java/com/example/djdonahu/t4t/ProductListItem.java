package com.example.djdonahu.t4t;

import android.widget.TextView;

/**
 * Created by Jake on 1/10/15.
 */
public class ProductListItem {
    public String name;
    public String UPC;

    ProductListItem(String name, String UPC) {
        this.name = name;
        this.UPC = UPC;
    }

    ProductListItem(String name) {
        this.name = name;
        this.UPC = "NO UPC FOUND";
    }

    ProductListItem(Product p){
        this.name = p.product_name;
        this.UPC = p.upc;
    }
}
