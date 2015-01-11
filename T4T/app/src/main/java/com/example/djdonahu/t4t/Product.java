package com.example.djdonahu.t4t;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {

    @SerializedName("name")
    public String product_name;

    @SerializedName("packaging_material")
    public String packaging_material;

    @SerializedName("attributes")
    public HashMap<String,String> attributes;

    @SerializedName("packaging_size")
    public String packaging_size;

    @SerializedName("contents_material")
    public String contents_material;

    @SerializedName("contents_size")
    public String contents_size;

    @SerializedName("barcode")
    public String upc;

    @SerializedName("images")
    public ArrayList<String> images;

    public void initialize() {
        packaging_material = attributes.get("packaging_material");
        packaging_size = attributes.get("packaging_size");
        contents_material = attributes.get("contents_material");
        contents_size = attributes.get("contents_size");
    }

    public boolean packageRecyclable(){
        if(packaging_material == null){ return false; };

        if(packaging_material.equals("plastic_1")) {
            return true;
        }
        else if(packaging_material.equals("plastic_2")) {
            return true;
        }
        else if(packaging_material.equals("plastic_3")) {
            return true;
        }
        else if(packaging_material.equals("plastic_4")) {
            return true;
        }
        else if(packaging_material.equals("plastic_5")) {
            return true;
        }
        else if(packaging_material.equals("plastic_6")) {
            return false;
        }
        else if(packaging_material.equals("plastic_7")) {
            return true;
        }
        else if(packaging_material.equals("paperboard")) {
            return true;
        }
        else if(packaging_material.equals("aluminum")) {
            return true;
        }
        else if(packaging_material.equals("soiled_paper")) {
            return false;
        }
        else if(packaging_material.equals("soiled_cardboard")) {
            return false;
        }
        else if(packaging_material.equals("glass")) {
            return true;
        }
        else if(packaging_material.equals("styrofoam")) {
            return false;
        }
        else if(packaging_material.equals("plastic")) {
            return false;
        }
        return false;
    }

    public boolean contentsRecyclable(){
        if(packaging_material == null){ return false; };

        if(packaging_material.equals("plastic_1")) {
            return true;
        }
        else if(packaging_material.equals("plastic_2")) {
            return true;
        }
        else if(packaging_material.equals("plastic_3")) {
            return true;
        }
        else if(packaging_material.equals("plastic_4")) {
            return true;
        }
        else if(packaging_material.equals("plastic_5")) {
            return true;
        }
        else if(packaging_material.equals("plastic_6")) {
            return false;
        }
        else if(packaging_material.equals("plastic_7")) {
            return true;
        }
        else if(packaging_material.equals("paperboard")) {
            return true;
        }
        else if(packaging_material.equals("aluminum")) {
            return true;
        }
        else if(packaging_material.equals("soiled_paper")) {
            return false;
        }
        else if(packaging_material.equals("soiled_cardboard")) {
            return false;
        }
        else if(packaging_material.equals("glass")) {
            return true;
        }
        else if(packaging_material.equals("styrofoam")) {
            return false;
        }
        else if(packaging_material.equals("plastic")) {
            return false;
        }
        return false;
    }

    @Override
    public String toString() {
        return product_name;
    }
}
