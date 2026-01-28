package com.hammi.foodplanner.data.models.remote;

import java.util.ArrayList;
import java.util.List;
public class Meal {
    private String idMeal;
    private String strMeal;
    private String strCategory;
    private String strArea;
    private String strInstructions;
    private String strMealThumb;
    private String strYoutube;

     private String strIngredient1,
             strIngredient2,
             strIngredient3,
             strIngredient4,
             strIngredient5,
            strIngredient6,
             strIngredient7,
             strIngredient8,
             strIngredient9,
             strIngredient10,
            strIngredient11,
             strIngredient12,
             strIngredient13,
             strIngredient14,
             strIngredient15,
            strIngredient16,
             strIngredient17,
             strIngredient18,
             strIngredient19,
             strIngredient20;

    private String strMeasure1,
            strMeasure2,
            strMeasure3,
            strMeasure4,
            strMeasure5,
            strMeasure6,
            strMeasure7,
            strMeasure8,
            strMeasure9,
            strMeasure10,
            strMeasure11,
            strMeasure12,
            strMeasure13,
            strMeasure14,
            strMeasure15,
            strMeasure16,
            strMeasure17,
            strMeasure18,
            strMeasure19,
            strMeasure20;

     public List<IngredientItem> getFullIngredients() {
        List<IngredientItem> ingredients = new ArrayList<>();

         checkAndAdd(ingredients, strIngredient1, strMeasure1);
        checkAndAdd(ingredients, strIngredient2, strMeasure2);
        checkAndAdd(ingredients, strIngredient3, strMeasure3);
        checkAndAdd(ingredients, strIngredient4, strMeasure4);
        checkAndAdd(ingredients, strIngredient5, strMeasure5);
        checkAndAdd(ingredients, strIngredient6, strMeasure6);
        checkAndAdd(ingredients, strIngredient7, strMeasure7);
        checkAndAdd(ingredients, strIngredient8, strMeasure8);
        checkAndAdd(ingredients, strIngredient9, strMeasure9);
        checkAndAdd(ingredients, strIngredient10, strMeasure10);
        checkAndAdd(ingredients, strIngredient11, strMeasure11);
        checkAndAdd(ingredients, strIngredient12, strMeasure12);
        checkAndAdd(ingredients, strIngredient13, strMeasure13);
        checkAndAdd(ingredients, strIngredient14, strMeasure14);
        checkAndAdd(ingredients, strIngredient15, strMeasure15);
        checkAndAdd(ingredients, strIngredient16, strMeasure16);
        checkAndAdd(ingredients, strIngredient17, strMeasure17);
        checkAndAdd(ingredients, strIngredient18, strMeasure18);
        checkAndAdd(ingredients, strIngredient19, strMeasure19);
        checkAndAdd(ingredients, strIngredient20, strMeasure20);

        return ingredients;
    }

    private void checkAndAdd(List<IngredientItem> list, String name, String measure) {
        if (name != null && !name.trim().isEmpty()) {
            String imageUrl = "https://www.themealdb.com/images/ingredients/" + name.trim().replace(" ", "%20") + ".png";
            list.add(new IngredientItem(name.trim(), measure, imageUrl));
        }
    }
   public String getStrYoutube() { return strYoutube; }
    public String getStrArea() { return strArea; }
    public String getStrCategory() { return strCategory; }
    public String getIdMeal() { return idMeal; }
    public String getStrMeal() { return strMeal; }
    public String getStrInstructions() { return strInstructions; }
    public String getStrMealThumb() { return strMealThumb; }


}