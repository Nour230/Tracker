package com.example.testtracker.presenter.mealdetails;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.testtracker.models.db.SavedMeals;
import com.example.testtracker.models.mealdetails.MealDetailsRepositoryImpl;
import com.example.testtracker.view.interfaces.MealDetailsView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenterImpl {
    private static final String TAG = "MainActivity";
    private final MealDetailsView view;
    private final MealDetailsRepositoryImpl repo;

    FirebaseDatabase database;
    DatabaseReference myRef;
    SharedPreferences sharedPreferences;

    public MealDetailsPresenterImpl(MealDetailsView view, MealDetailsRepositoryImpl repo,Context context) {
        this.view = view;
        this.repo = repo;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("meals");
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

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

    public void sendData(SavedMeals meal){
        String id = sharedPreferences.getString("id", null);
        myRef.child("Users").child(id).child(meal.getIdMeal()).child(meal.getDate()).setValue(meal);
    }




}
