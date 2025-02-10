package com.example.testtracker.network;

import com.example.testtracker.main_app.categorymeals.model.CategoryMeals;
import com.example.testtracker.main_app.home.allcategories.model.Category;
import com.example.testtracker.main_app.home.allcountries.model.Country;
import com.example.testtracker.main_app.home.dailymeal.model.Meal;

import java.util.List;

 public interface NetworkCallBack {
     default void onSuccess(List<Meal> products){}
     default void onCategoriesSuccess(List<Category> categories){}

     default void onCountrySuccess(List<Country> countries){}
     default void onCategoryMealsSuccess(List<CategoryMeals> meal){}
      void onFailure(String message);
}
