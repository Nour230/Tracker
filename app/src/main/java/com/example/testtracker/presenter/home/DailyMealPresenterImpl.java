package com.example.testtracker.presenter.home;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.navigation.Navigation;

import com.example.testtracker.R;
import com.example.testtracker.models.dailymeal.MealRepositoryImpl;
import com.example.testtracker.models.db.SavedMeals;
import com.example.testtracker.models.mealdetails.MealDetails;
import com.example.testtracker.presenter.intefaces.DailyMealPresenter;
import com.example.testtracker.view.interfaces.DailyMealView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DailyMealPresenterImpl implements DailyMealPresenter {
    private final DailyMealView view;
    private final MealRepositoryImpl repo;
    FirebaseDatabase database;
    DatabaseReference myRef;
    SharedPreferences sharedPreferences;
    private FirebaseAuth myauth;
    private Context context;

    public DailyMealPresenterImpl(DailyMealView view, MealRepositoryImpl repo, Context context) {
        this.view = view;
        this.repo = repo;
        this.context = context;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("meals");
        myauth = FirebaseAuth.getInstance();
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public void getProducts() {
        view.showLoading();
        repo.getAllMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MealDetails.MealsDTO>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // Handle subscription if needed
                    }

                    @Override
                    public void onSuccess(@NonNull List<MealDetails.MealsDTO> meals) {
                        view.hideLoading();
                        view.showData(meals);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideLoading();
                        view.showError(e.getMessage());
                    }

                });
    }

    public void fetchMealDetails(String mealId) {
        repo.getMealDetails(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealDetails.MealsDTO>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // Handle subscription if needed
                    }

                    @Override
                    public void onSuccess(MealDetails.@NonNull MealsDTO mealsDTO) {

                        view.showMealDetails(mealsDTO);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                });
    }

    @Override
    public void addToFav(SavedMeals meal) {
        repo.addMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            view.addToFav();
                        },
                        error -> {
                            view.showError(error.getMessage());
                        }
                );

    }

    public void addToPlan(SavedMeals meal) {
        repo.addMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            view.addToPlan();
                        },
                        error -> {
                            view.showError(error.getMessage());
                        }
                );
    }

    public void getData() {
        repo.getDataFromFiteBase();
    }

    public void logout(View view) {
        // Clear Firebase authentication
        if (myauth != null) {
            myauth.signOut();
        }

        // Clear SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Clear Room database
        repo.clearLocalDatabase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.d("Logout", "Local database cleared"),
                        throwable -> Log.e("Logout", "Failed to clear local database", throwable));

        // Clear application cache
        clearApplicationCache();

        // Exit the app
        navigateToLoginScreen(view);
    }
    private void navigateToLoginScreen(View view) {
        if (context instanceof Activity) {
            Navigation.findNavController(view)
                    .navigate(R.id.action_homeFragment_to_loginFragment);
        }
    }
    private void clearApplicationCache() {
        try {
            File cacheDir = context.getCacheDir();
            if (cacheDir != null && cacheDir.isDirectory()) {
                deleteDir(cacheDir);
            }
        } catch (Exception e) {
            Log.e("CACHE_CLEAR", "Failed to clear cache", e);
        }
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String child : children) {
                    boolean success = deleteDir(new File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return dir != null && dir.delete();
    }
}