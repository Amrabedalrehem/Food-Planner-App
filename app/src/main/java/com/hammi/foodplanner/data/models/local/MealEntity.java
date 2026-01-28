package com.hammi.foodplanner.data.models.local;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "meals")
public class MealEntity  {

    @PrimaryKey
    @NonNull
    private String mealId;
    private String name;
    private String category;
    private String area;
    private String instructions;
    private String thumbnailUrl;
    private String youtubeUrl;
    private String ingredientsJson;

     public MealEntity () {}

      public MealEntity(@NonNull String mealId, String name, String category,
                      String area, String instructions, String thumbnailUrl,
                      String youtubeUrl, String ingredientsJson) {
        this.mealId = mealId;
        this.name = name;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
        this.thumbnailUrl = thumbnailUrl;
        this.youtubeUrl = youtubeUrl;
        this.ingredientsJson = ingredientsJson;
    }

     @NonNull
    public String getMealId() {
        return mealId;
    }

    public void setMealId(@NonNull String mealId) {
        this.mealId = mealId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getIngredientsJson() {
        return ingredientsJson;
    }

    public void setIngredientsJson(String ingredientsJson) {
        this.ingredientsJson = ingredientsJson;
    }
}
