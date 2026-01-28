package com.hammi.foodplanner.data.datasource.remote.home.ingredients;

import com.hammi.foodplanner.data.models.remote.Ingredients;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientsResponse {

    @SerializedName("meals")
    private List<Ingredients>ingredients;

    public IngredientsResponse(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public  IngredientsResponse()
    {

    }
    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }
}

