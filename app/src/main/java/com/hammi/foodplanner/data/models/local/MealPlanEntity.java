package com.hammi.foodplanner.data.models.local;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import static androidx.room.ForeignKey.CASCADE;
@Entity(
        tableName = "meal_plan",
        foreignKeys = @ForeignKey(
                entity = MealEntity.class,
                parentColumns = "mealId",
                childColumns = "mealId",
                onDelete = CASCADE
        ),
        indices = {@Index("mealId"), @Index("plannedDate")}
)
public class MealPlanEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String mealId;
    private long plannedDate;
     public MealPlanEntity() {}

    public MealPlanEntity(@NonNull String mealId, long plannedDate) {
        this.mealId = mealId;
        this.plannedDate = plannedDate;
    }

     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getMealId() {
        return mealId;
    }

    public void setMealId(@NonNull String mealId) {
        this.mealId = mealId;
    }

    public long getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(long plannedDate) {
        this.plannedDate = plannedDate;
    }
}