package com.example.foodplanner.data.models;

public  class IngredientItem {
    public String name;
    public String measure;
    public String imageUrl;

    public IngredientItem(String name, String measure, String imageUrl) {
        this.name = name;
        this.measure = measure;
        this.imageUrl = imageUrl;
    }
}