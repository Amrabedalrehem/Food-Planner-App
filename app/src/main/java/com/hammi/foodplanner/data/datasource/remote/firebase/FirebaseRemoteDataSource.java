package com.hammi.foodplanner.data.datasource.remote.firebase;
import android.content.Context;
import android.util.Log;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.hammi.foodplanner.data.datasource.local.SharedPrefsManager.SharedPrefsDataSource;
import com.hammi.foodplanner.data.models.local.FavoriteEntity;
import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.data.models.local.MealPlanEntity;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;


public class FirebaseRemoteDataSource {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private SharedPrefsDataSource prefDataSource;
    public FirebaseRemoteDataSource(Context context){
        prefDataSource = new SharedPrefsDataSource(context);
    }

    public Completable removeFromFavorites(FavoriteEntity favorite) {
        return Completable.create(emitter -> {
            db.collection("users")
                    .document(prefDataSource.getUserId())
                    .collection("favourites")
                    .document(favorite.getMealId())
                    .delete()
                    .addOnSuccessListener(unused -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }


    public Completable addToFavorites(FavoriteEntity favorite) {
        return Completable.create(emitter -> {
            db.collection("users")
                    .document(prefDataSource.getUserId())
                    .collection("favourites")
                    .document(favorite.getMealId())
                    .set(favorite)
                    .addOnSuccessListener(unused -> {
                        Log.d("TAG", "Favorite added: " + favorite.getMealId());
                        emitter.onComplete();
                    })
                    .addOnFailureListener(emitter::onError);

        });
    }

    public Single<List<FavoriteEntity>> getAllFavorites() {
        return Single.create(emitter -> {
            List<FavoriteEntity> favourites = new ArrayList<>();
            db.collection("users")
                    .document(prefDataSource.getUserId())
                    .collection("favourites")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                favourites.add(document.toObject(FavoriteEntity.class));}
                            emitter.onSuccess(favourites);
                        } else {
                            emitter.onError(task.getException());
                        }
                    });});
    }

    public Single<List<MealEntity>> getAllMeals() {
        return Single.create(emitter -> {

            List<MealEntity> meals = new ArrayList<>();

            db.collection("users")
                    .document(prefDataSource.getUserId())
                    .collection("meals")
                    .get()
                    .addOnSuccessListener(query -> {
                        for (QueryDocumentSnapshot doc : query) {
                            meals.add(doc.toObject(MealEntity.class));
                        }
                        emitter.onSuccess(meals);
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }



    public Completable removeFromWeeklyPlan(MealPlanEntity mealPlan){
        return Completable.create(emitter -> {

            db.collection("users")
                    .document(prefDataSource.getUserId())
                    .collection("weekly_plan")
                    .document(mealPlan.getMealId())
                    .delete()
                    .addOnSuccessListener(unused -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);

        });
    }

    public Completable addToWeeklyPlan(MealPlanEntity mealPlan) {
        return Completable.create(emitter -> {
            db.collection("users")
                    .document(prefDataSource.getUserId())
                    .collection("weekly_plan")
                    .document(mealPlan.getMealId())
                    .set(mealPlan)
                    .addOnSuccessListener(unused -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Single<List<MealPlanEntity>> getAllMealPlans() {
        return Single.create(emitter -> {
            List<MealPlanEntity> mealPlans = new ArrayList<>();
            db.collection("users")
                    .document(prefDataSource.getUserId())
                    .collection("weekly_plan")
                    .get()
                    .addOnSuccessListener(query -> {
                        for (QueryDocumentSnapshot doc : query) {
                            mealPlans.add(doc.toObject(MealPlanEntity.class));
                        }
                        emitter.onSuccess(mealPlans);
                    })
                    .addOnFailureListener(emitter::onError);

        });
    }

    public Completable addToMeal(MealEntity meal) {
        return Completable.create(emitter -> {
            db.collection("users")
                    .document(prefDataSource.getUserId())
                    .collection("meals")
                    .document(meal.getMealId())
                    .set(meal)
                    .addOnSuccessListener(unused -> {
                        Log.d("TAG", "Meal added: " + meal.getMealId());
                        emitter.onComplete();
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }



}
