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

    public boolean packageRecyclable(){
        switch (packaging_material) {
            case "plastic_1": return true;
            case "plastic_2": return true;
            case "plastic_3": return true;
            case "plastic_4": return true;
            case "plastic_5": return true;
            case "plastic_6": return false;
            case "plastic_7": return true;
            case "paperboard": return true;
            case "aluminum": return true;
            case "soiled_paper": return false;
            case "soiled_cardboard": return false;
            case "glass": return true;
            case "styrofoam": return false;
            case "plastic": return false;
        }
        return false;
    }

    public boolean contentsRecyclable(){
        switch (contents_material) {
            case "plastic_1": return true;
            case "plastic_2": return true;
            case "plastic_3": return true;
            case "plastic_4": return true;
            case "plastic_5": return true;
            case "plastic_6": return false;
            case "plastic_7": return true;
            case "paperboard": return true;
            case "aluminum": return true;
            case "soiled_paper": return false;
            case "soiled_cardboard": return false;
            case "glass": return true;
            case "styrofoam": return false;
            case "plastic": return false;
        }
        return false;
    }
}
