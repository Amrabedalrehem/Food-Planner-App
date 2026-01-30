package com.hammi.foodplanner.data.repository.remote.auth;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.hammi.foodplanner.data.datasource.local.SharedPrefsManager.SharedPrefsDataSource;
 import com.hammi.foodplanner.data.datasource.local.favourite.FavoriteDao;
import com.hammi.foodplanner.data.datasource.local.mealplan.MealPlanDao;
import com.hammi.foodplanner.data.datasource.remote.auth.FirebaseAuthHelper;
import com.hammi.foodplanner.data.datasource.remote.firebase.FirebaseRemoteDataSource;
import com.hammi.foodplanner.data.models.local.FavoriteEntity;
import com.hammi.foodplanner.data.models.local.MealPlanEntity;
import com.google.firebase.auth.FirebaseUser;
import com.hammi.foodplanner.db.AppDatabase;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AuthRepository {

    private static final String TAG = "AuthRepository";

    private final FirebaseAuthHelper firebaseAuthHelper;
    private final SharedPrefsDataSource sharedPrefsDataSource;
    private final FirebaseRemoteDataSource firebaseRemoteDataSource;
    private final FavoriteDao favoriteDao;
    private final MealPlanDao mealPlanDao;

    public AuthRepository(Context context) {
        firebaseAuthHelper = FirebaseAuthHelper.getInstance(context);
        sharedPrefsDataSource = new SharedPrefsDataSource(context);
        firebaseRemoteDataSource = new FirebaseRemoteDataSource(context);

        AppDatabase database = AppDatabase.getInstance(context);
        favoriteDao = database.favoriteDao();
        mealPlanDao = database.mealPlanDao();
    }

    public Single<FirebaseUser> signInWithGoogle(Activity activity, String webClientId) {
        return firebaseAuthHelper.startGoogleSignIn(activity, webClientId)
                .flatMap(user -> saveSessionRx(user, "google")
                        .andThen(downloadDataAfterLogin())
                        .andThen(Single.just(user))
                );
    }

    public Single<FirebaseUser> registerWithEmail(String email, String password) {
        return firebaseAuthHelper.registerWithEmail(email, password)
                .flatMap(user -> saveSessionRx(user, "email")
                        .andThen(Single.just(user))
                );
    }

    public Single<FirebaseUser> loginWithEmail(String email, String password) {
        return firebaseAuthHelper.loginWithEmail(email, password)
                .flatMap(user -> saveSessionRx(user, "email")
                        .andThen(downloadDataAfterLogin())
                        .andThen(Single.just(user))
                );
    }

    private Completable saveSessionRx(FirebaseUser user, String mode) {
        return sharedPrefsDataSource.setLoggedIn(true)
                .andThen(sharedPrefsDataSource.setUserId(user.getUid()))
                .andThen(sharedPrefsDataSource.setUserMode(mode));
    }

    public Completable saveGuestSessionRx() {
        return sharedPrefsDataSource.setLoggedIn(false)
                .andThen(sharedPrefsDataSource.setUserMode("guest"));
    }

    public Completable signOut() {
        return uploadDataAndClearLocal()
                .andThen(firebaseAuthHelper.signOut())
                .andThen(sharedPrefsDataSource.clearAllData());
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuthHelper.getCurrentUser();
    }

     private Completable downloadDataAfterLogin() {
        return Completable.defer(() ->
                        firebaseRemoteDataSource.getAllFavorites()
                                .subscribeOn(Schedulers.io())
                                .flatMapCompletable(favorites ->
                                        favoriteDao.clearAllFavorites()
                                                .andThen(favoriteDao.insertAllFavorites(favorites))
                                                .subscribeOn(Schedulers.io())
                                                .doOnComplete(() -> Log.d(TAG, "ok " + favorites.size() ))
                                )
                                .andThen(
                                        firebaseRemoteDataSource.getAllMealPlans()
                                                .subscribeOn(Schedulers.io())
                                                .flatMapCompletable(mealPlans ->
                                                        mealPlanDao.clearAllPlans()
                                                                .andThen(mealPlanDao.addMealsToPlan(mealPlans))
                                                                .subscribeOn(Schedulers.io())
                                                                .doOnComplete(() -> Log.d(TAG, " "  + mealPlans.size()  ))
                                                )
                                )
                )
                .subscribeOn(Schedulers.io())
                .doOnError(error -> Log.e(TAG, " " + error.getMessage()));
    }

     private Completable uploadDataAndClearLocal() {
        return Completable.defer(() ->
                        Single.zip(
                                        favoriteDao.getAllFavoritesOnce().subscribeOn(Schedulers.io()),
                                        mealPlanDao.getAllMealPlansOnce().subscribeOn(Schedulers.io()),
                                        (favorites, mealPlans) -> {
                                            Log.d(TAG, "  " + favorites.size() + "   " + mealPlans.size() + "");
                                            return new DataToUpload(favorites, mealPlans);
                                        }
                                )
                                .subscribeOn(Schedulers.io())
                                .flatMapCompletable(data -> {
                                     Completable uploadFavorites = Observable.fromIterable(data.favorites)
                                            .flatMapCompletable(fav ->
                                                    firebaseRemoteDataSource.addToFavorites(fav)
                                                            .subscribeOn(Schedulers.io())
                                            );

                                     Completable uploadMealPlans = Observable.fromIterable(data.mealPlans)
                                            .flatMapCompletable(plan ->
                                                    firebaseRemoteDataSource.addToWeeklyPlan(plan)
                                                            .subscribeOn(Schedulers.io())
                                            );

                                     return Completable.mergeArray(uploadFavorites, uploadMealPlans)
                                            .andThen(favoriteDao.clearAllFavorites().subscribeOn(Schedulers.io()))
                                            .andThen(mealPlanDao.clearAllPlans().subscribeOn(Schedulers.io()))
                                            .doOnComplete(() -> Log.d(TAG, " ok "));
                                })
                )
                .subscribeOn(Schedulers.io())
                .doOnError(error -> Log.e(TAG, "" + error.getMessage()));
    }

    private static class DataToUpload {
        final List<FavoriteEntity> favorites;
        final List<MealPlanEntity> mealPlans;

        DataToUpload(List<FavoriteEntity> favorites, List<MealPlanEntity> mealPlans) {
            this.favorites = favorites;
            this.mealPlans = mealPlans;
        }
    }
}