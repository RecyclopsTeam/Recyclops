package com.example.djdonahu.t4t;


import com.google.gson.annotations.SerializedName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {

    @SerializedName("name")
    public String product_name;

    @SerializedName("packaging_material")
    public String packaging_material = "N/A";

    @SerializedName("attributes")
    public HashMap<String,String> attributes;

    @SerializedName("packaging_size")
    public String packaging_size = "No material size found";

    @SerializedName("contents_material")
    public String contents_material = "N/A";

    @SerializedName("contents_size")
    public String contents_size = "No material size found";

    @SerializedName("error")
    public HashMap<String,String> error;

    @SerializedName("barcode")
    public String upc;

    @SerializedName("images")
    public ArrayList<String> images;

    public String image_url;

    public Product()
    {

    }

    public Product(ParseObject p)
    {
        product_name = p.getString("product_name");
        packaging_material = p.getString("packaging_material");
        packaging_size = p.getString("packaging_size");
        contents_material = p.getString("contents_material");
        contents_size = p.getString("contents_size");
        upc = p.getString("upc");
        image_url = p.getString("image_url");

        // grab ParseImage object as well

    }

    public Product initialize() {
        if (error != null && error.containsKey("code")) {
            // Error encountered, bail! We'll handle this later.
            return this;
        }
        // Return an error
        if (product_name == null) {
            return errorProductNotFound();
        }
        packaging_material = attributes.get("packaging_material");
        packaging_size = attributes.get("packaging_size");
        if (packaging_material == null || packaging_size == null) {
            return errorProductNotFound();
        }
        contents_material = attributes.get("contents_material");
        contents_size = attributes.get("contents_size");
        return this;
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

    private static Product errorProductNotFound() {
        Product p = new Product();
        p.error = new HashMap<String, String>();
        p.error.put("code", "T4T_NOT_FOUND");
        return p;
    }

    @Override
    public String toString() {
        return product_name+", pack: "+packaging_material+", size: "+packaging_size;
    }

    public boolean hasPackaging(){
        return (packaging_material != "N/A") && (packaging_size != "No material size found");
    }

    public boolean hasContents(){
        return (contents_material != "N/A") && (contents_size != "No material size found");
    }
}
