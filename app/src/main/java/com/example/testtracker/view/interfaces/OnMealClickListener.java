package com.example.testtracker.view.interfaces;

import android.view.View;

public interface OnMealClickListener {
    default  void onMealClick(String mealId, View view){}
   default public void onContryClick(String country, View view){}
   default public void onCategoryClick(String category, View view){}


    //void onMealClick(String mealId, View view);
}
