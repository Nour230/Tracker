package com.example.testtracker.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.testtracker.models.db.SavedMeals;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealLocalDataSourceImpl {
    private static MealLocalDataSourceImpl clint = null;
    FirebaseDatabase database;
    DatabaseReference myRef;
    SharedPreferences sharedPreferences;
    private MealDAO dao;
    private AppDataBase db;
    private Single<List<SavedMeals>> storedMeals;

    private MealLocalDataSourceImpl(Context context) {
        db = AppDataBase.getInstance(context);
        dao = db.getMealsDao();
        storedMeals = dao.getFavMeals();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("meals");
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

    public static MealLocalDataSourceImpl getInstance(Context context) {
        if (clint == null) {
            clint = new MealLocalDataSourceImpl(context);
        }
        return clint;
    }

    public Single<List<SavedMeals>> getFavMeals() {
        return dao.getFavMeals();
    }


    public Completable insertMeal(SavedMeals meal) {
        Log.i("MainActivity", "insertMeal: " + meal.getIdMeal());
        return dao.insertMeal(meal);
    }

    public Completable deleteMeal(SavedMeals id) {
        return dao.deleteMeal(id);
    }


    public Single<List<SavedMeals>> getPlanMeals() {
        return dao.getPlanMeals();
    }
    public void fetchDataFromFirebase() {
        String id = sharedPreferences.getString("id", null);
        if(id != null){
            myRef.child("Users").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.i("MainActivity", "onDataChange: " + dataSnapshot.getChildren());
                    for (DataSnapshot mealSnapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot dateSnapshot : mealSnapshot.getChildren()) {
                            SavedMeals savedMeals = dateSnapshot.getValue(SavedMeals.class);
                            if (savedMeals != null) {
                                // Insert the meal into the Room database
                                dao.insertMeal(savedMeals).subscribeOn(Schedulers.io()).subscribe();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("SavedMealsRepository", "Error fetching data from Firebase", error.toException());
                }
            });
        }
    }
}
