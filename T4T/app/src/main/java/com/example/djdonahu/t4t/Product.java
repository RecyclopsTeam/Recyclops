package com.example.djdonahu.t4t;


import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("name")
    public String product_name;

    @SerializedName("packaging_material")
    public String packaging_material;

    @SerializedName("packaging_size")
    public String packaging_size;

    @SerializedName("contents_material")
    public String contents_material;

    @SerializedName("contents_size")
    public String contents_size;

    @SerializedName("barcode")
    public String upc;
}
