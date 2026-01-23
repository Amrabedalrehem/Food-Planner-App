package com.example.foodplanner.data.models;


import com.google.gson.annotations.SerializedName;

public class Ingredients {
    @SerializedName("idIngredient")
    private String idIngredient;

    @SerializedName("strIngredient")
    private String strIngredient;

    @SerializedName("strDescription")
    private String strDescription;

    @SerializedName("strType")
    private String strType;

     private String strThumb;

    public Ingredients() { }

    public Ingredients(String idIngredient, String strIngredient, String strDescription, String strType, String strThumb) {
        this.idIngredient = idIngredient;
        this.strIngredient = strIngredient;
        this.strDescription = strDescription;
        this.strType = strType;
        this.strThumb = strThumb;
    }

    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(String idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }

    public String getStrThumb() {
        return "https://www.themealdb.com/images/ingredients/" + strIngredient.replace(" ", "%20") + ".png";
    }
    public void setStrThumb(String strThumb) {
        this.strThumb = strThumb;
    }
}