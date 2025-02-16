package com.example.testtracker.view.interfaces;

import android.view.View;

import com.example.testtracker.models.db.SavedMeals;

public interface OnMealClickListener {
    default  void onMealClick(String mealId, View view){}
   default public void onContryClick(String country, View view){}
    default void onButtonClick(SavedMeals meal){}

     default void deleteFromFav(SavedMeals meal){}

   default public void onCategoryClick(String category, View view){}


    //void onMealClick(String mealId, View view);
}
