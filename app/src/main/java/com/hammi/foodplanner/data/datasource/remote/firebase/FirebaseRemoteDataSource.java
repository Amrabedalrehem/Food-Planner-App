package com.hammi.foodplanner.data.datasource.remote.firebase;
import android.content.Context;
 import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.hammi.foodplanner.data.datasource.local.SharedPrefsManager.SharedPrefsDataSource;
import com.hammi.foodplanner.data.models.local.FavoriteEntity;
 import com.hammi.foodplanner.data.models.local.MealPlanEntity;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirebaseRemoteDataSource {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final SharedPrefsDataSource prefDataSource;

    public FirebaseRemoteDataSource(Context context) {
        prefDataSource = new SharedPrefsDataSource(context);
    }

     private Single<String> getUserId() {
        return prefDataSource.getUserId();
    }

    public Completable addToFavorites(FavoriteEntity favorite) {
        return getUserId().flatMapCompletable(userId -> Completable.create(emitter -> {
            db.collection("users")
                    .document(userId)
                    .collection("favourites")
                    .document(favorite.getMealId())
                    .set(favorite)
                    .addOnSuccessListener(unused -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        }));
    }

    public Completable removeFromFavorites(FavoriteEntity favorite) {
        return getUserId().flatMapCompletable(userId -> Completable.create(emitter -> {
            db.collection("users")
                    .document(userId)
                    .collection("favourites")
                    .document(favorite.getMealId())
                    .delete()
                    .addOnSuccessListener(unused -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        }));
    }

    public Single<List<FavoriteEntity>> getAllFavorites() {
        return getUserId().flatMap(userId -> Single.create(emitter -> {
            db.collection("users")
                    .document(userId)
                    .collection("favourites")
                    .get()
                    .addOnSuccessListener(query -> {
                        List<FavoriteEntity> favourites = new ArrayList<>();
                        for (QueryDocumentSnapshot document : query) {
                            favourites.add(document.toObject(FavoriteEntity.class));
                        }
                        emitter.onSuccess(favourites);
                    })
                    .addOnFailureListener(emitter::onError);
        }));
    }

    public Completable addToWeeklyPlan(MealPlanEntity mealPlan) {
        return getUserId().flatMapCompletable(userId -> Completable.create(emitter -> {
            db.collection("users")
                    .document(userId)
                    .collection("weekly_plan")
                    .document(mealPlan.getMealId())
                    .set(mealPlan)
                    .addOnSuccessListener(unused -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        }));
    }

    public Single<List<MealPlanEntity>> getAllMealPlans() {
        return getUserId().flatMap(userId -> Single.create(emitter -> {
            db.collection("users")
                    .document(userId)
                    .collection("weekly_plan")
                    .get()
                    .addOnSuccessListener(query -> {
                        List<MealPlanEntity> mealPlans = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : query) {
                            mealPlans.add(doc.toObject(MealPlanEntity.class));
                        }
                        emitter.onSuccess(mealPlans);
                    })
                    .addOnFailureListener(emitter::onError);
        }));
    }

    public Completable removeFromWeeklyPlan(MealPlanEntity mealPlan) {
        return getUserId().flatMapCompletable(userId -> Completable.create(emitter -> {
            db.collection("users")
                    .document(userId)
                    .collection("weekly_plan")
                    .document(mealPlan.getMealId())
                    .delete()
                    .addOnSuccessListener(unused -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        }));
    }
}