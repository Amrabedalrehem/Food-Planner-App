package com.hammi.foodplanner.data.models.local;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "favorites", foreignKeys = @ForeignKey(entity = MealEntity.class, parentColumns = "mealId", childColumns = "mealId",
                onDelete = CASCADE
        ),
        indices = {@Index("mealId")}
)
public class FavoriteEntity {

    @PrimaryKey
    @NonNull
    private String mealId;
    private long timestamp;

     public FavoriteEntity() {}

     public FavoriteEntity(@NonNull String mealId, long timestamp) {
        this.mealId = mealId;
        this.timestamp = timestamp;
    }

     @NonNull
    public String getMealId() {
        return mealId;
    }
    public void setMealId(@NonNull String mealId) {
        this.mealId = mealId;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}